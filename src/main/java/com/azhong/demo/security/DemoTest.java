package com.azhong.demo.security;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DemoTest {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("openId", "test_opid3");
        map.put("timestamp", "1586394796389");
        map.put("merchantId", "default_merchant");
        List<Map.Entry<String, String[]>> infoIds = new ArrayList(map.entrySet());
        infoIds.sort(Comparator.comparing(Map.Entry::getKey));
        for (Entry<String, String[]> entry : infoIds) {
            System.out.println(entry.getKey() + "=>" + entry.getValue());
        }
    }
}
