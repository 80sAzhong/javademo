package com.azhong.demo.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;

/**
 * 非对称加密就是加密和解密使用的不是相同的密钥：只有同一个公钥-私钥对才能正常加解密
 * @author Administrator
 * 例子:因此，如果小明要加密一个文件发送给小红，他应该首先向小红索取她的公钥，然后，
 * 他用小红的公钥加密，把加密文件发送给小红，此文件只能由小红的私钥解开，因为小红的
 * 私钥在她自己手里，所以，除了小红，没有任何人能解开此文件。
 * 优点:
 * 非对称加密相比对称加密的显著优点在于，对称加密需要协商密钥，而非对称加密可以安全地
 * 公开各自的公钥，在N个人之间通信的时候：使用非对称加密只需要N个密钥对，每个人只管理
 * 自己的密钥对。而使用对称加密需要则需要N*(N-1)/2个密钥，因此每个人需要管理N-1
 * 个密钥，密钥管理难度大，而且非常容易泄漏。
 * 缺点:
 * 非对称加密的缺点就是运算速度非常慢，比对称加密要慢很多。
 * 应用场景:
 * 在实际应用的时候，非对称加密总是和对称加密一起使用。假设小明需要给小红需要传输加密文件，
 * 他俩首先交换了各自的公钥，然后：
 * 1.小明生成一个随机的AES口令，然后用小红的公钥通过RSA加密这个口令，并发给小红；
 * 2.小红用自己的RSA私钥解密得到AES口令；
 * 3.双方使用这个共享的AES口令用AES加密通信。
 * 可见非对称加密实际上应用在第一步，即加密“AES口令”。这也是我们在浏览器中常用的HTTPS
 * 协议的做法，即浏览器和服务器先通过RSA交换AES口令，接下来双方通信实际上采用的是速度较
 * 快的AES对称加密，而不是缓慢的RSA非对称加密。
 */
public class RSADemo {
	public static void main(String[] args) throws Exception {
		//明文:
		byte[] plain = "Hello,encrypt use RSA".getBytes("UTF-8");
		//创建公钥/私钥对:
		Person alice =new Person("Alice");
		//用Alice的公钥加密
		byte[] pk = alice.getPublicKey();
		System.out.println(String.format("public key:%s", Base64.getEncoder().encodeToString(pk)));
		byte[] encrypted = alice.encrypt(plain);
		System.out.println(String.format("encrypted:%x", new BigInteger(1,encrypted)));
		//用Alice的私钥解密
		byte[] sk = alice.getPrivateKey();
		System.out.println(String.format("private key:%s", Base64.getEncoder().encodeToString(sk)));
		byte[] decrypted = alice.decrypt(encrypted);
		System.out.println(new String(decrypted,"UTF-8"));
	}
}
class Person{
	String name;
	//私钥
	PrivateKey sk;
	//公钥
	PublicKey pk;
	public Person(String name) throws GeneralSecurityException {
		 this.name = name;
        // 生成公钥／私钥对:
        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
        kpGen.initialize(1024);
        KeyPair kp = kpGen.generateKeyPair();
        this.sk = kp.getPrivate();
        this.pk = kp.getPublic();
	}
	
	//把私钥导出为字节
	public byte[] getPrivateKey() {
		return this.sk.getEncoded();
	}
	
	//把公钥导出为字节
	public byte[] getPublicKey() {
		return this.pk.getEncoded();
	} 
	
	//用公钥加密
	public byte[] encrypt(byte[] message) throws GeneralSecurityException{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, this.pk);
		return cipher.doFinal(message);
	}
	
	//用私钥解密
	public byte[] decrypt(byte[] input) throws GeneralSecurityException{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE,this.sk);
		return cipher.doFinal(input);
	}
}
