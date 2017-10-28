package Utils;

import Entity.CloneCode;
import Entity.ClonePair;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Stack;


public class SWSq {
    private int[][] H;
    private int[][] Travel;
    private static int SPACE ;
    private static int MATCH ;
    private static int DISMACH;
    private ArrayList<Integer> niceIndexM;
    private ArrayList<Integer> niceIndexN;
    private ArrayList<Loc> locs;

    private final int threshold = 10;

    private ArrayList<ClonePair> clonePairs;

    public SWSq(){
        locs = new ArrayList<>();
        clonePairs = new ArrayList<>();
        niceIndexN = new ArrayList<>();
        niceIndexM = new ArrayList<>();
        SPACE = -1;
        MATCH = 1;
        DISMACH = -1;
    }

    public void clearAll(){
        locs.clear();
        clonePairs.clear();
        niceIndexM.clear();
        niceIndexN.clear();
    }



    public int max(int a, int b, int c){
        int maxN;
        if(a >= b)
            maxN = a;
        else
            maxN = b;
        if(maxN < c)
            maxN = c;
        if(maxN < 0)
            maxN = 0;
        return maxN;
    }

    public void calculateMatrix(ArrayList<Token> s1, ArrayList<Token> s2, int m, int n){//计算得分矩阵

        for(int i = 1 ; i<m;i++)
        {
            for(int j = 1;j<n;j++)
            {
                if(s1.get(i-1).getType() == s2.get(j-1).getType())
                    H[i][j] = max(H[i - 1][j - 1] + MATCH, H[i][j - 1] + SPACE, H[i - 1][j] + SPACE);
                else
                    H[i][j] = max(H[i - 1][j - 1] + DISMACH, H[i][j - 1] + SPACE, H[i - 1][j] + SPACE);
            }

        }

    }

    public void printMatrix(int m,int n){
        for(int i = 0 ; i <m;i++)
        {
            for(int j = 0 ; j <n;j++)
            {
                System.out.print(H[i][j]);
            }
            System.out.println();
        }
    }

    public void findMaxIndex(int[][] H, int m, int n){//找到得分矩阵H中得分最高的元组的下标

        for(int i = 1; i < m; i++)
            for(int j = 1; j < n; j++)
                if(H[i][j] >= threshold){
                    locs.add(new Loc(i,j,H[i][j]));
                    //max = H[i][j];
                    //niceIndexM.add(i-1);
                    //niceIndexN.add(j-1);
                }
        locs.sort(new LocComparator());
    }
    public void traceBack(int m,int n){//回溯 寻找最相似子序列
       /*for(int i = 0;i<m.size();i++)
       {
           int mindex = m.get(i);
           int nindex = n.get(i);

           int tmpM = mindex;
           int tmpN = nindex;

           while (tmpM>=0&&tmpN>=0&&H[tmpM][tmpN] != 0)
           {
               if(H[tmpM][tmpN] == H[tmpM-1][tmpN] + SPACE) {
                   tmpM = tmpM - 1;
               }
               else if(H[tmpM][tmpN] == H[tmpM][tmpN-1] + SPACE) {
                  tmpN = tmpN - 1;
               }
               else {
                  tmpM = tmpM - 1;
                  tmpN = tmpN - 1;
               }
           }
           CloneCode codeA = new CloneCode(tmpM,mindex);
           CloneCode codeB = new CloneCode(tmpN,nindex);

           ClonePair clonePair = new ClonePair(codeA,codeB);
           clonePairs.add(clonePair);
       }
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
            if(Travel[tmpM][tmpN] == 0) {
                Stack<GenericPair> trace = new Stack<>();
                boolean traveled = false;
                while (H[tmpM][tmpN] != 1) {
                    if(Travel[tmpM][tmpN] == 1)
                    {
                        while (!trace.empty())
                        {
                            GenericPair pair = trace.pop();
                            int mindex = (Integer) pair.getFirst();
                            int nindex = (Integer) pair.getSecond();
                            Travel[mindex][nindex] = 0;
                        }
                        traveled = true;
                        break;
                    }
                    else {
                        Travel[tmpM][tmpN] = 1;
                        trace.push(new GenericPair(new Integer(tmpM),new Integer(tmpN)));
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
                if(!traveled&&tmpM!=0&&tmpN!=0){
                    CloneCode codeA = new CloneCode(tmpM-1,loc.m-1);
                    CloneCode codeB = new CloneCode(tmpN-1,loc.n-1);

                    ClonePair clonePair = new ClonePair(codeA,codeB);
                    clonePairs.add(clonePair);
                }
            }
        }
    }

    public void find(ArrayList<Token> s1, ArrayList<Token> s2){
        //initMatrix(s1.length(), s2.length());
        int HM = s1.size() + 1;
        int HN = s2.size() + 1;
        H = new int[HM][HN];
        for(int i = 0 ; i<HM;i++)
        {
            H[i][0] = 0;
        }
        for(int j = 0 ; j<HN;j++)
        {
            H[0][j] = 0;
        }
        calculateMatrix(s1, s2, HM, HN);
        findMaxIndex(H, HM, HN);
        traceBack(HM,HN);
    }

    public ArrayList<ClonePair> getClonePairs() {
        return clonePairs;
    }

    class Loc{
        int m;
        int n;
        int matrixVal;
        public Loc(int m,int n,int matrixVal){
            this.m = m;
            this.n = n;
            this.matrixVal = matrixVal;
        }
    }

    class LocComparator implements Comparator<Loc>{
        @Override
        public int compare(Loc o1, Loc o2) {
            if(o1.matrixVal >o2.matrixVal)
            {
                return -1;
            }
            else if(o1.matrixVal < o2.matrixVal)
            {
                return 1;
            }
            else {
                if(o1.m > o2.m)
                    return -1;
                else if(o1.m<o2.m)
                    return 1;
                else
                    return 0;
            }
        }
    }
}
