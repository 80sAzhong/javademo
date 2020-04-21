package com.azhong.demo.cm;

import java.net.Socket;
import java.util.Date;

public class IOClient {

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 8000);
                while (true) {
                    try {
                        System.out.println("往8000端口发送数据---");
                        socket.getOutputStream().write((new Date() + ":" + "hello world").getBytes());
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
