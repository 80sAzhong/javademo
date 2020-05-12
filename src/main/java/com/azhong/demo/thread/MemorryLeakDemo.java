package main.java.com.azhong.demo.thread;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class MemorryLeakDemo {
public static void main(String[] args) {
    Set<Person> set = new HashSet<Person>();
    Person p1 = new Person("张三","pwd1",30);
    Person p2 = new Person("李四","pwd2",40);
    Person p3 = new Person("王五","pwd3",50);
    set.add(p1);
    set.add(p2);
    set.add(p3);
    System.out.println("总共有:"+set.size()+"个元素");//3
    for(Person p :set){
        System.out.println(p);
    }
    p3.setAge(42);
    set.remove(p3);
    System.out.println("总共有:"+set.size()+"个元素");//2
    for(Person p :set){
        System.out.println(p);
    }
    set.add(p3);
    System.out.println("总共有:"+set.size()+"个元素");//3
    for(Person p :set){
        System.out.println(p);
    }
    Vector v = new Vector(10);
    for(int i=0;i<100;i++){
        Object o = new Object();
        v.add(o);//此时，所有的Object对象都没有被释放，因为变量v引用这些对象。
        o=null;//仅仅释放引用本身，那么Vector仍然引用该对象，所以这个对象对GC来说是不可回收的
    }
}
}

