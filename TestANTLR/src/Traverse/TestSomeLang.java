package Traverse;

import com.google.gson.Gson;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by YanMing on 2017/9/27.
 */
public class TestSomeLang {
    public static void main(String[] args) {
// listener 方式遍历
        TestSomeLang testSomeLang = new TestSomeLang();
        String code = testSomeLang.readContent();
        SomeLanguageParser parser = getParseTree(code);
        SomeClass someClass = new LangeListenerParser().parse(parser.classDeclaration());

        Gson gson = new Gson();
        System.out.println(gson.toJson(someClass));

        System.out.println("======================================\n visitor方式");

// visitor方式遍历
        SomeLanguageParser parser2 = getParseTree(code);
        SomeClass someClass1 = new SomeLangVisitorParser().parse(parser2.classDeclaration());
        String json = gson.toJson(someClass1);
        //System.out.println(gson.toJson(someClass1));

    }

    public String readContent() {
        File dir = null;
        try {
            /*dir = new File(getClass().getResource("/someLang").toURI());
            for (File file : dir.listFiles()) {
                if (!file.getName().equalsIgnoreCase("lang1")) {
                    continue;
                }
            */
            String path = "E:\\class.txt";
            File file = new File(path);
            String code = FileUtils.readFileToString(file, "utf-8");
                return code;
            } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    } /*catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    public static SomeLanguageParser getParseTree(String code) {
        ANTLRInputStream antlrInputStream = new ANTLRInputStream(code);
        SomeLanguageLexer lexer = new SomeLanguageLexer(antlrInputStream);
        CommonTokenStream commonStream = new CommonTokenStream(lexer);
        SomeLanguageParser parseTree = new SomeLanguageParser(commonStream);
        return parseTree;
    }

}
