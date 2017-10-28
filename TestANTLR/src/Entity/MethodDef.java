package Entity;

import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
/**
 * @Author: YanMing
 * @Description: 抽象出的方法定义
 * @Date: 2017/10/28 21:14
 *
 */
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
