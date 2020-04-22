package com.azhong.demo.hash;

import java.util.HashMap;

/**
 * 
 * @description: 重写hash和equals
 * 如果某个类没有重写hashcode方法的话，equals判断两个值相等，但是hashcode的值不相等，如String类，这样就会造成歧义
 * @copyright: Copyright (c) 2020
 * @author: Administrator
 * @version: 1.0
 * @date: 2020年4月22日 下午5:11:06
 */
public class OverriedHashAndEquals {
    public static void main(String[] args) {
        Key k1 = new Key(1);
        Key k2 = new Key(1);
        HashMap<Key,String> hm = new HashMap<Key,String>();
        //把k1和一串字符放入到hm里
        hm.put(k1, "key with id is 1");
        System.out.println("k1==k2?"+(k1.equals(k2)));
        //想用k2去从HashMap里得到值；这就好比我们想用k1这把钥匙来锁门，用k2来开门
        System.out.println(hm.get(k2));//返回结果不是我们想象中的那个字符串，而是null
        // 原因有两个：第一是没有重写hashCode方法，第二是没有重写equals方法。
        /*
         * 当我们往HashMap里放k1时，首先会调用Key这个类的hashCode方法计算它的hash值，
         * 随后把k1放入hash值所指引的内存位置。关键是我们没有在Key里定义hashCode方法。这
         * 里调用的仍是Object类的hashCode方法（所有的类都是Object的子类），而Object
         * 类的hashCode方法返回的hash值其实是k1对象的内存地址（假设是1000）。 但我们这里
         * 的代码是hm.get(k2)，当我们调用Object类的hashCode方法（因为Key里没定义）
         * 计算k2的hash值时，其实得到的是k2的内存地址（假设是2000）。由于k1和k2是两个不
         * 同的对象，所以它们的内存地址一定不会相同，也就是说它们的hash值一定不同，这就是我们
         * 无法用k2的hash值去拿k1的原因。
         * 
         * 当我们把Key类重写hashCode方法的注释去掉后，会发现它是返回id属性的hashCode值，
         * 这里k1和k2的id都是1,所以它们的hash值是相等的。
         */
    }
}
class Key{
    private Integer id;

    
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }


    public Key(Integer id) {
        super();
        this.id = id;
    }

  //先故意先注释掉equals和hashCode方法
    @Override
    public int hashCode() {
        //原生重写hashCode
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ((id == null) ? 0 : id.hashCode());
//        return result;
        //自定义hashCode(Integerl类hashCode返回的就是本身的值)
        return id.hashCode();
    }

/*
 *  当我们通过k2的hashCode到100号位置查找时，确实会得到k1。但k1有可能仅仅是和k2具有相同的hash值，
 *  但未必和k2相等（k1和k2两把钥匙未必能开同一扇门），这个时候，就需要调用Key对象的equals方法来判断两者是否相等了。
 *  equals方法必须要满足以下几个特性

　　1.自反性：x.equals(x) == true,自己和自己比较相等

　　2.对称性：x.equals(y) == y.equals(x),两个对象调用equals的的结果应该一样

　　3.传递性：如果x.equals(y) == true y.equals(z) == true 则 x.equals(z) == true,x和y相等，y和z相等，则x和z相等

　　4.一致性 : 如果x对象和y对象有成员变量num1和num2，其中重写的equals方法只有num1参加了运算，则修改num2不影响x.equals(y)的值
 */
    @Override
    public boolean equals(Object obj) {
        //原生重写equals方法
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        if (getClass() != obj.getClass())
//            return false;
//        Key other = (Key) obj;
//        if (id == null) {
//            if (other.id != null)
//                return false;
//        } else if (!id.equals(other.id))
//            return false;
//        return true;
        if(obj==null||!(obj instanceof Key)){
            return false;
        }else{
            return this.getId().equals(((Key) obj).getId());
        }
    }
    
    
}