package com.azhong.demo.security;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


/**
    * 我们使用非对称加密算法的时候，对于一个公钥-私钥对，通常是用公钥加密，私钥解密。
   * 如果使用私钥加密，公钥解密是否可行呢？实际上是完全可行的。
   * 不过我们再仔细想一想，私钥是保密的，而公钥是公开的，用私钥加密，那相当于所有人都可以用公钥解密。这个加密有什么意义？
   * 这个加密的意义在于，如果小明用自己的私钥加密了一条消息，比如小明喜欢小红，然后他公开了加密消息，由于任何人都可以用
   * 小明的公钥解密，从而使得任何人都可以确认小明喜欢小红这条消息肯定是小明发出的，其他人不能伪造这个消息，小明也不能抵
   * 赖这条消息不是自己写的。
   * 因此，私钥加密得到的密文实际上就是数字签名，要验证这个签名是否正确，只能用私钥持有者的公钥进行解密验证。使用数字签
   * 名的目的是为了确认某个信息确实是由某个发送方发送的，任何人都不可能伪造消息，并且，发送方也不能抵赖。
   * 在实际应用的时候，签名实际上并不是针对原始消息，而是针对原始消息的哈希进行签名
   * 常用数字签名算法有：
 * MD5withRSA
 * SHA1withRSA
 * SHA256withRSA
 * @author Administrator
 *
 */
public class SignDemo {
	/**
	 * 生成公钥/私钥对
	 * @param skStr
	 * @param pkStr
	 * @throws GeneralSecurityException
	 */
	public static String[] generatorKeys() throws GeneralSecurityException {
		KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
		kpGen.initialize(1024);
		KeyPair kp = kpGen.generateKeyPair();
		PrivateKey sk = kp.getPrivate();
		PublicKey pk = kp.getPublic();
		String [] keys= {
		Base64.getEncoder().encodeToString(sk.getEncoded()),
		Base64.getEncoder().encodeToString(pk.getEncoded())};
		return keys;
	}
	
	/**
	 * 用私钥签名:
	 * @param orgStr
	 * @param skStr
	 * @return
	 * @throws GeneralSecurityException
	 */
	public static String sign(String message,String skStr) throws GeneralSecurityException {
		byte[] messageBytes= message.getBytes(StandardCharsets.UTF_8);
		//用私钥签名
		Signature s = Signature.getInstance("SHA1withRSA");
		byte[] skBytes = Base64.getDecoder().decode(skStr.getBytes(StandardCharsets.UTF_8));
        
		//恢复私钥
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PKCS8EncodedKeySpec skSpec = new PKCS8EncodedKeySpec(skBytes);
		PrivateKey sk = kf.generatePrivate(skSpec);
		s.initSign(sk);
		s.update(messageBytes);
		byte[] signed = s.sign();
		return Base64.getEncoder().encodeToString(signed);
	}
	
	/**
	 * 验签
	 * @param message
	 * @param signStr
	 * @param pkStr
	 * @return
	 * @throws GeneralSecurityException
	 */
	public static boolean verifySign(String message,String signStr,String pkStr) throws GeneralSecurityException
	{
		//用公钥验证
		Signature v = Signature.getInstance("SHA1withRSA");
		KeyFactory kf = KeyFactory.getInstance("RSA");
		//恢复公钥
		byte[] pkBytes = Base64.getDecoder().decode(pkStr.getBytes(StandardCharsets.UTF_8));
		X509EncodedKeySpec pkSpec = new X509EncodedKeySpec(pkBytes);
		PublicKey pk = kf.generatePublic(pkSpec);
		v.initVerify(pk);
		v.update(message.getBytes(StandardCharsets.UTF_8));
	    return v.verify(Base64.getDecoder().decode(signStr));
	}
	
	public static void main(String[] args) throws GeneralSecurityException {
		String[] keys=generatorKeys();
		String pkStr=keys[1];
		String skStr=keys[0];
		System.out.println("sk:"+skStr+"\npk:"+pkStr);
		String message = "Hello ,I am 80s'Azhong!!!";
		String sign=sign(message,skStr);
		System.out.println("sign:"+sign);
		boolean vf=verifySign(message,sign,pkStr);
		System.out.println("验签:"+(vf?"成功":"失败"));
	}
}
