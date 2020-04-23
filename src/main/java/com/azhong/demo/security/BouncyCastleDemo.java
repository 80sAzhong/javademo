package main.java.com.azhong.demo.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class BouncyCastleDemo {

    public static void main(String[] args) throws Exception {
        // 注册BouncyCastle
        Security.addProvider(new BouncyCastleProvider());
        // 按正常名称调用
        MessageDigest md = MessageDigest.getInstance("RipeMD160");
        md.update("helloWorld".getBytes("UTF-8"));
        byte[] result = md.digest();
        System.out.println(new BigInteger(1, result).toString(16));
    }
}
