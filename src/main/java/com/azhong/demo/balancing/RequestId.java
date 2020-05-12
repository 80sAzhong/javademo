package main.java.com.azhong.demo.balancing;

import java.util.concurrent.atomic.AtomicInteger;

public class RequestId {
    private  static AtomicInteger requestId=new AtomicInteger(-1);
    public static int getRequestId(){
        return requestId.incrementAndGet();
    }
}
