package Entity;
/**
 * @Author: YanMing
 * @Description: 表示克隆代码段的实体，代码开始、结束位置和克隆代码长度
 * @Date: 2017/10/28 21:12
 *
 */
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
