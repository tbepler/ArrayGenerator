package language.implementation.symbols.types;

import java.util.Collection;
import language.compiler.grammar.Production;
import language.implementation.Visitor;

public class TypeRules {
	
	public static void addAll(Collection<Production<Visitor>> col){
		col.add(new IdType(null));
		col.add(new BlockType(null, null, null));
		col.add(new FuncType(null, null, null));
	}

}
