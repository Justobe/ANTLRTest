package Traverse;

import org.antlr.v4.runtime.tree.ParseTree;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by YanMing on 2017/9/27.
 */
public class SomeLangVisitorParser implements ILangParser {
    @Override
    public SomeClass parse(ParseTree parseTree) {
        ClassVisitor classVistor = new ClassVisitor();
        return classVistor.visit(parseTree);
    }


    class ClassVisitor extends SomeLanguageBaseVisitor<SomeClass> {
        @Override
        public SomeClass visitClassDeclaration(SomeLanguageParser.ClassDeclarationContext ctx) {
            MethodVisitor methodVisitor = new MethodVisitor();
            Collection<Method> methods = ctx.method()
                    .stream()
                    .map(methodContext -> methodContext.accept(methodVisitor))
                    .collect(Collectors.toList());
            return new SomeClass(ctx.className().getText(), methods);
        }
    }

    class MethodVisitor extends SomeLanguageBaseVisitor<Method> {

        @Override
        public Method visitMethod(SomeLanguageParser.MethodContext ctx) {
            InstructionVisitor instructionVisitor = new InstructionVisitor();
            Collection<Instruction> instructions = ctx.instruction()
                    .stream()
                    .map(instructionContext -> instructionContext.accept(instructionVisitor))
                    .collect(Collectors.toList());
            StringBuilder sb = new StringBuilder("");
            ctx.parameter().forEach(e -> sb.append(e.getText()));
            return new Method(ctx.methodName().getText(), sb.toString().split(","), instructions);
        }
    }

    class InstructionVisitor extends SomeLanguageBaseVisitor<Instruction> {
        @Override
        public Instruction visitInstruction(SomeLanguageParser.InstructionContext ctx) {
            return new Instruction(ctx.getText());
        }
    }
}