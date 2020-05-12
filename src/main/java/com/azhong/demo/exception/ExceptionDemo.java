package main.java.com.azhong.demo.exception;

import java.util.*;

public class ExceptionDemo {
    public static void main(String[] args) {
        //多线程安全化
        List<String> list=Collections.synchronizedList(new ArrayList<String>());
        List<String> list1=Collections.synchronizedList(new LinkedList<String>());
        Collections.sort(list);
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }

}
class Annoyance extends Exception {
}
class Sneeze extends Annoyance {
}
class Human {
    public static void main(String[] args)
            throws Exception {
        try {
            try {
                throw new Sneeze();
            }
            catch ( Annoyance a ) {
                System.out.println("Caught Annoyance");
                throw a;
            }
        }
        catch ( Sneeze s ) {
            System.out.println("Caught Sneeze");
            return ;
        }
        finally {
            System.out.println("Hello World!");
        }
    }
}