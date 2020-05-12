package main.java.com.azhong.demo.time;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class TimeDemo {
    public static void main(String[] args) {
        //取得年月日、小时分钟秒
        Calendar cal=Calendar.getInstance();
        System.out.println(cal.get(Calendar.YEAR));
        System.out.println(cal.get(Calendar.MONTH)+1);
        System.out.println(cal.get(Calendar.DATE));
        System.out.println(cal.get(Calendar.HOUR_OF_DAY));
        System.out.println(cal.get(Calendar.MINUTE));
        System.out.println(cal.get(Calendar.SECOND));

        LocalDateTime dt = LocalDateTime.now();
        System.out.println(dt.getYear());
        System.out.println(dt.getMonthValue());
        System.out.println(dt.getDayOfMonth());
        System.out.println(dt.getHour());
        System.out.println(dt.getMinute());
        System.out.println(dt.getSecond());

        //获得从 1970 年 1 月 1 日 0 时 0 分 0 秒到现在的毫秒数
        System.out.println(Calendar.getInstance().getTimeInMillis());
        System.out.println(System.currentTimeMillis());
        System.out.println(Clock.systemDefaultZone().millis());

        //取得某月的最后一天
        Calendar time = Calendar.getInstance();
        System.out.println(time.getActualMaximum(Calendar.DAY_OF_MONTH));
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date();
        System.out.println(sdf.format(d1));
        //jdk8
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(dtf.format(ldt));

        //打印昨天的当前时刻
        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.DATE,-1);
        System.out.println(sdf.format(c1.getTime()));
        //jdk8
        LocalDateTime yesterday=ldt.minusDays(1);
        System.out.println(dtf.format(yesterday));
        System.out.println(testReturn());

        ThreadLocal<DateTimeFormatter> tl = new ThreadLocal<DateTimeFormatter>();
        tl.set(dtf);
    }

    public static int testReturn(){
        int a = 20;
        try{
            a=10;
            return a;
        }finally{
            a=15;
//            return a;//如果这样，就又重新形成了一条返回路径，由于只能通过1个return返回，所以这里直接返回15
        }
    }
}

