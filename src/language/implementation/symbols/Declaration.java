package language.implementation.symbols;

import language.compiler.grammar.Token;
import language.implementation.Visitor;

public abstract class Declaration extends Rule<Visitor>{
	private static final long serialVersionUID = 1L;
	
	protected Declaration(Class<? extends Declaration> clazz, Class<? extends Token<Visitor>> ... rhs){
		super(clazz, rhs);
	}
	
	@Override
	public Class<? extends Token<Visitor>> leftHandSide() {
		return Declaration.class;
	}

	@Override
	public Class<? extends Token<Visitor>> getSymbolType() {
		return Declaration.class;
	}
}

