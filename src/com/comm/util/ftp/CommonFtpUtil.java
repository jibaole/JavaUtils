package com.comm.util.ftp;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class CommonFtpUtil {
	private FTPClient ftpClient;
	public static final int BINARY_FILE_TYPE = FTP.BINARY_FILE_TYPE;
	public static final int ASCII_FILE_TYPE = FTP.ASCII_FILE_TYPE;

	/**
	 * 链接到服务器
	 * 
	 * @return
	 */
	public boolean open() {
		if (ftpClient != null)
			return true;
		try {
			ftpClient = new FTPClient();
			// ftpClient.openServer(server, port);
			// ftpClient.login(userName, userPassword);
			// ftpClient.binary();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			ftpClient = null;
			return false;
		}
	}

	// path should not the path from root index
	// or some FTP server would go to root as '/'.
/*	public void connectServer(FtpConfig ftpConfig) throws SocketException,
			IOException {
		String server = ftpConfig.getHostName();
		int port = ftpConfig.getPort();
		String user = ftpConfig.getLoginName();
		String password = ftpConfig.getPassword();
		String location = ftpConfig.getLoginPath();
		connectServer(server, port, user, password, location);
	}*/

	public void connectServer(String server, int port, String user,
			String password, String path) throws SocketException, IOException {
		ftpClient = new FTPClient();
		ftpClient.connect(server, port);
		System.out.println("Connected to " + server + ".");
		System.out.println(ftpClient.getReplyCode());

		boolean loginStatus = ftpClient.login(user, password);

		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		ftpClient.setDataTimeout(7200000);
	//	ftpClient.setsetConnectTimeout(7200000);
		System.out.println("loginStatus=" + loginStatus);
		// Path is the sub-path of the FTP path
		if (path.length() != 0) {
			ftpClient.changeWorkingDirectory(path);
		}
	}

	public FTPClient connectServer2(String server, int port, String user,
			String password, String path) throws SocketException, IOException {
		ftpClient = new FTPClient();
		ftpClient.connect(server, port);
		System.out.println("Connected to " + server + ".");
		System.out.println(ftpClient.getReplyCode());

		boolean loginStatus = ftpClient.login(user, password);

		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

		System.out.println("loginStatus=" + loginStatus);
		// Path is the sub-path of the FTP path
		if (path.length() != 0) {
			ftpClient.changeWorkingDirectory(path);
		}
		return ftpClient;
	}

	// FTP.BINARY_FILE_TYPE | FTP.ASCII_FILE_TYPE
	// Set transform type
	public void setFileType(int fileType) throws IOException {
		ftpClient.setFileType(fileType);
	}

	public void closeServer() throws IOException {
		if (ftpClient.isConnected()) {
			ftpClient.disconnect();
		}
	}

	// =======================================================================
	// == About directory =====
	// The following method using relative path better.
	// =======================================================================

	public boolean changeDirectory(String path) throws IOException {
		return ftpClient.changeWorkingDirectory(path);
	}

	public boolean createDirectory(String pathName) throws IOException {
		return ftpClient.makeDirectory(pathName);
	}

	public boolean removeDirectory(String path) throws IOException {
		return ftpClient.removeDirectory(path);
	}

	// delete all subDirectory and files.
	public boolean removeDirectory(String path, boolean isAll)
			throws IOException {

		if (!isAll) {
			return removeDirectory(path);
		}

		FTPFile[] ftpFileArr = ftpClient.listFiles(path);
		if (ftpFileArr == null || ftpFileArr.length == 0) {
			return removeDirectory(path);
		}
		//
		for (FTPFile ftpFile : ftpFileArr) {
			String name = ftpFile.getName();
			if (ftpFile.isDirectory()) {
				System.out.println("* [sD]Delete subPath [" + path + "/" + name
						+ "]");
				removeDirectory(path + "/" + name, true);
			} else if (ftpFile.isFile()) {
				System.out.println("* [sF]Delete file [" + path + "/" + name
						+ "]");
				deleteFile(path + "/" + name);
			} else if (ftpFile.isSymbolicLink()) {

			} else if (ftpFile.isUnknown()) {

			}
		}
		return ftpClient.removeDirectory(path);
	}

	// Check the path is exist; exist return true, else false.
	public boolean existDirectory(String path) {
		boolean flag = false;
		try {
			// FTPFile[] ftpFileArr = ftpClient.listFiles(path);
			FTPFile[] ftpFileArr = ftpClient.listFiles();

			if (ftpFileArr.length > 0) {
				for (FTPFile ftpFile : ftpFileArr) {

				//	System.out.println("ftpFile=" + ftpFile.getName());

					if (ftpFile.isDirectory()
							&& ftpFile.getName().equalsIgnoreCase(path)) {
						flag = true;
						break;
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}

	// =======================================================================
	// == About file =====
	// Download and Upload file using
	// ftpUtil.setFileType(FtpUtil.BINARY_FILE_TYPE) better!
	// =======================================================================

	// #1. list & delete operation
	// Not contains directory
	public List<String> getFileList(String path) throws IOException {
		// listFiles return contains directory and file, it's FTPFile instance
		// listNames() contains directory, so using following to filer
		// directory.
		// String[] fileNameArr = ftpClient.listNames(path);
		FTPFile[] ftpFiles = ftpClient.listFiles(path);

		List<String> retList = new ArrayList<String>();
		if (ftpFiles == null || ftpFiles.length == 0) {
			return retList;
		}
		for (FTPFile ftpFile : ftpFiles) {
			if (ftpFile.isFile()) {
				retList.add(ftpFile.getName());
			}
		}
		return retList;
	}

	public List<String> listFile() throws IOException {
		// listFiles return contains directory and file, it's FTPFile instance
		// listNames() contains directory, so using following to filer
		// directory.
		// String[] fileNameArr = ftpClient.listNames(path);
		FTPFile[] ftpFiles = ftpClient.listFiles();

		List<String> retList = new ArrayList<String>();
		if (ftpFiles == null || ftpFiles.length == 0) {
			return retList;
		}
		for (FTPFile ftpFile : ftpFiles) {
			if (ftpFile.isFile()) {
				retList.add(ftpFile.getName());
			}
		}
		return retList;
	}
	
	
	public boolean deleteFile(String pathName) throws IOException {
		return ftpClient.deleteFile(pathName);
	}

	// #2. upload to ftp server
	// InputStream <------> byte[] simple and See API

	public boolean uploadFile(String fileName, String newName) {
		boolean flag = false;
		InputStream iStream = null;
		try {
			iStream = new FileInputStream(fileName);
			System.out.println("uploadFile fileName=" + fileName);
			System.out.println("uploadFile iStream=" + iStream);
			// ftpClient.enterLocalPassiveMode();
			flag = ftpClient.storeFile(newName, iStream);
			// flag = ftpClient.storeFile("/company", iStream);
			System.out.println("uploadFile flag=" + flag);

		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			return flag;
		} finally {
			if (iStream != null) {
				try {
					iStream.close();
				} catch (Exception e) {
					flag = false;
					return flag;
				}
			}
		}
		return flag;
	}

	public boolean uploadFile(String fileName) throws IOException {
		return uploadFile(fileName, fileName);
	}

	public boolean uploadFile(InputStream iStream, String newName) {
		boolean flag = false;
		try {
			// can execute [OutputStream storeFileStream(String remote)]
			// Above method return's value is the local file stream.
			System.out.println("newName="+newName);
			System.out.println("iStream="+iStream.available());
			
			flag = ftpClient.storeFile(newName, iStream);
			System.out.println("--flag---" + flag);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			return flag;
		} finally {
			if (iStream != null) {
				try {
					iStream.close();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return flag;
	}

	// #3. Down load

	public boolean download(String remoteFileName, String localFileName)
			throws IOException {
		boolean flag = false;
		File outfile = new File(localFileName);
		OutputStream oStream = null;
		try {
			oStream = new FileOutputStream(outfile);
			flag = ftpClient.retrieveFile(remoteFileName, oStream);
		} catch (IOException e) {
			flag = false;
			return flag;
		} finally {
			oStream.close();
		}
		return flag;
	}

	public InputStream downFile(String sourceFileName) throws IOException {
		return ftpClient.retrieveFileStream(sourceFileName);
	}

	public static void main(String[] args) {
		CommonFtpUtil commonFtpUtil = new CommonFtpUtil();
		// FTPClient ftpclient = null;

		FileInputStream fis = null;
		try {

			commonFtpUtil.connectServer("192.168.1.37", 10021, "doc_upload", "doc654321upload",
					"");

			// ftpclient=CommonFtpUtil.ftpClient;

			boolean aa = commonFtpUtil.existDirectory("");
			System.out.println("aa=" + aa);
			boolean a = commonFtpUtil.uploadFile("/Users/gongfei/Documents/doc_prod/1.ppt",
					"1.ppt");
			System.out.println("a=" + a);
			commonFtpUtil.closeServer();
			/*
			 * ftpclient.connect("115.29.39.165"); ftpclient.login("larry",
			 * "123456"); File file = new File(File.separator + "opt" +
			 * File.separator + "1.jpg"); ftpclient.changeWorkingDirectory("/");
			 * ftpclient.setBufferSize(1024);
			 * ftpclient.setControlEncoding("UTF-8");
			 * ftpclient.setFileType(ftpclient.BINARY_FILE_TYPE); fis = new
			 * FileInputStream(file); ftpclient.storeFile("2.jpg", fis);
			 */

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}