public class A {

    public int a;
    public int b;

    @Repository
    public int func1(int a,int b){
        if(a>b)
        {
            return a - b;
        }
        else
            return b - a;
        a = a + 100;
        b = b + 100;
        int a1;
        int b1;
        if(a1>b1)
        {
            return a1 * b1;
        }
        else
            return b1 / a1;

    }

    public int func2(int a,int b){
        a = a + 100;
        b = b + 100;
        if(a<b)
        {
            return a + b;
        }
        else
            return b - a;
    }
}