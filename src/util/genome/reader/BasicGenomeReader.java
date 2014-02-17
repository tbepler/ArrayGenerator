package util.genome.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import jprobe.services.ErrorHandler;
import plugins.genome.GenomeActivator;
import util.genome.Chromosome;
import util.genome.Genome;
import util.genome.GenomicCoordinate;
import util.genome.GenomicRegion;
import util.genome.GenomicSequence;
import util.genome.reader.query.BoundedQueryProcessor;
import util.genome.reader.query.LocationBoundedSequenceQuery;
import util.genome.reader.query.LocationQuery;
import util.genome.reader.query.LocationQueryProcessor;
import util.genome.reader.query.SequenceQuery;
import util.genome.reader.query.SequenceQueryProcessor;
import util.progress.ProgressEvent;
import util.progress.ProgressEvent.Type;

public class BasicGenomeReader extends AbstractGenomeReader{
	
	public static final int LINES_PER_NOTIFY = 10;
	
	private final File m_GenomeFile;
	private final Genome m_Genome;
	
	public BasicGenomeReader(File genomeFile){
		m_GenomeFile = genomeFile;
		m_Genome = this.prereadGenome(genomeFile);
	}

	@Override
	public Genome getGenome() {
		return m_Genome;
	}
	
	private int percentComplete(long baseIndex, Chromosome chr){
		double p = ((double) baseIndex / (double) chr.getSize()) * 100.0;
		return (int) p;
	}
	
	protected void notifyReadProgress(long count, Chromosome chr){
		this.notifyListeners(
				new ProgressEvent(
						this,
						Type.UPDATE,
						this.percentComplete(count, chr),
						100,
						"Reading "+m_GenomeFile.getName()+": "+chr+" ("+m_Genome.indexOf(chr)+"//"+m_Genome.getNumChrs()+")"
						)
				);
	}
	
	protected void notifyCompleted(){
		this.notifyListeners(new ProgressEvent(this, Type.COMPLETED));
	}
	
	@Override
	public void read(List<LocationQuery> locationQueries, List<SequenceQuery> sequenceQueries, List<LocationBoundedSequenceQuery> boundedQueries) {
		LocationQueryProcessor locationProcessor = new LocationQueryProcessor(locationQueries);
		SequenceQueryProcessor sequenceProcessor = new SequenceQueryProcessor(sequenceQueries);
		BoundedQueryProcessor boundedProcessor = new BoundedQueryProcessor(boundedQueries);
		
		try {
			Scanner s = new Scanner(m_GenomeFile);
			GenomicCoordinate seqStart = m_Genome.newGenomicCoordinate(m_Genome.getFirstChr(), 1);
			long count = 0;
			int lineCount = 0;
			while(s.hasNextLine()){
				String line = s.nextLine();
				count += line.length();
				if(lineCount % LINES_PER_NOTIFY == 0){
					this.notifyReadProgress(count, seqStart.getChromosome());
				}
				if(line.startsWith(">")){
					//only reset the char count to zero
					//preread genome knows when to advance to next chromosome already
					lineCount = 0;
					count = 0;
					continue;
				}
				GenomicSequence seq = new GenomicSequence(line, new GenomicRegion(seqStart, seqStart.increment(line.length()-1)));
				locationProcessor.process(seq);
				sequenceProcessor.process(seq);
				boundedProcessor.process(seq);
				seqStart = seq.getEnd().increment(1);
				lineCount++;
			}
			this.notifyCompleted();
			s.close();
		} catch (FileNotFoundException e) {
			ErrorHandler.getInstance().handleException(e, GenomeActivator.getBundle());
		}
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
