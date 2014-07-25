package language.implementation;

import java.io.PrintStream;

import language.implementation.symbols.ErrorToken;
import language.implementation.symbols.Program;
import language.implementation.symbols.declarations.TypeDecl;
import language.implementation.symbols.expressions.BooleanExp;
import language.implementation.symbols.expressions.CharExp;
import language.implementation.symbols.expressions.DivExp;
import language.implementation.symbols.expressions.DoubleExp;
import language.implementation.symbols.expressions.ExponentExp;
import language.implementation.symbols.expressions.FunctionExp;
import language.implementation.symbols.expressions.IdentifierExp;
import language.implementation.symbols.expressions.IntExp;
import language.implementation.symbols.expressions.MinusExp;
import language.implementation.symbols.expressions.MultExp;
import language.implementation.symbols.expressions.PlusExp;
import language.implementation.symbols.expressions.StringExp;
import language.implementation.symbols.expressions.assignment.DeclFuncAssignment;
import language.implementation.symbols.expressions.assignment.DeclVarAssignment;
import language.implementation.symbols.expressions.assignment.FuncAssignment;
import language.implementation.symbols.expressions.assignment.VarAssignment;
import language.implementation.symbols.lists.IdList;
import language.implementation.symbols.lists.PatternList;
import language.implementation.symbols.lists.StmList;
import language.implementation.symbols.patterns.IdPattern;
import language.implementation.symbols.patterns.Pattern;
import language.implementation.symbols.statements.DeclStm;
import language.implementation.symbols.statements.ExpStm;
import language.implementation.symbols.terminals.BooleanLiteral;
import language.implementation.symbols.terminals.CharLiteral;
import language.implementation.symbols.terminals.DoubleLiteral;
import language.implementation.symbols.terminals.Identifier;
import language.implementation.symbols.terminals.IntLiteral;
import language.implementation.symbols.terminals.StringLiteral;
import language.implementation.symbols.types.BlockType;
import language.implementation.symbols.types.FuncType;
import language.implementation.symbols.types.IdType;

public class PrintVisitor implements Visitor{
	
	private static final String DELIM = "  ";
	private static final PrintStream OUT = System.out;
	
	private int indent = 0;
	
	private void println(Object o){
		for( int i = 0 ; i < indent ; ++i ){
			OUT.print(DELIM);
		}
		OUT.println(o);
	}
	
	@Override
	public void visit(Program p) {
		this.println(p);
		this.println("{");
		++indent;
		p.stm.accept(this);
		--indent;
		this.println("}");
	}
	
	@Override
	public void visit(StmList s){
		this.println(s);
		this.println("{");
		++indent;
		s.left.accept(this);
		s.right.accept(this);
		--indent;
		this.println("}");
	}

	@Override
	public void visit(ExpStm s) {
		this.println(s);
		this.println("{");
		++indent;
		s.e.accept(this);
		--indent;
		this.println("}");
	}

	@Override
	public void visit(IdentifierExp e) {
		this.println(e);
		this.println("{");
		++indent;
		e.id.accept(this);
		--indent;
		this.println("}");
	}

	@Override
	public void visit(StringExp e) {
		this.println(e);
		this.println("{");
		++indent;
		e.s.accept(this);
		--indent;
		this.println("}");
	}

	@Override
	public void visit(CharExp e) {
		this.println(e);
		this.println("{");
		++indent;
		e.c.accept(this);
		--indent;
		this.println("}");
	}

	@Override
	public void visit(IntExp e) {
		this.println(e);
		this.println("{");
		++indent;
		e.n.accept(this);
		--indent;
		this.println("}");
	}

	@Override
	public void visit(DoubleExp e) {
		this.println(e);
		this.println("{");
		++indent;
		e.n.accept(this);
		--indent;
		this.println("}");
	}

	@Override
	public void visit(BooleanExp e) {
		this.println(e);
		this.println("{");
		++indent;
		e.b.accept(this);
		--indent;
		this.println("}");
	}

	@Override
	public void visit(Identifier id) {
		this.println(id);
	}

	@Override
	public void visit(StringLiteral s) {
		this.println(s);
	}

	@Override
	public void visit(CharLiteral c) {
		this.println(c);
	}

	@Override
	public void visit(IntLiteral n) {
		this.println(n);
	}

	@Override
	public void visit(DoubleLiteral n) {
		this.println(n);
	}

	@Override
	public void visit(BooleanLiteral b) {
		this.println(b);
	}

	@Override
	public void visit(ErrorToken e) {
		this.println(e);
	}

	@Override
	public void visit(PlusExp e) {
		this.println(e);
		this.println("{");
		++indent;
		e.left.accept(this);
		e.right.accept(this);
		--indent;
		this.println("}");
	}

	@Override
	public void visit(MinusExp e) {
		this.println(e);
		this.println("{");
		++indent;
		e.left.accept(this);
		e.right.accept(this);
		--indent;
		this.println("}");
	}

	@Override
	public void visit(MultExp e) {
		this.println(e);
		this.println("{");
		++indent;
		e.left.accept(this);
		e.right.accept(this);
		--indent;
		this.println("}");
	}

	@Override
	public void visit(DivExp e) {
		this.println(e);
		this.println("{");
		++indent;
		e.left.accept(this);
		e.right.accept(this);
		--indent;
		this.println("}");
	}

	@Override
	public void visit(ExponentExp e) {
		this.println(e);
		this.println("{");
		++indent;
		e.left.accept(this);
		e.right.accept(this);
		--indent;
		this.println("}");
	}

	@Override
	public void visit(FunctionExp e) {
		this.println(e);
		this.println("{");
		++indent;
		e.left.accept(this);
		e.right.accept(this);
		--indent;
		this.println("}");
	}

	@Override
	public void visit(DeclStm s) {
		this.println(s);
		this.println("{");
		++indent;
		s.d.accept(this);
		--indent;
		this.println("}");
	}

	@Override
	public void visit(IdType t) {
		this.println(t);
		this.println("{");
		++indent;
		t.id.accept(this);
		--indent;
		this.println("}");
	}

	@Override
	public void visit(TypeDecl d) {
		this.println(d);
		this.println("{");
		++indent;
		d.t.accept(this);
		d.ids.accept(this);
		--indent;
		this.println("}");
	}

	@Override
	public void visit(IdList l) {
		this.println(l);
		this.println("{");
		++indent;
		for(Identifier id : l){
			id.accept(this);
		}
		--indent;
		this.println("}");
	}

	@Override
	public void visit(BlockType t) {
		this.println(t);
		this.println("{");
		++indent;
		t.t.accept(this);
		--indent;
		this.println("}");
	}

	@Override
	public void visit(FuncType t) {
		this.println(t);
		this.println("{");
		++indent;
		t.left.accept(this);
		t.right.accept(this);
		--indent;
		this.println("}");
	}

	@Override
	public void visit(FuncAssignment e) {
		this.println(e);
		this.println("{");
		++indent;
		e.ids.accept(this);
		e.pats.accept(this);
		e.e.accept(this);
		--indent;
		this.println("}");
	}

	@Override
	public void visit(DeclFuncAssignment e) {
		this.println(e);
		this.println("{");
		++indent;
		e.d.accept(this);
		e.pats.accept(this);
		e.e.accept(this);
		--indent;
		this.println("}");
	}

	@Override
	public void visit(VarAssignment e) {
		this.println(e);
		this.println("{");
		++indent;
		e.ids.accept(this);
		e.e.accept(this);
		--indent;
		this.println("}");
	}

	@Override
	public void visit(DeclVarAssignment e) {
		this.println(e);
		this.println("{");
		++indent;
		e.d.accept(this);
		e.e.accept(this);
		--indent;
		this.println("}");
	}

	@Override
	public void visit(PatternList l) {
		this.println(l);
		this.println("{");
		++indent;
		for(Pattern p : l){
			p.accept(this);
		}
		--indent;
		this.println("}");
	}

	@Override
	public void visit(IdPattern p) {
		this.println(p);
		this.println("{");
		++indent;
		p.id.accept(this);
		--indent;
		this.println("}");
	}

}
