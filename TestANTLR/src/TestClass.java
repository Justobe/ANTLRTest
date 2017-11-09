import Entity.CloneCode;
import Entity.ClonePair;
import Entity.MethodDef;
import Traverse.Java8Lexer;
import Traverse.Java8Parser;
import Traverse.Java8VisitorParser;
import Utils.MethodUtils;
import Utils.SWSq;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TestClass {
    public static void main(String args[]) {
        TestClass mainClass = new TestClass();
        MethodUtils methodUtils = new MethodUtils();


        String codeA = mainClass.readContent("A.java");

        ParseTree parseTreeA = mainClass.getParseTree(codeA).packageDeclaration();

        Java8VisitorParser JParserA = new Java8VisitorParser();
        JParserA.visit(parseTreeA);

        System.out.println(JParserA.getPackageName());


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
