package com.comm.util.generic.dao;

import java.util.List;
import java.util.Map;

import com.qiniu.http.Response;
import com.qiniu.storage.model.FileInfo;

public interface GenericQiniuDAO {

	Response putUploadFile(String filePath, String key);
	Response putUploadRecorderFile(String filePath, String key,String recorderPath);
	void DownloadFileUrl(String baseUrl,String filePath);
	String getAvthumbResult(String id);
	Map changeFileUpload(String filePath, String key);
	FileInfo getFileStat(String bucket,String key);

}
