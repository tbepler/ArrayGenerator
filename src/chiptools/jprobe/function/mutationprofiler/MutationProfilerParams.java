package chiptools.jprobe.function.mutationprofiler;

import java.io.File;

public class MutationProfilerParams {
	
	public boolean useMinEscore = false;
	public double minEscore = 0.35;
	
	public File kmerLibrary = null;
	public boolean recursive = false;
	
	public String seq1 = null;
	public String seq1Name = null;
	
	public String seq2 = null;
	public String seq2Name = null;
	
}
