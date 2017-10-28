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
        CloneCode c1 = new CloneCode(1,3);
        CloneCode c2 = new CloneCode(5,7);
        CloneCode c3 = new CloneCode(2,4);
        CloneCode c4 = new CloneCode(9,12);

        ClonePair cp1 = new ClonePair(c1,c3);
        ClonePair cp2 = new ClonePair(c2,c4);
        ArrayList<ClonePair> clonePairs = new ArrayList<>();
        clonePairs.add(cp1);clonePairs.add(cp2);

        MethodUtils methodUtils = new MethodUtils();
        methodUtils.getValidPair(clonePairs,0,0);

        for(ClonePair cp:clonePairs)
        {
            System.out.println(cp.getCode1().toString()+" "+cp.getCode2().toString()+" "+cp.getLength());
        }


    }


}
