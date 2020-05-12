package main.java.com.azhong.demo.thread;

import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Thread t1 = new ThreadA();
        t1.start();
        Thread t2 = new Thread(new ThreadB());
        t2.start();
        FutureTask<Integer> task = new FutureTask<>(new ThreadC());
        Thread t3 = new Thread(task);
        t3.start();
        try {
            Integer result=(Integer) task.get();
            System.out.println("callable 执行完毕，返回："+result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future future = executor.submit(new ThreadC());
        //Thread.sleep(4000);
        Lock lock = new ReentrantLock();
        System.out.println("线程池中callable 执行完毕，返回："+future.get());
    }
}
