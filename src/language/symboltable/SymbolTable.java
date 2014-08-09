package language.symboltable;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import language.exceptions.SymbolAlreadyDefinedException;
import language.exceptions.UndefinedSymbolException;

/**
 * 
 * @author Wei
 *
 * @param <V>
 */

public class SymbolTable<V> {
	private SymbolTable<V> parent;
	private Map<Symbol, Collection<V>> currentTable;
	
	public SymbolTable(){
		this.parent = null;
		this.currentTable = new HashMap<Symbol, Collection<V>>();
	}
	
	public SymbolTable(SymbolTable<V> parent){
		this.parent = parent;
		this.currentTable = new HashMap<Symbol, Collection<V>>();
	}
	
	public SymbolTable<V> insert(Symbol s, Collection<V> value) throws SymbolAlreadyDefinedException{
		
		if(!currentTable.containsKey(s)){
			currentTable.put(s,value);
		}else{
			throw new SymbolAlreadyDefinedException("The current table already contains: "+ s);
		}
		return this;
	}
	
	public Collection <Collection<V>> lookup(Symbol s) throws UndefinedSymbolException{
		Collection<Collection<V>> result = new HashSet<Collection<V>>();
		SymbolTable<V> currParent = this.parent;
		
		if(currentTable.containsKey(s)){
			result.add(currentTable.get(s));
		}
		
		while(currParent!= null){
			
			if(currParent.getCurrentTable().containsKey(s)){
				result.add(currParent.getCurrentTable().get(s));
			}
			
			currParent = currParent.getParent();
		}
		if(result.isEmpty()){
			throw new UndefinedSymbolException("Undefined Symbol: "+s);
		}
		
		return result;
	}
	
	public SymbolTable<V> getParent(){
		return this.parent;
	}
	
	public Map<Symbol,Collection<V>> getCurrentTable(){
		return this.currentTable;
	}
}
