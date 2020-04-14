package com.azhong.demo.security;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 对称加密算法就是传统的用一个密码进行加密和解密
 * 
 * @author Administrator
 *
 */
public class AESDemo {
	/**
	 * @Desc 加密
	 * @param key
	 * @param input
	 * @return
	 * @throws GeneralSecurityException
	 */
	public static byte[] encrypt(byte[] key, byte[] input) throws GeneralSecurityException {
		//根据算法名称/工作模式/填充模式获取Cipher实例；
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		SecretKey keySpec = new SecretKeySpec(key,"AES");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec);
		return cipher.doFinal(input);
	}
	
	/**
	 * @Desc 解密
	 * @param key
	 * @param input
	 * @return
	 * @throws GeneralSecurityException
	 */
	public static byte[] decrypt(byte[] key,byte[] input)throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		SecretKey keySpec = new SecretKeySpec(key,"AES");
		cipher.init(Cipher.DECRYPT_MODE, keySpec);
		return cipher.doFinal(input);
	}
	
	public static void main(String[] args) throws Exception {
		//原文
		String message ="Hello,world!";
		System.out.println("Message:"+message);
		//128位秘钥=16bytes Key:
		byte[] key="1234567890abcdef".getBytes("UTF-8");
		//加密
		byte[] data = message.getBytes("UTF-8");
		byte[] encrypted = encrypt(key,data);
		System.out.println("Encrypted:"+Base64.getEncoder().encodeToString(encrypted));
		//解密
		byte[] decrypted = decrypt(key,encrypted);
		System.out.println("Decrypted:"+new String(decrypted,"UTF-8"));
	}
}
