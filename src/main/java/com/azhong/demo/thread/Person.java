package main.java.com.azhong.demo.thread;

import javax.swing.plaf.synth.SynthColorChooserUI;

public class Person {
private  String  name;
private String pwd;
private int age;

public Person(String name, String pwd, int age) {
    super();
    this.name = name;
    this.pwd = pwd;
    this.age = age;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String getPwd() {
    return pwd;
}

public void setPwd(String pwd) {
    this.pwd = pwd;
}

public int getAge() {
    return age;
}

public void setAge(int age) {
    this.age = age;
}

@Override
public String toString() {
    return "Person [name=" + name + ", pwd=" + pwd + ", age=" + age + "]";
}

}
