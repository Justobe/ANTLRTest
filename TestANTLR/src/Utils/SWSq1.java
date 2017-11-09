package Utils;

/**
 * @Author: YanMing
 * @Description:
 * @Date: Created in 15:58 2017/11/7
 */

import Entity.CloneCode;
import Entity.ClonePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
//abcdefghijklmnopqrsjtuv
//abcxdefghiymzjlukpqsjtuv

public class SWSq1 {
    private int[][] M;
    private int[][] hm;
    private int[][] Travel;
    private static int SPACE;                      //空格匹配的得分
    private static int MATCH;                       //两个字母相同的得分
    private static int DISMACH;                    //两个字母不同的得分
    private int maxIndexM, maxIndexN;
    private final int THRESHOLD = 5;

    private Stack<Character> stk1, stk2;
    private ArrayList<Loc> Locs;
    private ArrayList<ClonePair> clonePairs; //所有检测出的代码克隆对列表

    public List<String> subSq1, subSq2;                        //相似度最高的两个子串

    public SWSq1() {
        stk1 = new Stack<Character>();
        stk2 = new Stack<Character>();
        subSq1 = new ArrayList<>();
        subSq2 = new ArrayList<>();
        Locs = new ArrayList<>();
        clonePairs = new ArrayList<>();
        SPACE = -1;
        MATCH = 1;
        DISMACH = -1;
    }

    private int max(int a, int b, int c) {
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

    private void calculateMatrix(String s1, String s2, int m, int n) {//计算得分矩阵
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                // 计算M矩阵
                if (s1.charAt(i - 1) == s2.charAt(j - 1))
                    M[i][j] = max(M[i - 1][j - 1] + MATCH, M[i][j - 1] + SPACE, M[i - 1][j] + SPACE);
                else
                    M[i][j] = max(M[i - 1][j - 1] + DISMACH, M[i][j - 1] + SPACE, M[i - 1][j] + SPACE);


                // 初始化计算 hm矩阵
                if (M[i][j] == 0) {
                    hm[i][j] = 0;
                } else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    hm[i][j] = Math.max(M[i - 1][j - 1], hm[i - 1][j - 1]);
                } else {
                    // 找到 (i,j)的父节点
                    if (M[i][j] == M[i - 1][j] + SPACE) {
                        hm[i][j] = Math.max(M[i - 1][j], hm[i - 1][j]);
                    } else if (M[i][j] == M[i][j - 1] + SPACE) {
                        hm[i][j] = Math.max(M[i][j - 1], hm[i][j - 1]);
                    } else {
                        hm[i][j] = Math.max(M[i - 1][j - 1], hm[i - 1][j - 1]);
                    }
                }

                if (hm[i][j] - M[i][j] >= THRESHOLD) {
                    hm[i][j] = 0;
                    M[i][j] = 0;
                }
                if (M[i][j] > THRESHOLD && M[i][j] > hm[i][j]) {
                    Locs.add(new Loc(i,j,M[i][j]));
                }



            }

        }
        LocComparator locComparator = new LocComparator();
        Locs.sort(locComparator);
    }

    private void findMaxIndex(int[][] H, int m, int n) {//找到得分矩阵H中得分最高的元组的下标
        int curM, curN, i, j, max;
        curM = 0;
        curN = 0;
        max = H[0][0];
        for (i = 0; i < m; i++)
            for (j = 0; j < n; j++)
                if (H[i][j] > max) {
                    max = H[i][j];
                    curM = i;
                    curN = j;
                }
        maxIndexM = curM;
        maxIndexN = curN;
    }

    private void traceBack(String s1, String s2, int m, int n) {//回溯 寻找最相似子序列
        Travel = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Travel[i][j] = 0;
            }
        }

        for(Loc loc:Locs)
        {
            int tmpM = loc.getRow();
            int tmpN = loc.getCol();
            // 判断当前取出的位置是否被访问过
            if (Travel[tmpM][tmpN] == 0) {
                //记录回溯路径
                Stack<GenericPair> trace = new Stack<>();
                boolean traveled = false;
                // 直到当前矩阵值为1，不断寻找前驱
                while (M[tmpM][tmpN] != 0) {
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
                        if (M[tmpM][tmpN] == M[tmpM - 1][tmpN] + SPACE) {
                            tmpM = tmpM - 1;
                        } else if (M[tmpM][tmpN] == M[tmpM][tmpN - 1] + SPACE) {
                            tmpN = tmpN - 1;
                        } else {
                            tmpM = tmpM - 1;
                            tmpN = tmpN - 1;
                        }
                    }
                }
                if (!traveled) {
                    CloneCode codeA = new CloneCode(tmpM, loc.getRow() - 1);
                    CloneCode codeB = new CloneCode(tmpN, loc.getCol() - 1);

                    ClonePair clonePair = new ClonePair(codeA, codeB);
                    clonePairs.add(clonePair);

                }
            }
        }
    }

    public void printMatrix(int[][] H) {
        for (int i = 0; i < H.length; i++) {
            for (int j = 0; j < H[i].length; j++) {
                System.out.print(H[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void find(String s1, String s2) {
        int ROW_NUM = s1.length() + 1;
        int COL_NUM = s2.length() + 1;
        M = new int[ROW_NUM][COL_NUM];
        hm = new int[ROW_NUM][COL_NUM];
        Travel = new int[ROW_NUM][COL_NUM];
        for (int i = 0; i < ROW_NUM; i++) {
            M[i][0] = hm[i][0] = 0;
        }
        for (int j = 0; j < COL_NUM; j++) {
            M[0][j] = hm[0][j] = 0;
        }
        for (int i = 0; i < ROW_NUM; i++) {
            for (int j = 0; j < COL_NUM; j++) {
                Travel[i][j] = 0;
            }
        }
        calculateMatrix(s1, s2, ROW_NUM, COL_NUM);
        //findMaxIndex(M, M.length, M[0].length);
        printMatrix(M);
        System.out.println();
        printMatrix(hm);
        traceBack(s1,s2,ROW_NUM,COL_NUM);
        for (ClonePair pair : clonePairs) {
            CloneCode codeA = pair.getCode1();
            CloneCode codeB = pair.getCode2();
            int aS = codeA.getSindex();
            int aE = codeA.getEindex();
            int bS = codeB.getSindex();
            int bE = codeB.getEindex();
            StringBuffer sbA = new StringBuffer();
            StringBuffer sbB = new StringBuffer();
            for(int i = aS;i<=aE;i++)
            {
                sbA.append(s1.charAt(i));
            }
            for(int j = bS;j<=bE;j++)
            {
                sbB.append(s2.charAt(j));
            }
            subSq1.add(sbA.toString());
            subSq2.add(sbB.toString());
        }
    }
    class Loc {
        private int row;
        private int col;
        private int matrixVal;

        public Loc(int m, int n, int matrixVal) {
            this.row = m;
            this.col = n;
            this.matrixVal = matrixVal;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        public int getMatrixVal() {
            return matrixVal;
        }
    }

    /**
     * @Author: YanMing
     * @Description: 矩阵位置比较器，矩阵值越大，或者越靠近右下角优先级越高
     * @Date: 2017/10/28 22:05
     */
    class LocComparator implements Comparator<SWSq1.Loc> {
        @Override
        public int compare(SWSq1.Loc o1, SWSq1.Loc o2) {
            if (o1.getMatrixVal() > o2.getMatrixVal()) {
                return -1;
            } else if (o1.getMatrixVal() < o2.getMatrixVal() ) {
                return 1;
            } else {
                if (o1.getRow() > o2.getRow())
                    return -1;
                else if (o1.getRow() < o2.getRow())
                    return 1;
                else
                    return 0;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        SWSq1 x = new SWSq1();
        String s1 = "abcdefghijklmnopqrsjtuv", s2 = "abcxdefghiymzjlukpqsjtuv";
        /*BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter one String:");
        s1 = br.readLine();
        System.out.println("Enter another String:");
        s2 = br.readLine();
        */
        System.out.println("String 1: " + s1);
        System.out.println("String 2: " + s2);
        x.find(s1, s2);
        for (int i = 0; i < x.subSq1.size(); i++) {
            System.out.println("The subsequences with greatest similarity are " + x.subSq1.get(i) + " and " + x.subSq2.get(i));

        }
    }
}