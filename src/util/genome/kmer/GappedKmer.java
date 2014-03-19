package util.genome.kmer;

import java.util.*;

import util.ArrayUtils;

public class GappedKmer implements Kmer{
	private static final long serialVersionUID = 1L;
	
	private final Map<Integer, Map<String, Score>> m_Words = new HashMap<Integer, Map<String, Score>>();
	private final int[] m_MotifLens;
	
	GappedKmer(Map<String, Score> words){
		for(String word : words.keySet()){
			int len = word.length();
			Score score = words.get(word);
			if(m_Words.containsKey(len)){
				Map<String, Score> lenWords = m_Words.get(len);
				lenWords.put(word, score);
			}else{
				Map<String, Score> lenWords = new HashMap<String, Score>();
				lenWords.put(word, score);
				m_Words.put(len, lenWords);
			}
		}
		m_MotifLens = new int[m_Words.size()];
		int i = 0;
		for(int len : m_Words.keySet()){
			m_MotifLens[i] = len;
			i++;
		}
		Arrays.sort(m_MotifLens);
	}
	
	/**
	 * Converts the given word into a regex by appending "^" at the start and "$" at the end
	 * @param word
	 * @return
	 */
	protected static String toRegex(String word){
		return "^"+word+"$";
	}
	
	/**
	 * Checks whether the given word matches the given motif as a regex
	 * @param word
	 * @param motif
	 * @return
	 */
	protected static boolean matches(String word, String motif){
		return word.matches(toRegex(motif));
	}

	@Override
	public boolean contains(String word) {
		int len = word.length();
		if(m_Words.containsKey(len)){
			for(String motif : m_Words.get(len).keySet()){
				if(matches(word, motif)){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int[] getWordLengths() {
		return m_MotifLens;
	}
	
	/**
	 * Returns a collection containing the scores for motifs matching the given word
	 * @param word
	 * @return
	 */
	protected Collection<Score> getScores(String word){
		int len = word.length();
		if(!m_Words.containsKey(len)){
			throw new RuntimeException("Kmer does not contain word: "+word+".");
		}
		Map<String, Score> motifScores = m_Words.get(len);
		Collection<Score> scores = new HashSet<Score>();
		for(String motif : motifScores.keySet()){
			if(matches(word, motif)){
				scores.add(motifScores.get(motif));
			}
		}
		return scores;
		
	}
	
	@Override
	public double escore(String word) {
		Collection<Score> scores = this.getScores(word);
		return maxEScore(scores);
	}
	
	@Override 
	public double escore(String seq, int start, int end){
		return this.escore(seq.substring(start, end)); 
	}
	
	protected static double maxEScore(Collection<Score> scores){
		double escore = Double.NEGATIVE_INFINITY;
		for(Score s : scores){
			if(s.ESCORE > escore){
				escore = s.ESCORE;
			}
		}
		return escore;
	}
	
	protected static double maxIntensity(Collection<Score> scores){
		double inten = Double.NEGATIVE_INFINITY;
		for(Score s : scores){
			if(s.INTENSITY > inten){
				inten = s.INTENSITY;
			}
		}
		return inten;
	}
	
	protected static double maxZScore(Collection<Score> scores){
		double zscore = Double.NEGATIVE_INFINITY;
		for(Score s : scores){
			if(s.ZSCORE > zscore){
				zscore = s.ZSCORE;
			}
		}
		return zscore;
	}

	@Override
	public double[] escoreSequence(String sequence) {
		int[] lens = this.getWordLengths();
		int min = ArrayUtils.min(lens);
		if(sequence.length() < min){
			throw new RuntimeException("Cannot escore sequence: "+sequence+". Sequence length is less than min word length: "+min+".");
		}
		double[] scores = new double[sequence.length()];
		//initialize scores to -infinity
		Arrays.fill(scores, Double.NEGATIVE_INFINITY);
		//scan the sequence with each valid motif length
		for(int motifLen : lens){
			//check that word length is valid for this sequence
			if(sequence.length() >= motifLen){
				//score the word at each position
				for(int i=0; i<sequence.length() - motifLen + 1; i++){
					String word = sequence.substring(i, i+motifLen);
					double escore = this.escore(word);
					//assign each base within the word the score of the highest scoring word that contains it 
					for(int j=i; j<i+motifLen; j++){
						if(scores[j] < escore){
							scores[j] = escore;
						}
					}
				}
			}
		}
		return scores;
	}
	
	@Override
	public double[] escoreSequence(String sequence, int start, int end){
		return this.escoreSequence(sequence.substring(start, end));
	}

	@Override
	public double intensity(String word) {
		return maxIntensity(this.getScores(word));
	}

	@Override
	public double[] intensitySequence(String sequence) {
		int[] lens = this.getWordLengths();
		int min = ArrayUtils.min(lens);
		if(sequence.length() < min){
			throw new RuntimeException("Cannot intensity score sequence: "+sequence+". Sequence length is less than min word length: "+min+".");
		}
		double[] scores = new double[sequence.length()];
		//initialize scores to -infinity
		Arrays.fill(scores, Double.NEGATIVE_INFINITY);
		//scan the sequence with each valid motif length
		for(int motifLen : lens){
			//check that word length is valid for this sequence
			if(sequence.length() >= motifLen){
				//score the word at each position
				for(int i=0; i<sequence.length() - motifLen + 1; i++){
					String word = sequence.substring(i, i+motifLen);
					double intensity = this.intensity(word);
					//assign each base within the word the score of the highest scoring word that contains it 
					for(int j=i; j<i+motifLen; j++){
						if(scores[j] < intensity){
							scores[j] = intensity;
						}
					}
				}
			}
		}
		return scores;
	}

	@Override
	public double zscore(String word) {
		return maxZScore(this.getScores(word));
	}

	@Override
	public double[] zscoreSequence(String sequence) {
		int[] lens = this.getWordLengths();
		int min = ArrayUtils.min(lens);
		if(sequence.length() < min){
			throw new RuntimeException("Cannot zscore sequence: "+sequence+". Sequence length is less than min word length: "+min+".");
		}
		double[] scores = new double[sequence.length()];
		//initialize scores to -infinity
		Arrays.fill(scores, Double.NEGATIVE_INFINITY);
		//scan the sequence with each valid motif length
		for(int motifLen : lens){
			//check that word length is valid for this sequence
			if(sequence.length() >= motifLen){
				//score the word at each position
				for(int i=0; i<sequence.length() - motifLen + 1; i++){
					String word = sequence.substring(i, i+motifLen);
					double zscore = this.zscore(word);
					//assign each base within the word the score of the highest scoring word that contains it 
					for(int j=i; j<i+motifLen; j++){
						if(scores[j] < zscore){
							scores[j] = zscore;
						}
					}
				}
			}
		}
		return scores;
	}

}