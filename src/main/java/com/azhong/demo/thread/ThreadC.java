package main.java.com.azhong.demo.thread;

import java.util.concurrent.Callable;


public class ThreadC implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        synchronized(this){
            try {
                this.wait(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
          }
        System.out.println("实现callable接口，重写call方法，创建线程...");
        return 1;
    }

}
