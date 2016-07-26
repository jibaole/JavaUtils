package com.comm.utils;

import org.springframework.security.providers.encoding.Md5PasswordEncoder;

public class MD5PasswordEncoderUtil {

	public static String DEFAULT_PASSWORD = "fsdfdsf32325";

	/**
	 * 将密码用MD5加密
	 * 
	 * @param rawPass
	 *            密码明文
	 * @param emailAddress
	 *            密钥
	 * @return 返加密码明文的MD5摘要
	 */
	public final static String encode(String rawPass, String emailAddress) {
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		md5.setEncodeHashAsBase64(false);

		return md5.encodePassword(rawPass, emailAddress);
	}

	/**
	 * 验证密码
	 * 
	 * @param encPass
	 *            密码MD5摘要
	 * @param rawPass
	 *            密码明文
	 * @param emailAddress
	 *            密钥
	 * @return 如果明文与摘要匹配返回true,反之返回false
	 */
	public final static boolean valid(String encPass, String rawPass,
			String emailAddress) {
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		md5.setEncodeHashAsBase64(false);

		return md5.isPasswordValid(encPass, rawPass, emailAddress);
	}

	public static void main(String[] args) {

		System.out.println("b1="+ MD5PasswordEncoderUtil.encode("111111", "liu@liu.com"));

		boolean b1 = MD5PasswordEncoderUtil.valid("f56fce659909cb21220d43785cc85272", "111111", "liu@liu.com");

		System.out.println("b1=" + b1);

		boolean b2 = MD5PasswordEncoderUtil.valid("f56fce659909cb21220d43785cc85272", "11111111","liu11@liu.com");

		System.out.println("b2=" + b2);

		boolean b3 = MD5PasswordEncoderUtil.valid("f56fce659909cb21220d43785cc85272", "1112111", "liu@liu.com");

		System.out.println("b3=" + b3);

	}
}
