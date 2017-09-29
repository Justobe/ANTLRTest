// Generated from E:/Javaworkspace/TestANTLR/src\Hello.g4 by ANTLR 4.7
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link HelloParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface HelloVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link HelloParser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(HelloParser.ClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link HelloParser#className}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassName(HelloParser.ClassNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link HelloParser#method}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethod(HelloParser.MethodContext ctx);
	/**
	 * Visit a parse tree produced by {@link HelloParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(HelloParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link HelloParser#methodName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodName(HelloParser.MethodNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link HelloParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstruction(HelloParser.InstructionContext ctx);
	/**
	 * Visit a parse tree produced by {@link HelloParser#str}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStr(HelloParser.StrContext ctx);
}