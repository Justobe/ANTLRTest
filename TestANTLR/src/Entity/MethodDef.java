package Entity;

import org.antlr.v4.runtime.Token;

import java.util.ArrayList;

public class MethodDef {
    private String methodHeader;
    private String className;

    public String getMethodHeader() {
        return methodHeader;
    }


    private ArrayList<Token> bodyTokens;
    public MethodDef(ArrayList<Token> tokens, String methodHeader, String className){
        this.bodyTokens = tokens;
        this.methodHeader = methodHeader;
        this.className = className;
    }

    public void setbodyTokens(ArrayList<Token> tokens) {
        this.bodyTokens = tokens;
    }

    public ArrayList<Token> getbodyTokens() {
        return bodyTokens;
    }

    public String getClassName() {
        return className;
    }
}
