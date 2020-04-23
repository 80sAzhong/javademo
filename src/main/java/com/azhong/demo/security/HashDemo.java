package main.java.com.azhong.demo.security;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * 哈希算法可用于验证数据完整性，具有防篡改检测的功能；
 * 常用的哈希算法有MD5、SHA-1等；
 * 用哈希存储口令时要考虑彩虹表攻击。
 * @author Administrator
 *
 */
public class HashDemo {

    public static void main(String[] args) throws Exception {
        // 创建一个MessageDigest实例
        MessageDigest md = MessageDigest.getInstance("MD5");
        // 反复调用update输入数据:
        md.update("Hello".getBytes("UTF-8"));
        md.update("World".getBytes("UTF-8"));
        byte[] result = md.digest();
        System.out.println(new BigInteger(1, result).toString(16));
        // 加盐的目的在于使黑客的彩虹表失效，即使用户使用常用口令，也无法从MD5反推原始口令。
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        sha1.update("salt".getBytes("UTF-8"));
        sha1.update("password".getBytes("UTF-8"));
        result = md.digest();
        System.out.println(new BigInteger(1, result).toString(16));
    }
}
