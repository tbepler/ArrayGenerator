
package language.parser.nodes;

import language.parser.Symbols;
import language.parser.Visitor;
import language.parser.framework.Symbol;
import language.parser.framework.Token;


/**
 * This class was generated by the LRPaGe parser generator v1.0 using the com.sun.codemodel library.
 * 
 * <P>LRPaGe is available from https://github.com/tbepler/LRPaGe.
 * <P>CodeModel is available from https://codemodel.java.net/.
 * 
 */
public class EOFToken
    extends Token<Visitor>
{


    public EOFToken(int line, int pos) {
        super("", line, pos);
    }

    @Override
    public void accept(Visitor visitor) {
        //do nothing
    }

    @Override
    public Symbol symbol() {
        return Symbols.EOF;
    }

    @Override
    public String toString() {
        return symbol().toString();
    }

}