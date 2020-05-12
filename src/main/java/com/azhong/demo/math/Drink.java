package main.java.com.azhong.demo.math;

/**
 * 递归的应用:
 * 1.假设有50瓶饮料，喝完三个空瓶可以换一瓶饮料，依次类推，请问总共喝了多少饮料
 */
public class Drink {
    public static int countDrink(int buyCount,int duihuan,int sendPer){
        if (buyCount<duihuan)
                return buyCount;
        int sendCount=(buyCount/duihuan)*sendPer;
        int left = buyCount%duihuan;
        return (buyCount-left)+countDrink(left+sendCount,duihuan,sendPer);
    }

    public static int countDrink2(int buyCount,int duihuan,int sendPer){
        int n=buyCount;
        int i=0;
        while(true){
            buyCount-=duihuan;
            buyCount+=sendPer;
            i+=sendPer;
            if(buyCount<duihuan){
                return n+i;
            }
        }
    }
    public static void main(String[] args) {
        int buyCount=50;
        int duihuan=3;
        int sendPer=1;
        int count =countDrink(buyCount,duihuan,sendPer);
        System.out.println("总共喝了:"+count+"瓶，其中总共兑换了:"+(count-buyCount));
        int count2 =countDrink2(buyCount,duihuan,sendPer);
        System.out.println("总共喝了:"+count2+"瓶，其中总共兑换了:"+(count2-buyCount));

    }
}
