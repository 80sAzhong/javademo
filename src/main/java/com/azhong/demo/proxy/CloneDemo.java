package main.java.com.azhong.demo.proxy;

public class CloneDemo implements Cloneable
{
    @Override
    public Object clone() throws CloneNotSupportedException {
        CloneDemo cd = (CloneDemo)super.clone();
        return cd;
    }
}
