package jprobe.framework.model.compiler.grammar.implementation.symbols.terminals;

public class Constants {
	
	public static final String IF_REGEX = "if";
	public static final String THEN_REGEX = "then";
	public static final String ELSE_REGEX = "else";
	public static final String TRUE_REGEX = "true(?=[^a-zA-Z0-9_]|$)";
	public static final String FALSE_REGEX = "false(?=[^a-zA-Z0-9_]|$)";
	
	public static final String ID_REGEX = "[a-zA-Z_]+[a-zA-Z0-9_]*";
	public static final String NUM_REGEX = "-?[0-9]+(\\.[0-9]+)?";
	public static final String INT_REGEX = "[0-9]+";
	public static final String DOUBLE_REGEX = "[0-9]+\\.[0-9]+";
	public static final String STR_REGEX = "\"(?:\\\\\"|[^\"])*?\"";
	public static final String CHAR_REGEX = "'.'";
	public static final String BOOL_REGEX = "("+TRUE_REGEX+")|("+FALSE_REGEX+")";
	public static final String LPAREN_REGEX = "\\(";
	public static final String RPAREN_REGEX = "\\)";
	public static final String COMMA_REGEX =  ",";
	public static final String LBRACKED_REGEX = "\\[";
	public static final String RBRACKET_REGEX = "\\]";
	public static final String SEMICOLON_REGEX = ";";
	
	public static final String BANG_REGEX = "!";
	
	public static final String AND_REGEX = "(and)|(&&)";
	public static final String OR_REGEX = "(or)|(||)";
	public static final String EQ_REGEX = "==";
	public static final String NEQ_REGEX = "!=";
	public static final String LESS_REGEX = "<";
	public static final String LESS_EQ_REGEX = "<=";
	public static final String GREATER_REGEX = ">";
	public static final String GREATER_EQ_REGEX = ">=";

	public static final String PLUS_REGEX = "+";
	public static final String MINUS_REGEX = "-";
	public static final String MULT_REGEX = "\\*";
	public static final String DIV_REGEX = "/";
	public static final String EXP_REGEX = "\\^";
	public static final String ASSIGN_REGEX =  "=";
	
	
	public static final String WHITESPACE_REGEX = "\\s+";
	
	public static final String ERROR_REGEX = ".";
	
	public static final char ESC = '\\';
	
}
