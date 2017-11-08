package Utils;

import Entity.ClonePair;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Comparator;
/**
 * @Author: YanMing
 * @Description: 针对方法的工具类
 * @Date: 2017/10/28 21:24
 *
 */
public class MethodUtils {


    public String StmntInText(ArrayList<Token> tokens) {
        /**
         * @Description: 以文本形式展示方法的语句
         * @param tok   ens :token列表
         *
         */
        StringBuffer res = new StringBuffer();
        for (Token t : tokens) {
            res.append(t.getText() + " ");
        }
        return res.toString();
    }

    public String StmntInType(ArrayList<Token> tokens) {
        /**
         * @Description: 以类型信息(数字)展示语句
         * @param tokens :token列表
         *
         */
        StringBuffer res = new StringBuffer();
        for (Token t : tokens) {
            res.append(t.getType() + " ");
        }
        return res.toString();
    }


    public ArrayList<Token> splitStatement(ArrayList<Token> rawStatement) {
        /**
         * @Description: 将方法的token列表，以{,},;为分隔符分隔为语句
         * @param rawStatement ：未分割前的token列表
         *
         */
        ArrayList<Token> statement = new ArrayList<>();
        for (Token t : rawStatement) {
            if (!t.getText().equals(";") && !t.getText().equals("{") && !t.getText().equals("}")) {
                statement.add(t);
            }
        }
        return statement;
    }


    /**
     * @Author: YanMing
     * @Description: 比较克隆对顺序的比较器
     * @Date: 2017/10/28 21:50
     *
     */
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
