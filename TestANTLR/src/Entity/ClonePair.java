package Entity;

import Entity.CloneCode;
/**
 * @Author: YanMing
 * @Description: 表示代码克隆对，包含两个克隆代码段实例
 * @Date: 2017/10/28 21:13
 *
 */
public class ClonePair {
    private CloneCode code1;
    private CloneCode code2;
    private int length;

    public ClonePair(CloneCode code1,CloneCode code2)
    {
        this.code1 = code1;
        this.code2 = code2;
        this.length = code1.getLength() > code2.getLength() ? code1.getLength() : code2.getLength();
    }

    public CloneCode getCode1() {
        return code1;
    }

    public CloneCode getCode2() {
        return code2;
    }

    public int getLength() {
        return length;
    }

}
