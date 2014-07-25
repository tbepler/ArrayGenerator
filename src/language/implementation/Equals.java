package language.implementation;

import java.util.Arrays;
import java.util.List;

public class Equals {
	
	public static boolean equals(Object o1, Object o2){
		if(o1 == o2) return true;
		if(o1 == null || o2 == null) return false;
		return o1.equals(o2);
	}
	
	public static boolean listEquals(List<? extends Object> l1, List<? extends Object> l2){
		if(l1 == l2) return true;
		if(l1 == null || l2 == null) return false;
		if(l1.size() != l2.size()) return false;
		for( int i = 0 ; i < l1.size() ; ++i ){
			if(!equals(l1.get(i), l2.get(i))){
				return false;
			}
		}
		return true;
	}
	
	public static int hashCode(Object ... o){
		return Arrays.deepHashCode(o);
	}
	
}
