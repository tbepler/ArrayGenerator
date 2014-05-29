package util.genome.probe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import util.genome.GenomicCoordinate;
import util.genome.GenomicRegion;
import util.genome.GenomicSequence;
import util.genome.kmer.Kmer;
import util.progress.ProgressListener;

class Mutate {
	
	private static class Mutation{
		public final GenomicCoordinate coord;
		public final char base;
		public Mutation(GenomicCoordinate coord, char base){
			this.coord = coord; this.base = base;
		}
	}
	
	private static class MutationRecord{
		public GenomicSequence seq = null;
		public Collection<Mutation> muts = new ArrayList<Mutation>();
	}
	
	public static Probe mutate(
			ProgressListener l,
			Probe mut,
			Kmer kmer,
			double escoreCutoff,
			int bindingSiteBarrier,
			Set<Character> alphabet
			){
		
		List<GenomicRegion> bindingSites = getBindingSites(mut);
		List<GenomicRegion> protectedRegions = getProtectedRegions(bindingSites, bindingSiteBarrier);
		Set<GenomicCoordinate> immutableCoords = toCoordinateSet(protectedRegions);
		
		MutationRecord record = new MutationRecord();
		record.seq = mut.asGenomicSequence();
		record = mutateRecursive(l, record, kmer, protectedRegions, immutableCoords, escoreCutoff, alphabet);
		
		List<GenomicCoordinate> mutations = new ArrayList<GenomicCoordinate>(mut.getMutations());
		for(Mutation m : record.muts){
			mutations.add(m.coord);
		}
		
		return new Probe(mut, record.seq, mutations, !mutations.isEmpty());
		
	}
	
	private static MutationRecord mutateRecursive(
			ProgressListener l,
			MutationRecord record,
			Kmer kmer,
			List<GenomicRegion> protectedRegions,
			Set<GenomicCoordinate> immutable,
			double escoreCutoff,
			Set<Character> alphabet
			){
		
		GenomicSequence seq = record.seq;
		Map<GenomicSequence, Double> scores = score(seq, kmer, protectedRegions, immutable);
		
		if(allScoresBelowCutoff(scores, escoreCutoff)){
			return record;
		}
		
		GenomicSequence mutate = pickHighestScoringSubseq(scores);
		
		Mutation mut = pickBestMutation(mutate, kmer, immutable, alphabet); //picks the mutation that lowers the score the most
		if(mut == null){ //no mutation could lower the score
			immutable.addAll(toCoordinateSet(mutate.getRegion()));
			return mutateRecursive(l, record, kmer, protectedRegions, immutable, escoreCutoff, alphabet);
		}else{ //make best mutation
			GenomicSequence mutatedSeq = makeMutation(mut, seq);
			record.seq = mutatedSeq;
			record.muts.add(mut);
			return mutateRecursive(l, record, kmer, protectedRegions, immutable, escoreCutoff, alphabet);
		}
	}
	
	private static Mutation pickBestMutation(GenomicSequence seq, Kmer kmer, Set<GenomicCoordinate> immutable, Set<Character> alphabet){
		Mutation best = null;
		double bestScore = kmer.escore(seq.getSequence());
		for(GenomicCoordinate coord : seq){
			if(!immutable.contains(coord)){
				for(Mutation mut : possibleMutations(seq, coord, alphabet)){
					GenomicSequence result = makeMutation(mut, seq);
					double resultScore = kmer.escore(result.getSequence());
					if(resultScore < bestScore){
						best = mut;
						bestScore = resultScore;
					}
				}
			}
		}
		return best;
	}
	
	private static GenomicSequence makeMutation(Mutation mut, GenomicSequence seq){
		return seq.mutate(mut.coord, mut.base);
	}
	
	private static List<Mutation> possibleMutations(GenomicSequence seq, GenomicCoordinate location, Set<Character> alphabet){
		List<Mutation> muts = new ArrayList<Mutation>();
		char cur = seq.getBaseAt(location);
		for(char c : alphabet){
			if(c != cur){
				muts.add(new Mutation(location, c));
			}
		}
		return muts;
	}
	
	private static GenomicSequence pickHighestScoringSubseq(Map<GenomicSequence, Double> scores){
		GenomicSequence seq = null;
		double score = Double.NEGATIVE_INFINITY;
		for(Entry<GenomicSequence, Double> e : scores.entrySet()){
			if(e.getValue() > score){
				seq = e.getKey();
				score = e.getValue();
			}
		}
		return seq;
	}
	
	private static boolean allScoresBelowCutoff(Map<GenomicSequence, Double> scores, double cutoff){
		for(Double score : scores.values()){
			if(score > cutoff) return false;
		}
		return true;
	}
	
	private static Map<GenomicSequence, Double> score(
			GenomicSequence seq,
			Kmer kmer,
			List<GenomicRegion> protectedRegions,
			Set<GenomicCoordinate> immutable
			){
		
		Map<GenomicSequence, Double> scores = new HashMap<GenomicSequence, Double>();
		for(int wordLen : kmer.getWordLengths()){
			for(GenomicSequence subseq : subseqs(seq, wordLen)){
				if(!moreThanHalfOverlapsProtected(subseq, protectedRegions) && isMutable(subseq, immutable)){
					scores.put(subseq, kmer.escore(subseq.getSequence()));
				}
			}
		}
		return scores;
	}
	
	private static boolean isMutable(GenomicSequence seq, Set<GenomicCoordinate> immutable){
		for(GenomicCoordinate coord : seq){
			if(!immutable.contains(coord)) return true;
		}
		return false;
	}
	
	private static boolean moreThanHalfOverlapsProtected(GenomicSequence subseq, List<GenomicRegion> protectedRegions){
		int maxOverlap = subseq.length() / 2;
		for(GenomicRegion r : protectedRegions){
			if(maxOverlap < subseq.getOverlap(r)){
				return true;
			}
		}
		return false;
	}
	
	private static List<GenomicSequence> subseqs(GenomicSequence seq, int subseqLength){
		List<GenomicSequence> subseqs = new ArrayList<GenomicSequence>();
		GenomicCoordinate start = seq.getStart();
		GenomicCoordinate end = start.increment(subseqLength - 1);
		while(end.compareTo(seq.getEnd()) <= 0){
			subseqs.add(seq.subsequence(start, end));
			start = start.increment(1);
			end = end.increment(1);
		}
		return subseqs;
	}
	
	private static List<GenomicRegion> getBindingSites(Probe p){
		List<GenomicRegion> bindingSites = new ArrayList<GenomicRegion>();
		for(GenomicRegion bindingSite : p.getBindingSites()){
			bindingSites.add(bindingSite);
		}
		return bindingSites;
	}
	
	private static List<GenomicRegion> getProtectedRegions(List<GenomicRegion> bindingSites, int bindingSiteBarrier){
		List<GenomicRegion> prot = new ArrayList<GenomicRegion>();
		for(GenomicRegion bindingSite : bindingSites){
			GenomicRegion protRegion = new GenomicRegion(
					bindingSite.getStart().decrement(bindingSiteBarrier),
					bindingSite.getEnd().increment(bindingSiteBarrier)
					);
			prot.add(protRegion);
		}
		return prot;
	}
	
	private static Set<GenomicCoordinate> toCoordinateSet(Collection<GenomicRegion> regions){
		Set<GenomicCoordinate> coords = new HashSet<GenomicCoordinate>();
		for(GenomicRegion r : regions){
			for(GenomicCoordinate c : r){
				coords.add(c);
			}
		}
		return coords;
	}
	
	private static Set<GenomicCoordinate> toCoordinateSet(GenomicRegion region){
		Set<GenomicCoordinate> coords = new HashSet<GenomicCoordinate>();
		for(GenomicCoordinate coord : region){
			coords.add(coord);
		}
		return coords;
	}
	
}