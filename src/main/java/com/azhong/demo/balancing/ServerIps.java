package main.java.com.azhong.demo.balancing;

import java.util.*;

public class ServerIps {
    public static List<String> IP_LIST= Arrays.asList(
            "192.168.2.1",
            "192.168.2.2",
            "192.168.2.3",
            "192.168.2.4",
            "192.168.2.5",
            "192.168.2.6");

    public static Map<String,Integer> WEIGHT_LIST=new LinkedHashMap<String,Integer>();
    static{
        WEIGHT_LIST.put("192.168.2.1",2);
        WEIGHT_LIST.put("192.168.2.6",5);
        WEIGHT_LIST.put("192.168.2.9",3);
    };
}
