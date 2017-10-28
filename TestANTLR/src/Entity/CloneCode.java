package Entity;

public class CloneCode {
    private int sindex;
    private int eindex;
    private int length;

    public CloneCode (int s,int e){
        this.sindex = s;
        this.eindex = e;
        this.length = e - s + 1;
    }

    public int getSindex() {
        return sindex;
    }

    public int getEindex() {
        return eindex;
    }

    public int getLength() {
        return length;
    }
}
