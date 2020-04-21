package com.azhong.demo.security;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 对称加密算法就是传统的用一个密码进行加密和解密
 * 
 * @author Administrator
 *
 */
public class AESDemo {

    /**
     * @Desc 加密 ECB模式是最简单的AES加密模式，它只需要一个固定长度的密钥， 固定的明文会生成固定的密文，
     * 		   这种一对一的加密方式会导致安全性降低，更好的方式是通过CBC模式，它需要一个随机数作为IV参数， 
     * 		   这样对于同一份明文，每次生成的密文都不同：
     *       在CBC模式下，需要一个随机生成的16字节IV参数，必须使用SecureRandom生成。
     *       因为多了一个IvParameterSpec实例，因此，初始化方法需要调用Cipher的一个
     *       重载方法并传入IvParameterSpec。观察输出，可以发现每次生成的IV不同，密文 也不同。
     * @param key
     * @param input
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] encrypt(byte[] key, byte[] input) throws GeneralSecurityException {
        // 1.根据算法名称/工作模式/填充模式获取Cipher实例；
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "AES/ECB/PKCS5Padding");
        // 2.根据算法名称初始化一个SecretKey实例，密钥必须是指定长度；
        SecretKey keySpec = new SecretKeySpec(key, "AES");
        // CBC模式需要生成一个16bytes的initialization vector
        SecureRandom sr = SecureRandom.getInstanceStrong();
        byte[] iv = sr.generateSeed(16);
        IvParameterSpec ivps = new IvParameterSpec(iv);
        // 3.使用SerectKey初始化Cipher实例，并设置加密或解密模式；
        // cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivps);
        // 4.传入明文或密文，获得密文或明文。
        // return cipher.doFinal(input);
        byte[] data = cipher.doFinal(input);
        // IV不需要保密，把IV和密文一起返回
        return join(iv, data);
    }

    private static byte[] join(byte[] iv, byte[] data) {
        byte[] r = new byte[iv.length + data.length];
        System.arraycopy(iv, 0, r, 0, iv.length);
        System.arraycopy(data, 0, r, iv.length, data.length);
        return r;
    }

    /**
     * @Desc 解密
     * @param key
     * @param input
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] decrypt(byte[] key, byte[] input) throws GeneralSecurityException {
        // 把input分割成IV和密文
        byte[] iv = new byte[16];
        byte[] data = new byte[input.length - 16];
        System.arraycopy(input, 0, iv, 0, 16);
        System.arraycopy(input, 16, data, 0, data.length);
        // 解密
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "AES/ECB/PKCS5Padding");
        SecretKey keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivps = new IvParameterSpec(iv);
        // cipher.init(Cipher.DECRYPT_MODE, keySpec);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivps);
        return cipher.doFinal(data);
    }

    public static void main(String[] args) throws Exception {
        // 原文
        String message = "Hello,world!";
        System.out.println("Message:" + message);
        // 128位秘钥=16bytes Key:
        // byte[] key="1234567890abcdef".getBytes("UTF-8");
        // 256位秘钥=32bytes Key:
        byte[] key = "1234567890abcdef1234567890abcdef".getBytes("UTF-8");
        // 加密
        byte[] data = message.getBytes("UTF-8");
        byte[] encrypted = encrypt(key, data);
        System.out.println("Encrypted:" + Base64.getEncoder().encodeToString(encrypted));
        // 解密
        byte[] decrypted = decrypt(key, encrypted);
        System.out.println("Decrypted:" + new String(decrypted, "UTF-8"));
    }
}
