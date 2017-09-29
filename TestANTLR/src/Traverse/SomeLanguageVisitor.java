package Traverse;

/**
 * Created by YanMing on 2017/9/27.
 */
// Generated from E:/Javaworkspace/TestANTLR/src\SomeLanguage.g4 by ANTLR 4.7
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SomeLanguageParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SomeLanguageVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link SomeLanguageParser#classDeclaration}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitClassDeclaration(SomeLanguageParser.ClassDeclarationContext ctx);
    /**
     * Visit a parse tree produced by {@link SomeLanguageParser#className}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitClassName(SomeLanguageParser.ClassNameContext ctx);
    /**
     * Visit a parse tree produced by {@link SomeLanguageParser#method}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMethod(SomeLanguageParser.MethodContext ctx);
    /**
     * Visit a parse tree produced by {@link SomeLanguageParser#parameter}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitParameter(SomeLanguageParser.ParameterContext ctx);
    /**
     * Visit a parse tree produced by {@link SomeLanguageParser#methodName}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMethodName(SomeLanguageParser.MethodNameContext ctx);
    /**
     * Visit a parse tree produced by {@link SomeLanguageParser#instruction}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInstruction(SomeLanguageParser.InstructionContext ctx);
    /**
     * Visit a parse tree produced by {@link SomeLanguageParser#str}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStr(SomeLanguageParser.StrContext ctx);
}