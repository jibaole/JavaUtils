package com.comm.util.secret;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

public class HmacShaUtil {

	
	/**
	 * 与php中的hash_hmac('sha512', $data, $key)功能相同
	 * 
	 * @param data
	 * @param key
	 * @return
	 */
	public static String hmacSHA1(String data, String key) {
		String result = "";
		byte[] bytesKey = key.getBytes();
		final SecretKeySpec secretKey = new SecretKeySpec(bytesKey, "HmacSHA1"); // HmacSHA512
		try {
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(secretKey);
			final byte[] macData = mac.doFinal(data.getBytes());
			byte[] hex = new Hex().encode(macData);
			result = new String(hex, "ISO-8859-1");
		} catch (NoSuchAlgorithmException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
