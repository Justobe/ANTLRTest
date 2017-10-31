import Entity.CloneCode;
import Entity.ClonePair;
import Entity.MethodDef;
import Utils.MethodUtils;
import Utils.SWSq;
import Traverse.Java8Lexer;
import Traverse.Java8Parser;
import Traverse.Java8VisitorParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainClass {
    public static void main(String args[]){
        MainClass mainClass = new MainClass();
        MethodUtils methodUtils = new MethodUtils();
        SWSq sq = new SWSq();

        String codeA = mainClass.readContent("C.java");
        String codeB = mainClass.readContent("D.java");
        System.out.println(codeA);
        System.out.println(codeB);
        ParseTree parseTreeA = mainClass.getParseTree(codeA).normalClassDeclaration();
        ParseTree parseTreeB = mainClass.getParseTree(codeB).normalClassDeclaration();

        Java8VisitorParser JParserA = new Java8VisitorParser();
        JParserA.visit(parseTreeA);
        Java8VisitorParser JParserB = new Java8VisitorParser();
        JParserB.visit(parseTreeB);

        //System.out.println("Code A:" + JParserA.printStmntInText(JParserA.getRawStatement()));
        ArrayList<MethodDef> methodsA = JParserA.getMethodDefs();
        ArrayList<MethodDef> methodsB = JParserB.getMethodDefs();
        for (MethodDef methodA : methodsA) {
            for (MethodDef methodB : methodsB) {
                ArrayList<Token> tokensA = methodUtils.splitStatement(methodA.getbodyTokens());
                ArrayList<Token> tokensB = methodUtils.splitStatement(methodB.getbodyTokens());
                System.out.println(methodA.getClassName()+":"+methodA.getMethodHeader() +"<----->"+methodB.getClassName()+":"+methodB.getMethodHeader());

                sq.find(tokensA, tokensB);
                //sq.printMatrix(tokensA,tokensB);
                System.out.println();
                ArrayList<ClonePair> clonePairs = sq.getClonePairs();
                int t = 1;
                for (ClonePair cp : clonePairs) {
                    CloneCode cd1 = cp.getCode1();
                    CloneCode cd2 = cp.getCode2();

                    System.out.println("Code Clone Pair:" + (t++) + "=================================================");
                    for (int i = cd1.getSindex(); i <= cd1.getEindex(); i++) {
                        System.out.print(tokensA.get(i).getText() + " ");
                    }
                    System.out.println();
                    for (int i = cd1.getSindex(); i <= cd1.getEindex(); i++) {
                        System.out.print(tokensA.get(i).getType() + " ");
                    }
                    System.out.println();
                    for (int i = cd2.getSindex(); i <= cd2.getEindex(); i++) {
                        System.out.print(tokensB.get(i).getText() + " ");
                    }
                    System.out.println();
                    for (int i = cd2.getSindex(); i <= cd2.getEindex(); i++) {
                        System.out.print(tokensB.get(i).getType() + " ");
                    }
                    System.out.println();
                    System.out.println();
                }
                sq.clearAll();
            }
        }
    }

    public String readContent(String filename) {
        String dataDir = System.getProperty("user.dir") + "\\data\\";
        String filePath = dataDir + filename;
        File file = new File(filePath);
        String code = null;
        try {
            code = FileUtils.readFileToString(file, "UTF-8");
            return code;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Java8Parser getParseTree(String code) {
        ANTLRInputStream antlrInputStream = new ANTLRInputStream(code);
        Java8Lexer lexer = new Java8Lexer(antlrInputStream);
        CommonTokenStream commonStream = new CommonTokenStream(lexer);
        Java8Parser parseTree = new Java8Parser(commonStream);
        return parseTree;
    }
}
