package com.azhong.demo.security;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
/**
 * URL编码和Base64编码都是编码算法，它们不是加密算法；
 * URL编码的目的是把任意文本数据编码为%前缀表示的文本，便于浏览器和服务器处理；
 * Base64编码的目的是把任意二进制数据编码为文本，但编码后数据量会增加1/3。
 * @author Administrator
 *
 */
public class URLTest {
	public static void main(String[] args) throws UnsupportedEncodingException {
//	System.out.println(String.valueOf(StandardCharsets.UTF_8));
		String encode = URLEncoder.encode("中文", String.valueOf(StandardCharsets.UTF_8));
		System.out.println(encode);
		String decode = URLDecoder.decode(encode, String.valueOf(StandardCharsets.UTF_8));
		System.out.println(decode);
//		byte[] input = "中".getBytes();
//		byte[] input = new byte[] { (byte) 0xe4, (byte) 0xb8, (byte) 0xad };
		byte[] input = "hello world".getBytes();//new byte[] { (byte) 0xe4, (byte) 0xb8, (byte) 0xad, (byte) 0x21 };
		//		String b64encoded = Base64.getEncoder().encodeToString(input);
		//Base64编码的时候可以用withoutPadding()去掉=，解码出来的结果是一样的"CAESC2hlbGxvIHdvcmxk"
		String b64encoded = "CAESYOaCqOeahOi0puWPtzrmsLTnha7psbws5bey57uP5ZyoSU9T56uv55m75b2V77yM5Y+v6YCa6L+H5paH5Lu25Lyg6L6T5Yqp5omL5Lyg6L6T5paH5Lu25YiwSU9T56uvLiJg5oKo55qE6LSm5Y+3OuawtOeFrumxvCzlt7Lnu4/lnKhJT1Pnq6/nmbvlvZXvvIzlj6/pgJrov4fmlofku7bkvKDovpPliqnmiYvkvKDovpPmlofku7bliLBJT1Pnq68uMABAAEgAUAA=";
		//Base64.getEncoder().withoutPadding().encodeToString(input);
		System.out.println(b64encoded);
		byte[] output = Base64.getDecoder().decode(b64encoded.getBytes("UTF-8"));
		
		System.out.println(Arrays.toString(output) + "\n" + (new String(output)));
//		input = new byte[] { 0x01, 0x02, 0x7f, 0x00 };
	}
}
