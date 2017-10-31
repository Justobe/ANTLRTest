package Utils;

import Entity.CloneCode;
import Entity.ClonePair;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Stack;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @Author: YanMing
 * @Description: 实现SW算法的类
 * @Date: 2017/10/28 21:51
 */
public class SWSq {
    private int[][] H; // SW算法中的计算序列相同值的矩阵
    private int[][] Travel; // 标记回溯过程的矩阵
    private static int SPACE; // SW算法参数1 gap值
    private static int MATCH; // SW算法参数2 
    private static int DISMACH; // SW算法参数3
    private ArrayList<Loc> locs; //记录矩阵中所有达到阈值的值的位置及矩阵值
    private final int threshold = 20; // 设置阈值
    private ArrayList<ClonePair> clonePairs; //所有检测出的代码克隆对列表

    public SWSq() {
        locs = new ArrayList<>();
        clonePairs = new ArrayList<>();
        SPACE = -1;
        MATCH = 1;
        DISMACH = -1;
    }

    public void clearAll() {
        /**
         * @Description: 清空位置信息和克隆对信息
         * @param
         *
         */
        locs.clear();
        clonePairs.clear();

    }


    public int max(int a, int b, int c) {
        int maxN;
        if (a >= b)
            maxN = a;
        else
            maxN = b;
        if (maxN < c)
            maxN = c;
        if (maxN < 0)
            maxN = 0;
        return maxN;
    }

    public void calculateMatrix(ArrayList<Token> s1, ArrayList<Token> s2, int m, int n) {
        /**
         * @Description: 计算得分矩阵
         * @param s1 ：比较的Token列表
         * @param s2
         * @param m 得分矩阵大小
         * @param n
         *
         */
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (s1.get(i - 1).getType() == s2.get(j - 1).getType())
                    H[i][j] = max(H[i - 1][j - 1] + MATCH, H[i][j - 1] + SPACE, H[i - 1][j] + SPACE);
                else
                    H[i][j] = max(H[i - 1][j - 1] + DISMACH, H[i][j - 1] + SPACE, H[i - 1][j] + SPACE);
            }

        }

    }

    public void printMatrix(ArrayList<Token> s1, ArrayList<Token> s2) {
        /**
         * @Description: 打印得分矩阵
         * @param m
         * @param n
         *
         */
        System.out.print("        ");
        for (int i = 0; i < s2.size(); i++) {
            System.out.printf("%4d", s2.get(i).getType());
        }
        System.out.println();
        for (int i = 0; i < s1.size() + 1; i++) {
            if (i > 0) {
                System.out.printf("%4d", s1.get(i - 1).getType());
            } else {
                System.out.printf("    ");
            }
            for (int j = 0; j < s2.size() + 1; j++) {
                System.out.printf("%4d", H[i][j]);
            }
            System.out.println();
        }
    }

    public void findNiceIndex(int[][] H, int m, int n) {
        /**
         * @Description: 计算得分矩阵中达到阈值的值得位置，作为候选克隆对
         * @param H ：得分矩阵
         * @param m
         * @param n
         *
         */
        for (int i = 1; i < m; i++)
            for (int j = 1; j < n; j++)
                if (H[i][j] >= threshold) {
                    locs.add(new Loc(i, j, H[i][j]));
                }
        locs.sort(new LocComparator());
    }

    public void traceBack(int m, int n) {//
        /**
         * @Description: 回溯 寻找代码克隆对
         * @param m
         * @param n
         *
         */
        Travel = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Travel[i][j] = 0;
            }
        }

        for (Loc loc : locs) {
            int tmpM = loc.m;
            int tmpN = loc.n;
            // 判断当前取出的位置是否被访问过
            if (Travel[tmpM][tmpN] == 0) {
                //记录回溯路径
                Stack<GenericPair> trace = new Stack<>();
                boolean traveled = false;
                // 直到当前矩阵值为1，不断寻找前驱
                while (H[tmpM][tmpN] != 0) {
                    //如果中途发现被访问过
                    if (Travel[tmpM][tmpN] == 1) {
                        //清空回溯路径并退出
                        while (!trace.empty()) {
                            GenericPair pair = trace.pop();
                            int mindex = (Integer) pair.getFirst();
                            int nindex = (Integer) pair.getSecond();
                            Travel[mindex][nindex] = 0;
                        }
                        traveled = true;
                        break;
                    } else {
                        //标记当前位置，记录回溯路径，同时寻找前驱
                        Travel[tmpM][tmpN] = 1;
                        trace.push(new GenericPair(new Integer(tmpM), new Integer(tmpN)));
                        if (H[tmpM][tmpN] == H[tmpM - 1][tmpN] + SPACE) {
                            tmpM = tmpM - 1;
                        } else if (H[tmpM][tmpN] == H[tmpM][tmpN - 1] + SPACE) {
                            tmpN = tmpN - 1;
                        } else {
                            tmpM = tmpM - 1;
                            tmpN = tmpN - 1;
                        }
                    }
                }
                //如果当前回溯成功同时，记录代码对信息
                if (!traveled) {
                    CloneCode codeA = new CloneCode(tmpM, loc.m - 1);
                    CloneCode codeB = new CloneCode(tmpN, loc.n - 1);

                    ClonePair clonePair = new ClonePair(codeA, codeB);
                    clonePairs.add(clonePair);

                }
            }
        }
    }

    public void find(ArrayList<Token> s1, ArrayList<Token> s2) {
        /**
         * @Description: 寻找s1，s2的克隆对
         * @param s1
         * @param s2
         *
         */
        int HM = s1.size() + 1;
        int HN = s2.size() + 1;
        H = new int[HM][HN];
        for (int i = 0; i < HM; i++) {
            H[i][0] = 0;
        }
        for (int j = 0; j < HN; j++) {
            H[0][j] = 0;
        }
        calculateMatrix(s1, s2, HM, HN);
        findNiceIndex(H, HM, HN);
        traceBack(HM, HN);
    }

    public ArrayList<ClonePair> getClonePairs() {
        return clonePairs;
    }

    /**
     * @Author: YanMing
     * @Description: 矩阵中值的位置及矩阵值
     * @Date: 2017/10/28 22:05
     */
    class Loc {
        int m;
        int n;
        int matrixVal;

        public Loc(int m, int n, int matrixVal) {
            this.m = m;
            this.n = n;
            this.matrixVal = matrixVal;
        }
    }

    /**
     * @Author: YanMing
     * @Description: 矩阵位置比较器，矩阵值越大，或者越靠近右下角优先级越高
     * @Date: 2017/10/28 22:05
     */
    class LocComparator implements Comparator<Loc> {
        @Override
        public int compare(Loc o1, Loc o2) {
            if (o1.matrixVal > o2.matrixVal) {
                return -1;
            } else if (o1.matrixVal < o2.matrixVal) {
                return 1;
            } else {
                if (o1.m > o2.m)
                    return -1;
                else if (o1.m < o2.m)
                    return 1;
                else
                    return 0;
            }
        }
    }
}
