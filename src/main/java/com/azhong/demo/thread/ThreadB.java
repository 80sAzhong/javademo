package main.java.com.azhong.demo.thread;


public class ThreadB implements Runnable {

    @Override
    public void run() {
        System.out.println("实现Runnable接口，重写run方法创建线程。。。");
    }

}
