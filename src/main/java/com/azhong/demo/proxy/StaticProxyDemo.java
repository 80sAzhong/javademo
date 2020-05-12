package main.java.com.azhong.demo.proxy;
/**
 * @description: 静态代理：由程序员创建或特定工具自动生成源代码，
 * 也就是在编译时就已经将接口，被代理类，代理类等确定下来。在程序运行之前，
 * 代理类的.class文件就已经生成。
 * @copyright: Copyright (c) 2020
 * @author: azhong
 * @version: 1.0
 * @date: 2020年5月3日 上午11:05:53
 */
public class StaticProxyDemo {

    public static void main(String[] args) {
        Person s1 = new Student("张三");
        //生成代理对象，并把张三传递给代理对象
        Person s1Proxy = new StudentProxy(s1);
        //班长代理上交班费
        s1Proxy.giveMoney();
    }
}

interface Person {

    //上交班费
    void giveMoney();
}

class Student implements Person {

    private String name;

    public Student(String name) {
        super();
        this.name = name;
    }

    @Override
    public void giveMoney() {
        System.out.println(name + "上交班费50元");
    }

}

class StudentProxy implements Person {

    //被代理的学生
    Student stu;

    public StudentProxy(Person stu) {
        super();
        if (stu.getClass() == Student.class) {
            this.stu = (Student) stu;
        }
    }

    @Override
    public void giveMoney() {
        System.out.println("代理对象执行代理对象的方法...");
        stu.giveMoney();
    }

}
