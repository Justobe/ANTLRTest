package Utils;

import Entity.ClonePair;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Comparator;

public class MethodUtils {

    public String StmntInText(ArrayList<Token> tokens) {
        StringBuffer res = new StringBuffer();
        for (Token t : tokens) {
            res.append(t.getText() + " ");
        }
        return res.toString();
    }

    public String StmntInType(ArrayList<Token> tokens) {
        StringBuffer res = new StringBuffer();
        for (Token t : tokens) {
            res.append(t.getType() + " ");
        }
        return res.toString();
    }


    public ArrayList<Token> splitStatement(ArrayList<Token> rawStatement) {
        ArrayList<Token> statement = new ArrayList<>();
        for (Token t : rawStatement) {
            if (!t.getText().equals(";") && !t.getText().equals("{") && !t.getText().equals("}")) {
                statement.add(t);
            }
        }
        return statement;
    }

    public ArrayList<ClonePair> getValidPair(ArrayList<ClonePair> clonePairs,int lenA,int lenB) {
        clonePairs.sort(new CPComparator());
        int [] secA = new int [lenA+2];
        int [] secB = new int [lenB+2];
        return clonePairs;
        /*for(int i = 0;i<clonePairs.size();i++)
        {
            int code
        }*/

    }

    class CPComparator implements Comparator<ClonePair> {

        @Override
        public int compare(ClonePair cpA, ClonePair cpB) {
            if (cpA.getLength() > cpB.getLength()) {
                return -1;
            } else if (cpA.getLength() < cpB.getLength()) {
                return 1;
            } else {
                return 0;
            }
        }

    }
}
