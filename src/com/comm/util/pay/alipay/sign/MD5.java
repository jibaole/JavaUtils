package com.comm.util.pay.alipay.sign;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;

import org.apache.commons.codec.digest.DigestUtils;

/** 
* 功能：支付宝MD5签名处理核心文件，不需要修改
* 版本：3.3
* 修改日期：2012-08-17
* 说明：
* 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
* 该代码仅供学习和研究支付宝接口使用，只是提供一个
* */

public class MD5 {

    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String input_charset) {
    	text = text + key;
    	
        String sign= DigestUtils.md5Hex(getContentBytes(text, input_charset));
        System.out.println(sign+":--pre-------------text:-"+text);
        return sign;       
    }
    
    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param sign 签名结果
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     * 
     * 
     * 28470d4b281b0eb83ab357aa35dab35:--pre-------------text:-_input_charset=utf-8&format=xml&partner=2088311473820010&req_data=<direct_trade_create_req><notify_url>http://182.92.9.84/mcall/order/payment/alipay_notify/</notify_url><call_back_url>http://182.92.9.84/mcall/order/payment/alipay_return/</call_back_url><seller_account_name>gukysfh@cmda.org.cn</seller_account_name><out_trade_no>1414999687649558</out_trade_no><subject>CAOS ORDER</subject><total_fee>0.01</total_fee><merchant_url></merchant_url></direct_trade_create_req>&req_id=20141103152819&sec_id=MD5&service=alipay.wap.trade.create.direct&v=2.08kgohb2exdf9fbjpqnpr7e87wyslmorb
fbf0d28d51cc564d33542a91ebddbd4d:--pre-------------text:-_input_charset=utf-8&format=xml&partner=2088311473820010&req_data=<auth_and_execute_req><request_token>20141103b780d1440fe02f4f6a5e9eaab9956565</request_token></auth_and_execute_req>&sec_id=MD5&service=alipay.wap.auth.authAndExecute&v=2.08kgohb2exdf9fbjpqnpr7e87wyslmorb

     * notify_data=<notify><payment_type>1</payment_type><subject>CAOS ORDER</subject><trade_no>2014110367396697</trade_no><buyer_email>1114887324@qq.com</buyer_email><gmt_create>2014-11-03 15:28:21</gmt_create><notify_type>trade_status_sync</notify_type><quantity>1</quantity><out_trade_no>1414999687649558</out_trade_no><notify_time>2014-11-03 15:28:35</notify_time><seller_id>2088311473820010</seller_id><trade_status>TRADE_SUCCESS</trade_status><is_total_fee_adjust>N</is_total_fee_adjust><total_fee>0.01</total_fee><gmt_payment>2014-11-03 15:28:35</gmt_payment><seller_email>gukysfh@cmda.org.cn</seller_email><price>0.01</price><buyer_id>2088502275340972</buyer_id><notify_id>3eca3c94d3ef2a443a5cd7a5af60ceab7e</notify_id><use_coupon>N</use_coupon></notify>
     * &sec_id=MD5&service=alipay.wap.trade.create.direct&v=1.08kgohb2exdf9fbjpqnpr7e87wyslmorb
     * 
     */
    public static boolean verify(String text, String sign, String key, String input_charset) {
    	text = text + key;
    	String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
    	System.out.println(text+":----------------------mySign--pre-"+mysign);
    	System.out.println("verify sign="+sign);
    	if(mysign.equals(sign)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException 
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

}