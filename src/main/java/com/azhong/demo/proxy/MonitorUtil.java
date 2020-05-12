package main.java.com.azhong.demo.proxy;

/**
 * @description: 方法耗时统计工具类
 * @copyright: Copyright (c) 2020
 * @author: azhong
 * @version: 1.0
 * @date: 2020年5月3日 上午11:36:30
 */
public class MonitorUtil {
private static ThreadLocal<Long> t1 = new ThreadLocal<Long>();
//设置起始时间
public static void start(){
    t1.set(System.currentTimeMillis());
}
//结束时打印耗时
public static void finish(String methodName){
    long finishTime = System.currentTimeMillis();
    System.out.println(methodName+"方法耗时:"+(finishTime-t1.get())+"ms");
}
}

