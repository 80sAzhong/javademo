package main.java.com.azhong.demo.balancing;

import java.util.Random;

/**
 * 随机负载均衡算法
 */
public class RandomBalance {
    public static String getIpByRandom(){
        Random random = new Random();
        int index= random.nextInt(ServerIps.IP_LIST.size());
        return ServerIps.IP_LIST.get(index);
    }

    /**
     * 权重随机算法
     * 将权重值当作一个区域，权重值越大，区域越大，随机落点如果小于权重值则就落在该区域里
     * 如 |0-1|2---4|5------9|
     * @return
     */
    public static String getWeightIpByRandom(){
        int totalWeight=0;
        for(Integer weight:ServerIps.WEIGHT_LIST.values())
        {
            totalWeight+=weight;
        }
        Random random = new Random();
        int pos = random.nextInt(totalWeight);
//        System.out.println("pos:"+pos);
        for(String key:ServerIps.WEIGHT_LIST.keySet())
        {
            int curWeight=ServerIps.WEIGHT_LIST.get(key);
//            System.out.println("curWeight:"+curWeight);
            if(pos<curWeight)
            {
                return key+"=>"+curWeight;
            }
                pos=pos-curWeight;
//            System.out.println("last_pos:"+pos);
        }
        return "";
    }

    public static void main(String[] args) {
        for(int i=0;i<20;i++) {
            System.out.println(getWeightIpByRandom());
        }
    }
}
