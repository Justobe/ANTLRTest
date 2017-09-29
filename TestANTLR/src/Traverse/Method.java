package Traverse;

import java.util.Collection;

/**
 * Created by YanMing on 2017/9/27.
 */
public class Method {
    private String methodName;
    private String[] parameters;
    private Collection<Instruction> instructions;

    public Method(String methodName, String[] parameters, Collection<Instruction> instructions) {
        this.methodName = methodName;
        this.parameters = parameters;
        this.instructions = instructions;
    }
}