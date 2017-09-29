package Traverse;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.stringtemplate.v4.compiler.Bytecode;



import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by YanMing on 2017/9/27.
 */
public class LangeListenerParser implements ILangParser {

    public SomeClass parse(ParseTree parseTree) {
        ClassListener classListener = new ClassListener();
        new ParseTreeWalker().walk(classListener, parseTree);
        SomeClass someClass = classListener.getSomeClass();
        return someClass;
    }

    class ClassListener extends SomeLanguageBaseListener {
        SomeClass someClass;

        @Override
        public void enterClassDeclaration(SomeLanguageParser.ClassDeclarationContext ctx) {
            SomeLanguageParser.ClassNameContext classNameContext = ctx.className();
            final MethodListener methodListener = new MethodListener();
            ctx.method().forEach(method -> {
                        method.enterRule(methodListener);
                    }
            );
            someClass = new SomeClass(classNameContext.getText(), methodListener.getMethod());
        }

        public SomeClass getSomeClass() {
            return someClass;
        }
    }
    class MethodListener extends SomeLanguageBaseListener {
        Collection<Method> method;

        public MethodListener() {
            method = new ArrayList<>();
        }

        @Override
        public void enterMethod(SomeLanguageParser.MethodContext ctx) {
            String methodName = ctx.methodName().getText();
            InstructionListener instructionListener = new InstructionListener();
            ctx.instruction().forEach(instructionContext -> {
                        instructionContext.enterRule(instructionListener);
                    }
            );
            StringBuilder parameter = new StringBuilder("");
            ctx.parameter().forEach(e -> parameter.append(e.getText()));
            method.add(new Method(methodName, parameter.toString().split(","), instructionListener.getInstruction()));
        }

        public Collection<Method> getMethod() {
            return method;
        }
    }

    class InstructionListener extends SomeLanguageBaseListener {
        Collection<Instruction> instruction;
        StringBuilder instructionDesc = new StringBuilder("");

        public InstructionListener() {
            instruction = new ArrayList<>();
        }

        @Override
        public void enterInstruction(SomeLanguageParser.InstructionContext ctx) {
            String instructionName = ctx.getText();

            instructionDesc.append(instructionName);
            if (instructionName.equals(";")) {
                instruction.add(new Instruction(instructionDesc.toString()));
                instructionDesc.delete(0, instructionDesc.length());
            }
        }

        public Collection<Instruction> getInstruction() {
            return instruction;
        }
    }
}