package language.implementation.symbols.terminals;

import language.compiler.grammar.Symbol;
import language.implementation.Visitor;
import language.implementation.symbols.Constants;
import language.implementation.symbols.Terminal;

public class Plus extends Terminal{
	private static final long serialVersionUID = 1L;
	
	private static final int HASH = 350385;

	@Override
	public String getRegex() {
		return Constants.PLUS_REGEX;
	}

	@Override
	public Symbol<Visitor> tokenize(String s) {
		return this;
	}

	@Override
	public void accept(Visitor visitor) {
		//do nothing
	}

	@Override
	public Class<? extends Symbol<Visitor>> getSymbolType() {
		return Plus.class;
	}
	
	@Override
	public int getPriority(){
		return Constants.PLUS_MINUS_PRIORITY;
	}
	
	@Override
	public int hashCode(){
		return HASH;
	}
	
	@Override
	public boolean equals(Object o){
		if(o == null) return false;
		if(o == this) return true;
		return o instanceof Plus;
	}

}
