package main.java.com.azhong.demo.balancing;

public class Weight {
    public String ip;//服务地址
    public Integer weight;//静态权重(初始配置的权重)
    public Integer currentWeight;//动态权重

    public Weight(String ip, Integer weight, Integer currentWeight) {
        this.ip = ip;
        this.weight = weight;
        this.currentWeight = currentWeight;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(Integer currentWeight) {
        this.currentWeight = currentWeight;
    }

    @Override
    public String toString() {
        return "Weight{" +
                "ip='" + ip + '\'' +
                ", weight=" + weight +
                ", currentWeight=" + currentWeight +
                '}';
    }
}
