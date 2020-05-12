package main.java.com.azhong.demo.jvm;

import org.openjdk.jol.info.ClassLayout;

public class EmptyObject {
    int a=10;
    int[] c= {1,6,9};
    int b=20;
    public static void main(String[] args) {
        EmptyObject object = new EmptyObject();
        System.out.println(ClassLayout.parseInstance(object).toPrintable());
    }
}
