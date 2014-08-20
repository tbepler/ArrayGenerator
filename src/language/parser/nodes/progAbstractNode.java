
package language.parser.nodes;

import language.parser.Symbols;
import language.parser.Visitor;
import language.parser.framework.Node;
import language.parser.framework.Symbol;


/**
 * This class was generated by the LRPaGe parser generator v1.0 using the com.sun.codemodel library.
 * 
 * <P>LRPaGe is available from https://github.com/tbepler/LRPaGe.
 * <P>CodeModel is available from https://codemodel.java.net/.
 * 
 */
public abstract class progAbstractNode
    implements Node<Visitor>
{


    @Override
    public Symbol symbol() {
        return Symbols.PROG;
    }

    @Override
    public progAbstractNode replace() {
        return this;
    }

    @Override
    public String toString() {
        return symbol().toString();
    }

}