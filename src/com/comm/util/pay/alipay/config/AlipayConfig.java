package com.comm.util.pay.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {

	public static String ALIPAY_GATEWAY_NEW = "http://wappaygw.alipay.com/service/rest.htm?";
	// ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088311473820010";
	// 商户的私钥

	// 交易安全检验码，由数字和字母组成的32位字符串
	// 如果签名方式设置为“MD5”时，请设置该参数
	public static String key = "8kgohb2exdf9fbjpqnpr7e87wyslmorb";

	// 商户的私钥
	// 如果签名方式设置为“0001”时，请设置该参数
	//public static String private_key = "8kgohb2exdf9fbjpqnpr7e87wyslmorb";
	public static String private_key="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAPSzVHrLs3SwC0NhxQysaDIR5aqZZOyGIBKBmFi8o+rYbkRGGuYfZE/Oc3hJknfPZG/3xvWLJGNn+dCIQVlBCjN1IXaqG2TduE5UNFgq27PBhUu5o42Yc25GWzkVALF4Xy62KP8nIN4VFkjvNzOjNmdmHUVCBiMIgzVTrmzvvlDvAgMBAAECgYEAjEJiUQSDt4XU3umiphIIo3KIv1GvfLegmJr59RsjpdAq3C2G65Lgz7HZlAAv2UbiHh/bRhFcaTJHChWfx4Y1Tox8VvskzAeSWxkLKrT0p6OoSOQxcuw84UMyUOO6K1JCPLUQb3CFeVvgxPBxWjnABNnMkB3fcrFHWJLLizKU5WECQQD/9awofsOeNel78jyg4tCmwyvKgfRQmAhW7zFluOP3kumJ53wg0EMlMKTFaKAkfwSHv8kf9KIq8fL/oY40loORAkEA9L00BjJDL9wdHZ42R5RWMs71zuiGB+alZsX+CNkDaZppuAjPDS2SJL7PvG0xs30prfoVgVYQU6zmseBa/8RMfwJAY9eU3Ab2j1RAS1TmSWuwMByZjFi80GM9fYu4HRHN+tiXuG9PgA0849jhrQ9uyE2zmrA45/oo98+BFi4Spni/wQJAVPqtacliEug3I+Wfq1MSayn5qBKNF4k6vWjaQHA6Ax0ewEwuuMCuEhw/PYocy9oV8tb3SKV9ndBXekQQ1OZwOQJAYHMUrw8cQW0LdXJ+uvua/um3XhXkGANglj9XLkOn5UEVKZZ2gSo+K7MIKujr70Gzl9nQT+MeJLUmvThDK0Xr+A==";

	// 支付宝的公钥
	// 如果签名方式设置为“0001”时，请设置该参数
	//	public static String ali_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCO1jCtmoWSCzqZEJkNL1jpCA4reaUaKvjoIiDNH7m440CMk6MaNvhz8vFZBIusFwngblEM5h3zHa5CoEZprAu0zB5SDxfSAlalvtEs576MRb43omyq01pjiQy61Y33fuCg02KC/E0Y5hgBZ58LODF3xmzvJNJuco36dAY8QIDAQAB";
	public static String ali_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQD0s1R6y7N0sAtDYcUMrGgyEeWqmWTshiASgZhYvKPq2G5ERhrmH2RPznN4SZJ3z2Rv98b1iyRjZ/nQiEFZQQozdSF2qhtk3bhOVDRYKtuzwYVLuaONmHNuRls5FQCxeF8utij/JyDeFRZI7zczozZnZh1FQgYjCIM1U65s775Q7wIDAQAB";

	// ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "/data/alipay_log/";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";

	// 签名方式 不需修改
	public static String direct_sign_type = "MD5";//MD5\0001
	
	public static String wap_sign_type = "MD5";

}
