
package language.parser.nodes;

import language.parser.Visitor;


/**
 * This class was generated by the LRPaGe parser generator v1.0 using the com.sun.codemodel library.
 * 
 * <P>LRPaGe is available from https://github.com/tbepler/LRPaGe.
 * <P>CodeModel is available from https://codemodel.java.net/.
 * 
 */
public class _Punctuation6exp_Punctuation7exp
    extends expAbstractNode
{

    public final expAbstractNode exp0;

    public _Punctuation6exp_Punctuation7exp(_Punctuation6Token _punctuation60, expAbstractNode exp1, _Punctuation7Token _punctuation72) {
        this.exp0 = exp1;
    }

    @Override
    public int getLine() {
        return exp0 .getLine();
    }

    @Override
    public int getPos() {
        return exp0 .getPos();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = ((hash* 13)+ exp0 .hashCode());
        hash = ((hash* 13)+ getClass().hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this.equals(o)) {
            return true;
        }
        if (o.equals(null)) {
            return false;
        }
        if (!(o instanceof _Punctuation6exp_Punctuation7exp)) {
            return false;
        }
        _Punctuation6exp_Punctuation7exp castResult = ((_Punctuation6exp_Punctuation7exp) o);
        if (!this.exp0 .equals(castResult.exp0)) {
            return false;
        }
        return true;
    }

}