package main.java.com.azhong.demo.thread;

public class ThreadA extends Thread {

    @Override
    public void run() {
        System.out.println("继承Thread方式创建线程");
    }
}
