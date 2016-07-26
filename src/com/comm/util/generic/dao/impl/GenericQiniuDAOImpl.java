package com.comm.util.generic.dao.impl;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


import com.comm.util.ConvertUtil;
import com.comm.util.HttpClientUtils;
import com.comm.util.HttpRequestUtil;
import com.comm.util.JsonToMap;
import com.comm.util.datetime.DateTimeUtil;
import com.comm.util.generic.QiniuConfig;
import com.comm.util.string.StringTool;
import com.qiniu.processing.OperationManager;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Recorder;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;



public class GenericQiniuDAOImpl<T extends Serializable, PK extends Serializable> {
	 private UploadManager uploadManager = new UploadManager();
	 
	 private OperationManager operater = new OperationManager(QiniuConfig.keyAuth);
	 private BucketManager bucketManager = new BucketManager(QiniuConfig.keyAuth);
	 //获取转码结果接口
	 private static final String  RESULT_URL = "http://api.qiniu.com/status/get/prefop";
	// 实体类类型(由构造方法自动赋值)
	private Class<T> entityClass;

	// 构造方法，根据实例类自动获取实体类类型
	public GenericQiniuDAOImpl() {
		this.entityClass = null;
		Class c = getClass();
		Type t = c.getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.entityClass = (Class<T>) p[0];
		}
	}
	/**
     * 上传文件
     *
     * @param filePath 上传的文件路径
     * @param key      上传文件保存的文件名(决定在七牛空间内显示的名称)
     * @param token    上传凭证
	 * @throws QiniuException 
     */
	public Response putUploadFile(String filePath, String key){
		Response res = null;
		String token = "";//QiniuConfig.keyAuth.uploadToken(QiniuConfig.bucket, key);
		//增加返回参数 设置
		StringMap policy = new StringMap();
		//此参数指定后台进行转码动作
		//;avthumb/m3u8/segtime/15/vb/440k
		//480*320
		//1280*720 avthumb/mp4/s/1280x720;
		///aspect/ 宽高比
		//vframe/jpg/offset/7/w/480/h/360 截取视频图片 3-225*150、4-157*109,7-75*52
		//测试指定文件名称
		//1398321761660_1398504227728_75_52.jpg  videoid+时间_图片格式
		//视频命名格式:videoid+视频规格.mp4
		String keystr = key.substring(0, key.indexOf("."));
		String tempTime = String.valueOf(DateTimeUtil.formatDateStringToInt(DateTimeUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss")));
		String saveas1 = UrlSafeBase64.encodeToString(QiniuConfig.bucket+":"+keystr+"_480_320"+".mp4");
		String saveas2 = UrlSafeBase64.encodeToString(QiniuConfig.bucket+":"+keystr+"_1280_720"+".mp4");
		String saveas3 = UrlSafeBase64.encodeToString(QiniuConfig.bucket+":"+keystr+"_"+tempTime+"_225_150"+".jpg");
		String saveas4 = UrlSafeBase64.encodeToString(QiniuConfig.bucket+":"+keystr+"_"+tempTime+"_157_109"+".jpg");
		String saveas5 = UrlSafeBase64.encodeToString(QiniuConfig.bucket+":"+keystr+"_"+tempTime+"_75_52"+".jpg");
		policy.putNotEmpty("persistentOps", "avthumb/mp4/s/480x320|saveas/"+saveas1+
				";avthumb/mp4/s/1280x720|saveas/"+saveas2+
				";vframe/jpg/offset/7/w/225/h/150|saveas/"+saveas3 +
				";vframe/jpg/offset/7/w/157/h/109|saveas/"+saveas4 +
				";vframe/jpg/offset/7/w/75/h/52|saveas/"+saveas5);
		token = QiniuConfig.keyAuth.uploadToken(QiniuConfig.bucket, key, 3600, policy);
		

		try {
			res = uploadManager.put(filePath, key, token);
		} catch (QiniuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	/**
	 * 断点续传文件
	 * @param filePath
	 * @param key
	 * @return
	 * @throws IOException 
	 */
	public Response putUploadRecorderFile(String filePath, String key,String recorderPath){
		Recorder recorder = null;
		UploadManager uploader = null;
		Response res = null;
		String token = "";
		try {
			//pathFile变量表示断点记录文件所在 “文件夹” 的路径
			//C:/f/视频文件/path
			recorder = new FileRecorder(recorderPath);
			 uploader = new UploadManager(recorder);
			 token = QiniuConfig.keyAuth.uploadToken(QiniuConfig.bucket, key);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			res =  uploader.put(filePath, key, token);
		} catch (QiniuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	/**
	 * 通过URL下载文件
	 * @param baseUrl  文件url
	 * @param filePath 存放路径 path+fileName 需要指定文件名，否则会报权限错误
	 * @return
	 */
	public void DownloadFileUrl(String baseUrl,String filePath){
		HttpClientUtils.getInstance().downloadfile(baseUrl, filePath);
	}
	/**
	 * 通过url获取转码结果,成功的修改对应的URL
	 * @param args
	 * http://api.qiniu.com/status/get/prefop?id=z0.56050b457823de5a496bce99
	 *
	 */
	public String getAvthumbResult(String id){
		Map paramsMap = new HashMap();
		paramsMap.put("id", id);
		return HttpClientUtils.getInstance().httpPost(RESULT_URL, paramsMap);
	}
	/**
	 * 文档文件上传，并转换为pdf格式，取对应的图片
	 * ?imageView2/1/w/200/h/100 图片裁剪
	 * 
	 */
	public Map changeFileUpload(String filePath, String key){
		Response res = null;
		String token = "";
		Map returnStr_Map = new HashMap();
		String returnStr = "";
		String keystr = key.substring(0, key.indexOf("."));
		String tempTime = String.valueOf(DateTimeUtil.formatDateStringToInt(DateTimeUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss")));
		String saveas1 = UrlSafeBase64.encodeToString(QiniuConfig.pdfbucket+":"+keystr+"_"+tempTime+".pdf");
		String saveas2 = UrlSafeBase64.encodeToString(QiniuConfig.pdfbucket+":"+keystr+"_"+tempTime+".jpg");
		
		StringMap policy = new StringMap();
		//实现异步pdf图片转换,并上传源文件
		policy.putNotEmpty("persistentOps","yifangyun_preview|saveas/"+saveas1);
		token = QiniuConfig.keyAuth.uploadToken(QiniuConfig.pdfbucket, key, 3600, policy);
		try {
			Response res_pdf = uploadManager.put(filePath, key, token);
				if(res_pdf.isOK()){
					returnStr_Map.put("isOK", "OK");
				}
				StringMap map = res_pdf.jsonToMap();
				returnStr = map.get("persistentId").toString();
				returnStr_Map.put("key", map.get("key").toString());
			if(res_pdf.statusCode==200){
				policy.putNotEmpty("persistentOps","yifangyun_preview|odconv/jpg/page/1|saveas/"+saveas2);
				token = QiniuConfig.keyAuth.uploadToken(QiniuConfig.pdfbucket, key, 3600, policy);
				res = uploadManager.put(filePath, key, token);
				StringMap map_jpg = res.jsonToMap();
				returnStr += "#"+map_jpg.get("persistentId").toString();
				
			}
			returnStr_Map.put("persistentId", returnStr);
		} catch (QiniuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnStr_Map;
	}
	/**
	 * 获取资源文件大小
	 */
	public FileInfo getFileStat(String bucket,String key){
		FileInfo info = null;
		try {
			info = bucketManager.stat(bucket,key);
		} catch (QiniuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return info;
	}
	/**
	 * 使用第三方转换服务
	 * @param args
	 */

	public static void main(String[] args) {
			String filePath = "C:/f/视频文件/test_vide_008.mov";//"C:/f/视频文件/test_vide_007.mov"; //"C:/f/文库文件/tomcat_003.docx";//"C:/f/视频文件/test_vide_005.mov";
			String key = "test_vide_008.mov";
			String id = "z0.5640335f7823de5a497314f8";//"z0.56161d3b7823de5a49fdf315";
			GenericQiniuDAOImpl qiniu = new GenericQiniuDAOImpl();
			//http://7xl1fx.com2.z0.glb.qiniucdn.com/1429836230.mp4
//			FileInfo info = qiniu.getFileStat(QiniuConfig.bucket, "1429836230.mp4");
//			System.out.println("====="+(info.fsize/(1024*1024)));
//			System.out.println(Math.round(info.fsize/(1024.0*1024.0)));
//			String url = "http://7xl1fx.com2.z0.glb.qiniucdn.com/1429836230.mp4?avinfo";
//			String jsonstr = HttpClientUtils.getInstance().httpGet(url);
//			Map map = JsonToMap.toMap(jsonstr);
//			System.out.println("===="+((Map)map.get("format")).get("duration"));//1323.960000 格式化成00:00:00
//			Map r = qiniu.changeFileUpload(filePath, key);
//			try {
//				StringMap map = r.jsonToMap();
//				System.out.println("=========="+r+"======="+map.formString());
//				//7xnoef.com2.z0.glb.qiniucdn.com
//				System.out.println("======="+"http://7xnoef.com2.z0.glb.qiniucdn.com/"+map.get("key"));
//			} catch (QiniuException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		 try {
			Response r = qiniu.putUploadFile(filePath,key);
					//qiniu.putUploadRecorderFile(filePath,key);
			StringMap  map = r.jsonToMap();
			System.out.println("=========="+r+"======="+map.formString());
			System.out.println("======="+"http://7xl1fx.com2.z0.glb.qiniucdn.com/"+map.get("key"));
			//下载
//			String  baseUrl = "http://7xl1fx.com2.z0.glb.qiniucdn.com/UAA-4hndfVc5V6DJX0EvslAUBBI=/lo3QXMj-tKLJAJlxwbwbnbBSoZkb";
//			String temstr = qiniu.privateDownloadUrl(baseUrl);
//			System.out.println("wyb===="+temstr);
//			String param = "";
//			String result = HttpRequestUtil.sendGet(baseUrl, param);
//			System.out.println("wyb===="+result);
//			Map map = JsonToMap.toMap((qiniu.getAvthumbResult(id)));
//			System.out.println("wyb==map=="+map);
//			System.out.println("wyb==items=="+map.get("items"));
//			System.out.println("wyb==items=="+((List)map.get("items")).size());
//			System.out.println("wyb==items=cmd="+((Map)((List)map.get("items")).get(0)).get("cmd"));
			
		} catch (QiniuException e) {
			 //TODO Auto-generated catch block
			e.printStackTrace();
		}
//			
//			File source= new File(filePath);
//		       Encoder encoder = new Encoder();
//		       try {
//		          MultimediaInfo m = encoder.getInfo(source);
//		          VideoInfo videoinfo = m.getVideo();
//		          long ls = m.getDuration();
//		          System.out.println();
//		       } catch(Exception e) {
//		         e.printStackTrace();
//		       }
	}

}