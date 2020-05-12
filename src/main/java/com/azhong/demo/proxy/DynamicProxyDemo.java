package main.java.com.azhong.demo.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @description: 动态代理 代理类在程序运行时创建的代理方式被成为动态代理
 * 动态代理的优势在于可以很方便的对代理类的函数进行统一的处理，而不用修改每个代理类中的方法。
 * jdk自带的动态代理方式:是通过在InvocationHandler中的invoke方法调用的，
 * 所以我们只要在invoke方法中统一处理，就可以对所有被代理的方法进行相同的操作了。
 * Spring采用Cglib实现动态代理:
 * （1）JDK动态代理只能对实现了接口的类生成代理，而不能针对类
 *  （2）CGLIB是针对类实现代理，主要是对指定的类生成一个子类，覆盖其中的方法
 *    因为是继承，所以该类或方法最好不要声明成final .
 *    如果目标对象没有实现了接口，必须采用CGLIB库，spring会自动在JDK动态代理和CGLIB之间转换
 * @copyright: Copyright (c) 2020
 * @author: azhong
 * @version: 1.0
 * @date: 2020年5月3日 上午11:13:31
 */
public class DynamicProxyDemo {

    public static void main(String[] args) {
        Person1 p1 = new Student1("张三");
        MyInvocationHandler<Person1> stuHandler = new MyInvocationHandler<Person1>(p1);
        Person1 stuProxy = (Person1) stuHandler.getProxyObj(p1);//(Person1) Proxy.newProxyInstance(Person1.class.getClassLoader(), new Class<?>[] { Person1.class }, stuHandler);
        stuProxy.giveMoney();
        Student2 s2 = new Student2("李四","社会大学");
        CglibProxy<Student2> cglibProxy = new CglibProxy<>();
        Student2 s2Proxy=cglibProxy.getCglibProxy(s2);
        s2Proxy.getCard();
    }
}

interface Person1 {
    static int DEMO=1;
    void giveMoney();
}

class Student1 implements Person1 {

    String name;

    public Student1(String name) {
        super();
        this.name = name;
    }

    @Override
    public void giveMoney() {
        //假设方法执行需要1s（为后面统计方法执行时间做准备）
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(name + "上交班费50元");
    }

}

class MyInvocationHandler<T> implements InvocationHandler {

    //invocationHandler持有的被代理对象
    T target;

    public MyInvocationHandler(T target) {
        super();
        this.target = target;
    }

    /**
     * (非 Javadoc)
     * <p>
     * Title: 代理类执行被代理的方法
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param proxy ：代表动态代理对象
     * @param method ：代表正在执行的方法
     * @param args ：代表调用目标方法时传入的实参
     * @return
     * @throws Throwable
     * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
     *      java.lang.reflect.Method, java.lang.Object[])
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理执行:" + method.getName() + "方法");
        MonitorUtil.start();
        Object result = method.invoke(target, args);
        MonitorUtil.finish(method.getName());
        return result;
    }
    
    /**
     * @description: TODO (简单说明如何使用，以及其它有助于快速、正确使用它的有关信息)
     * @author: azhong
     * @version: 1.0
     * @date: 2020年5月3日 下午4:50:49
     * @param target
     * @return
     */
    public Object getProxyObj(Object target){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

}

class CglibProxy<T> implements MethodInterceptor{
    T target;

    /**
     * 采用Cglib的方式生成动态代理并执行被代理的方法
     * @param o
     * @param method
     * @param arr
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] arr, MethodProxy methodProxy) throws Throwable {
        System.out.println("Cglib动态代理，监听开始...");
        MonitorUtil.start();
        Object invoke = method.invoke(target,arr);//方法执行
        MonitorUtil.finish(method.getName());
        return invoke;
    }

    //定义获取代理对象
    public T getCglibProxy(T objTarget){
        this.target=objTarget;
        Enhancer enhancer = new Enhancer();
        //设置父类，因为Cglib是针对指定的类生成一个子类
        enhancer.setSuperclass(objTarget.getClass());
        enhancer.setCallback(this);//设置回调
        Object result = enhancer.create();//创建并返回代理对象
        return (T)result;
    }
}

class Student2{
    String name;
    String sName;
    public Student2(){}
    public Student2(String name, String sName) {
        this.name = name;
        this.sName = sName;
    }

    public void getCard(){
        //假设方法执行需要1s（为后面统计方法执行时间做准备）
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(name+"毕业于"+sName);
    }
}