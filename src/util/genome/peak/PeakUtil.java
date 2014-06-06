package util.genome.peak;

import java.util.ArrayList;
import java.util.List;

public class PeakUtil {
	
	public static interface Filter{
		
		public boolean keep(Peak p);
		
	}
	
	public PeakGroup filter(PeakGroup group, Filter f){
		List<Peak> keep = new ArrayList<Peak>();
		for(Peak p : group){
			if(f.keep(p)){
				keep.add(p);
			}
		}
		return new PeakGroup(keep);
	}
	
}
