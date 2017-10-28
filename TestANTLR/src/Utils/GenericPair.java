package Utils;
/**
 * @Author: YanMing
 * @Description: 自定义Pair类型
 * @Date: 2017/10/28 21:23
 *
 */
public class GenericPair<E extends Object, F extends Object> {
    private E first;
    private F second;

    public GenericPair(){

    }

    public GenericPair(E first,F second){
        this.first = first;
        this.second = second;
    }

    public E getFirst() {
        return first;
    }
    public void setFirst(E first) {
        this.first = first;
    }
    public F getSecond() {
        return second;
    }
    public void setSecond(F second) {
        this.second = second;
    }


}