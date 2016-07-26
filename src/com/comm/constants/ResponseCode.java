package com.comm.constants;
/**  
 * @desc: 系统输出编码
 * @author:Administrator 
 * @date：2014-4-10   
 */
public class ResponseCode {
	   /** 全局系统输出变量*/
	   public static final String GLOBAL_SUSSCESS="success";
	   public static final String GLOBAL_FAILTURE="fail";
	   public static final String GLOBAL_EXCEPTION="exception";
	   public static final boolean GLOBAL_YES=true;
	   public static final boolean GLOBAL_NO=false;
	   public static final String GLOBAL_SYSTEM_REPEAT="9X0001";  //数据重复
	   public static final String GLOBAL_SYSTEM_OUTDATE="9X0002"; //已过期
	   public static final String GLOBAL_SYSTEM_INCOMPLEMENT="9X0003"; //数据缺失
	   public static final String GLOBAL_SYSTEM_NONEXIST="9X0004"; //不存在
	   public static final String GLOBAL_SYSTEM_EXIST="9X0005"; //已存在
	   public static final String GLOBAL_SYSTEM_PARAM_ERROR="9X0006"; //参数错误
	   public static final String GLOBAL_SYSTEM_MORE="9X0007"; //超出数量
	   
	   /** 系统登录*/
	   public static final String RESPONSE_LOGIN_0A0001="0A0001";  //数据不完整
	   public static final String RESPONSE_LOGIN_0A0002="0A0002";  //格式验证失败
	   public static final String RESPONSE_LOGIN_0A0003="0A0003";  //用户名或密码错误
	   public static final String RESPONSE_LOGIN_0A0004="0A0004";  //登录成功，跳转到访问页面
	   public static final String RESPONSE_LOGIN_0A0005="0A0005";  //密码错误
	   			//------------caos用户与唯医用户
	   public static final String RESPONSE_LOGIN_0A1001="0A1001";  //CAOS用户与唯医用户已绑定
	   public static final String RESPONSE_LOGIN_0A1002="0A1002";  //CAOS用户与唯医用户未绑定
	   public static final String RESPONSE_LOGIN_0A1003="0A1003";  //CAOS用户与唯医用户已绑定，但是不存在唯医账号
	   public static final String RESPONSE_LOGIN_0A1004="0A1004";  //CAOS用户与唯医用户未绑定，但是存在与caos同样的唯医账号
	   public static final String RESPONSE_LOGIN_0A1005="0A1005";  //CAOS用户与唯医用户绑定成功
	   public static final String RESPONSE_LOGIN_0A1006="0A1006";  //CAOS用户与唯医用户绑定失败
	   
	   /** 会员 */
	   public static final String RESPONSE_CUSTOMER_0B0001="0B0001";  //用户名已存在
	   public static final String RESPONSE_CUSTOMER_0B0002="0B0002";  //验证错误
	   public static final String RESPONSE_CUSTOMER_0B0003="0B0003";  //用户名不存在
	   public static final String RESPONSE_CUSTOMER_0B0004="0B0004";  //会员创建成功
	
	   public static final String RESPONSE_CUSTOMER_0B0005="0B0005";  //邮箱已存在
	   public static final String RESPONSE_CUSTOMER_0B0006="0B0006";  //手机号码已存在  该手机号码已经绑定过其他账户
	   public static final String RESPONSE_CUSTOMER_0B0007="0B0007";  //非法帐号
	   public static final String RESPONSE_CUSTOMER_0B0008="0B0008";  //冻结帐号
	   public static final String RESPONSE_CUSTOMER_0B0009="0B0009";  //邮箱不存在
	   public static final String RESPONSE_CUSTOMER_0B0010="0B0010";  //手机号码不存在
	   public static final String RESPONSE_AUTH_0B0101="0B0101";  //提交认证待审核
	   public static final String RESPONSE_AUTH_0B0102="0B0102";  //已提交过认证，待审核
	   public static final String RESPONSE_AUTH_0B0103="0B0103";  //已认证通过

	   
	   /** 邮件 */
	   public static final String RESPONSE_EMAIL_0C0001="0C0001";  //超出邮件数量
	   
	   /** 短信 */
	   public static final String RESPONSE_SMS_0C0001="0D0001";  //超出短信数量
	  
	   public static final String RESPONSE_VALID_0A0001="1A0001";  //验证已经失效
	   public static final String RESPONSE_VALID_0A0002 = "1A0002";  //验证失败
	
	   
	   public static final String RESPONSE_CAOS_CUSTOMER_0C0001="1C0001";  //不存在的CAOS账户
	   public static final String RESPONSE_CAOS_CUSTOMER_0C0002="1C0002";  //不正确的CAOS密码
	   
	   /** 订单 */
	   public static final String RESPONSE_ORDER_0F0001="0F0001";  //创建订单成功
	   public static final String RESPONSE_ORDER_0F0002="0F0002";  //订单验证错误
	   public static final String RESPONSE_ORDER_0F0003="0F0003";  //订单完成
	   public static final String RESPONSE_ORDER_0F0004="0F0004";  //订单取消
	   public static final String RESPONSE_ORDER_0F0005="0F0005";  //未支付
	   public static final String RESPONSE_ORDER_0F0006="0F0006";  //已支付
	   public static final String RESPONSE_ORDER_0F0010="0F0010";  //caos订单已经存在
	   public static final String RESPONSE_ORDER_0F0011="0F0011";  //年会订单已经存在
	   public static final String RESPONSE_ORDER_0F0012="0F0012";  //caos订单已经存在(待付款)
		
	   public static final String RESPONSE_WEIXIN_0G0001="0G0001";  //unite、info 已绑定  
	   public static final String RESPONSE_WEIXIN_0G0002="0G0002";  //unite 已绑定 
	   public static final String RESPONSE_WEIXIN_0G0003="0G0003";  //unite_info 已绑定 
	   
}

  
