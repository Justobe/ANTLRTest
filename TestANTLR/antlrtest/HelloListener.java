// Generated from E:/Javaworkspace/TestANTLR/src\Hello.g4 by ANTLR 4.7
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link HelloParser}.
 */
public interface HelloListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link HelloParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(HelloParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(HelloParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#className}.
	 * @param ctx the parse tree
	 */
	void enterClassName(HelloParser.ClassNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#className}.
	 * @param ctx the parse tree
	 */
	void exitClassName(HelloParser.ClassNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#method}.
	 * @param ctx the parse tree
	 */
	void enterMethod(HelloParser.MethodContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#method}.
	 * @param ctx the parse tree
	 */
	void exitMethod(HelloParser.MethodContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(HelloParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(HelloParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#methodName}.
	 * @param ctx the parse tree
	 */
	void enterMethodName(HelloParser.MethodNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#methodName}.
	 * @param ctx the parse tree
	 */
	void exitMethodName(HelloParser.MethodNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstruction(HelloParser.InstructionContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstruction(HelloParser.InstructionContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#str}.
	 * @param ctx the parse tree
	 */
	void enterStr(HelloParser.StrContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#str}.
	 * @param ctx the parse tree
	 */
	void exitStr(HelloParser.StrContext ctx);
}