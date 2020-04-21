package com.azhong.demo.security;

import java.math.BigInteger;
import java.nio.charset.Charset;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Base64;

/**
 * Hmac算法是一种标准的基于密钥的哈希算法，可以配合MD5、SHA-1等哈希算法，计算的摘要长度和原摘要算法长度相同。
 * @author Administrator
 *
 */
public class HMacTest {

    private static void validateSign() throws Exception {
        byte[] hkey = KEY_STR.getBytes(Charset.forName("UTF-8"));
        SecretKey key = new SecretKeySpec(hkey, KEY_MAC);
        Mac mac = Mac.getInstance(KEY_MAC);
        mac.init(key);
        mac.update("HelloWorld".getBytes("UTF-8"));
        byte[] result = mac.doFinal();
        System.out.println(new BigInteger(1, result).toString(16));
        String base64Str = new String(Base64.encode(result));
        System.out.println(base64Str);
    }

    public static void main(String[] args) throws Exception {
        byte[] bkey = KEY_STR.getBytes(Charset.forName("UTF-8"));
        SecretKey key = new SecretKeySpec(bkey, KEY_MAC);
        // 打印随机生成的key
        byte[] skey = key.getEncoded();
        System.out.println(new BigInteger(1, skey).toString(16));
        Mac mac = Mac.getInstance(KEY_MAC);
        mac.init(key);
        mac.update("HelloWorld".getBytes("UTF-8"));
        byte[] result = mac.doFinal();
        System.out.println(new BigInteger(1, result).toString(16));
        String base64Str = new String(Base64.encode(result));
        System.out.println(base64Str);
        validateSign();
    }

    private final static String KEY_MAC = "HmacMD5";

    private final static String KEY_STR = "keys";
}
