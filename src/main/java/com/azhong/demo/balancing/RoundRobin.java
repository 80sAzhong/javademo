package main.java.com.azhong.demo.balancing;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 轮询算法
 */
public class RoundRobin {
    public static Integer pos = 0;
    public static ThreadLocal<Map<String, Weight>> ThreadServerMap = new ThreadLocal<Map<String, Weight>>();

    static {
        ThreadServerMap.set(new LinkedHashMap<String, Weight>());
    }

    /**
     * 普通轮询算法
     *
     * @return
     */
    public static String getServerIp() {
        String ip = null;
        synchronized (pos) {
            if (pos >= ServerIps.IP_LIST.size()) {
                pos = 0;
            }
            ip = ServerIps.IP_LIST.get(pos);
            pos++;
        }
        return ip;
    }

    /**
     * 加权轮询算法
     *
     * @return
     */
    public static String getWeightIp() {
        int totalWeigh = 0;
        for (Integer value : ServerIps.WEIGHT_LIST.values()) {
            totalWeigh += value;
        }
        int pos = RequestId.getRequestId() % totalWeigh;
        for (String key : ServerIps.WEIGHT_LIST.keySet()) {
            int curw = ServerIps.WEIGHT_LIST.get(key);
            if (pos < curw) {
                return key + "=>" + curw;
            } else {
                pos = pos - curw;
            }
        }
        return "";
    }

    /**
     * 平滑加权算法
     * 服务器列表比如:{"A",3},{"B",5},{"C",2}
     * 则加权列表为(3,5,2)
     * 服务器:
     * ip :A,B,C
     * weight: 静态权重(3,5,2)
     * currentWeight:动态权重(0,0,0)
     * |currentWeight=currentWeight+weight|max(currentWeight)|ip|currentWeight=currentweight-toal(weight)|
     * |3,5,2|5|B|3,-5,2|
     * |6,0,4|6|A|-4,0,4|
     * |-1,5,6|6|C|-1,5,-4|
     * |2,10,-2|10|B|2,0,-2|
     * |5,5,0|5|A|-5,5,0|
     * |-2,10,2|10|B|-2,0,2|
     * |1,5,4|5|B|1,-5,4|
     * |4,0,6|6|C|4,0,-4|
     * |7,5,-2|7|A|-3,5,-2|
     * |0,10,0|10|B|0,0,0|
     *
     * @param args
     */
    public static String getSmoothWeightIp() {
        //1.统计总权重
        int totalWeigh = 0;
        for (Integer value : ServerIps.WEIGHT_LIST.values()) {
            totalWeigh += value;
        }
        //2.初始化serverMap
        Map<String, Weight> serverMap = ThreadServerMap.get();
        if (serverMap.isEmpty()) {
            ServerIps.WEIGHT_LIST.forEach((key, value) -> serverMap.put(key, new Weight(key, value, 0)));
        }
        //3.循环设置动态权重currentWeight=currentWeight+weight
//        for (Weight weight : serverMap.values()) {
//            System.out.println("befor:" + weight);
//            weight.setCurrentWeight(weight.getWeight() + weight.getCurrentWeight());
//            System.out.println("after:" + weight);
//        }
        serverMap.forEach((key,value)->value.setCurrentWeight(value.getCurrentWeight()+value.getWeight()));
        //4.取出当前最大的动态权重max(currentWeight)
        Weight currentMaxWeight = null;
        for (Weight weight : serverMap.values()) {
            if (currentMaxWeight == null || weight.getCurrentWeight() > currentMaxWeight.getCurrentWeight()) {
                currentMaxWeight = weight;
            }
        }

        //5.重新计算动态权重currentWeight=currentweight-toal(weight)
        currentMaxWeight.setCurrentWeight(currentMaxWeight.getWeight() - totalWeigh);
        //6.返回最大的动态权重对应的IP，return maxWeightIP
        return currentMaxWeight.getIp() + "=>" + currentMaxWeight.getWeight();
    }


    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getSmoothWeightIp());
        }
    }
}
