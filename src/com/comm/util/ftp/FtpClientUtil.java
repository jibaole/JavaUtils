package com.comm.util.ftp;


public class FtpClientUtil {

}

// package com.comm.util.ftp;
//
// import java.io.DataInputStream;
// import java.io.File;
// import java.io.FileInputStream;
// import java.io.FileNotFoundException;
// import java.io.FileOutputStream;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.StringTokenizer;
//
// import org.apache.log4j.Logger;
//
// import sun.net.TelnetInputStream;
// import sun.net.TelnetOutputStream;
// import sun.net.ftp.FtpClient;
//
// import com.comm.util.string.StringUtil;
//
// public class FtpClientUtil
// {
// /**
// * Logger for this class
// */
// private static final Logger logger = Logger.getLogger(FtpClientUtil.class);
//
// public static Long SYSTEM_ID = 1L;
//
// // public static final String FTP_PIC_SERVER = "116.58.219.3";
// // public static final String FTP_PIC_SERVER =
// // "back_pic_01.gongtianxia.com"; //ͼƬ������
// // public static final int FTP_PIC_PORT = 50022;
// // / public static final String FTP_PIC_USERNAME = "ftpview";
// // public static final String FTP_PIC_PASSWORD = "ftp!QAZ@WSX#EDC";
// // public static final String FTP_PIC_VISIT_PREFIX =
// // "http://back_pic_01.gongtianxia.com/backup/";
//
// private static final int BUFFER_SIZE = 16 * 1024;
// FtpClient ftpClient;
// private String server;
// private int port;
// private String userName;
// private String userPassword;
//
// public FtpClientUtil(String server, int port, String userName, String
// userPassword)
// {
// this.server = server;
// this.port = port;
// this.userName = userName;
// this.userPassword = userPassword;
// }
//
// /**
// * ���ӵ�������
// *
// * @return
// */
// public boolean open()
// {
// if (ftpClient != null && ftpClient.serverIsOpen())
// return true;
// try
// {
// ftpClient = new FtpClient();
// ftpClient.openServer(server, port);
// ftpClient.login(userName, userPassword);
// ftpClient.binary();
// return true;
// } catch (Exception e)
// {
// e.printStackTrace();
// ftpClient = null;
// return false;
// }
// }
//
// public boolean cd(String dir)
// {
// boolean f = false;
// try
// {
// ftpClient.cd(dir);
// } catch (IOException e)
// {
// // Logs.error(e.toString());
// e.printStackTrace();
// return f;
// }
// return true;
// }
//
// /**
// * �ϴ��ļ���FTP������
// *
// * @param localPathAndFileName
// * �����ļ�Ŀ¼���ļ���
// * @param ftpFileName
// * �ϴ�����ļ���
// * @param ftpDirectory
// * FTPĿ¼��:/path1/pathb2/,���Ŀ¼�����ڻ��Զ�����Ŀ¼
// * @throws Exception
// */
// public boolean upload2(String localDirectoryAndFileName, String ftpFileName,
// String ftpDirectory)
// {
// // if (!open())
// // return false;
// FileInputStream is = null;
// TelnetOutputStream os = null;
// try
// {
// char ch = ' ';
// if (ftpDirectory.length() > 0)
// ch = ftpDirectory.charAt(ftpDirectory.length() - 1);
// /*
// * for (; ch == '/' || ch == '\\'; ch = ftpDirectory
// * .charAt(ftpDirectory.length() - 1)) ftpDirectory =
// * ftpDirectory.substring(0, ftpDirectory.length() - 1);
// */
//
// for (; ch == '/' || ch == '\\'; ch =
// ftpDirectory.charAt(ftpDirectory.length() - 1))
// {
// ftpDirectory = ftpDirectory.substring(0, ftpDirectory.length() - 1);
// if (ftpDirectory.length() == 0)
// {
// break;
// }
// }
//
// int slashIndex = ftpDirectory.indexOf(47);
// int backslashIndex = ftpDirectory.indexOf(92);
// int index = slashIndex;
// String dirall = ftpDirectory;
// if (backslashIndex != -1 && (index == -1 || index > backslashIndex))
// index = backslashIndex;
// String directory = "";
// while (index != -1)
// {
// if (index > 0)
// {
// String dir = dirall.substring(0, index);
// directory = directory + "/" + dir;
// ftpClient.sendServer("XMKD " + directory + "\r\n");
// ftpClient.readServerResponse();
// }
// dirall = dirall.substring(index + 1);
// slashIndex = dirall.indexOf(47);
// backslashIndex = dirall.indexOf(92);
// index = slashIndex;
// if (backslashIndex != -1 && (index == -1 || index > backslashIndex))
// index = backslashIndex;
// }
// ftpClient.sendServer("XMKD " + ftpDirectory + "\r\n");
//
//			logger.error("upload(String, String, String) - String ftpDirectory=" + ftpDirectory); //$NON-NLS-1$
//			logger.error("upload(String, String, String) - String ftpFileName=" + ftpFileName); //$NON-NLS-1$
// ftpClient.readServerResponse();
//
// os = ftpClient.put(ftpDirectory + "/" + ftpFileName);
//			logger.error("upload(String, String, String) - os=" + os); //$NON-NLS-1$
// File file_in = new File(localDirectoryAndFileName);
// is = new FileInputStream(file_in);
// byte bytes[] = new byte[1024];
// int i;
// while ((i = is.read(bytes)) != -1)
// os.write(bytes, 0, i);
// // ��������
//
// return true;
// } catch (Exception e)
// {
// e.printStackTrace();
// return false;
// } finally
// {
// if (is != null)
// try
// {
// is.close();
// } catch (Exception e)
// {
// e.printStackTrace();
// }
// if (os != null)
// try
// {
// os.close();
// } catch (Exception e)
// {
// e.printStackTrace();
//
// }
// }
// }
//
// /**
// * ��FTP�������������ļ������������ļ�����
// *
// * @param ftpDirectoryAndFileName
// * @param localDirectoryAndFileName
// * @return
// * @throws Exception
// */
// public long download(String ftpDirectoryAndFileName, String
// localDirectoryAndFileName) throws Exception
// {
// long result = 0;
// if (!open())
// return result;
// TelnetInputStream is = null;
// FileOutputStream os = null;
// try
// {
// is = ftpClient.get(ftpDirectoryAndFileName);
// java.io.File outfile = new java.io.File(localDirectoryAndFileName);
// os = new FileOutputStream(outfile);
// byte[] bytes = new byte[1024];
// int c;
// while ((c = is.read(bytes)) != -1)
// {
// os.write(bytes, 0, c);
// result = result + c;
// }
// } catch (Exception e)
// {
// throw e;
// } finally
// {
// if (is != null)
// is.close();
// if (os != null)
// os.close();
//
// }
// return result;
// }
//
// /**
// * ����FTPĿ¼�µ��ļ��б�
// *
// * @param ftpDirectory
// * @return
// */
// public List<String> getFileNameList(String ftpDirectory)
// {
//
// System.out.println("ftpDirectory=" + ftpDirectory);
// List<String> list = new ArrayList<String>();
// if (!open())
// return list;
// try
// {
//
// DataInputStream dis = new DataInputStream(ftpClient.nameList(ftpDirectory));
// String filename = "";
// while ((filename = dis.readLine()) != null)
// {
// list.add(filename);
// }
// } catch (Exception e)
// {
// e.printStackTrace();
// }
// return list;
// }
//
// /**
// * ɾ��FTP�ϵ��ļ�
// *
// * @param ftpDirAndFileName
// */
// public boolean deleteFile(String ftpDirAndFileName)
// {
// if (!open())
// return false;
// ftpClient.sendServer("DELE " + ftpDirAndFileName + "\r\n");
// return true;
// }
//
// /**
// * ɾ��FTPĿ¼
// *
// * @param ftpDirectory
// */
// public boolean deleteDirectory(String ftpDirectory)
// {
// if (!open())
// return false;
// ftpClient.sendServer("XRMD " + ftpDirectory + "\r\n");
// return true;
// }
//
// /**
// * �ر�����
// */
// public void close()
// {
// try
// {
// if (ftpClient != null && ftpClient.serverIsOpen())
// ftpClient.closeServer();
// } catch (Exception e)
// {
//
// }
// }
//
// /**
// * �����ļ�
// *
// * @param remoteSourcePath
// * ftpԭ�ļ�
// * @param remoteAimPath
// * ftpĿ���ļ�
// * @return
// */
// public Boolean copyFile(String remoteSourcePath, String remoteAimPath)
// {
// Boolean success = false;
// // if (!open())
// // return success;
// TelnetInputStream is = null;
// TelnetOutputStream os = null;
// try
// {
// is = ftpClient.get(remoteSourcePath);
// os = ftpClient.put(remoteAimPath);
// System.out.println("--" + remoteAimPath);
// byte bytes[] = new byte[BUFFER_SIZE];
// int i;
// while ((i = is.read(bytes)) != -1)
// {
// os.write(bytes, 0, i);
// }
// success = true;
// } catch (Exception e)
// {
// e.printStackTrace();
// } finally
// {
// try
// {
// if (is != null)
// is.close();
// if (os != null)
// {
// os.flush();
// os.close();
// }
// } catch (Exception ex)
// {
// ex.printStackTrace();
// }
// }
//
// return success;
// }
//
// // public static void main(String[] args) {
// // // FtpClientUtil ftpClientUtil = new FtpClientUtil("116.58.219.3", 21,
// // // "shanxi-gtx", "gtxshanxi123");
// // // System.out.println("ftpClientUtil=" + ftpClientUtil);
// // // List<String> list = ftpClientUtil.getFileNameList("taobao");
// // // System.out.println("list=" + list);
// // //
// // // if (!CollectionUtils.isEmpty(list)) {
// // // for (int i = 0; i < list.size(); i++) {
// // // System.out.println("i=" + i + "    " + (String) list.get(i));
// // // }
// // // }
// // // try {
// // // ftpClientUtil.upload("E://1000178_detail_001.jpg", "1111.jpg",
// // // "11000000");
// // // // System.out.println("cd---"+ftpClientUtil.cd("taobao"));
// // // } catch (Exception ex) {
// // // ex.printStackTrace();
// // // }
// //
// // try {
// // // FtpClientUtil ftpClientUtil = new FtpClientUtil(FTP_PIC_SERVER,
// // // FTP_PIC_PORT,
// // // FTP_PIC_USERNAME, FTP_PIC_PASSWORD);
// // // ftpClientUtil.open();
// // // for (int m = 0; m < 3; m++) {
// // //
// // // // / TelnetInputStream srcFile
// // // // =ftpClient.get("/1/1000002/1000002_preview_001.jpg");
// // //
// // // // TelnetInputStream sss=ftpClientUtil
// // //
// // // ftpClientUtil.copyFile("/backup/1/1000002/1000002_preview_003.jpg",
// // // "/master/1/100000m/1000002_preview_003_back" + m + ".jpg");
// // // }
// // // List<String> list =
// // // ftpClientUtil.getFileNameList("/backup/1/1000002/");
// // // if (!CollectionUtils.isEmpty(list)) {
// // // for (int i = 0; i < list.size(); i++) {
// // // if(((String) list.get(i)).endsWith(".jpg"))
// // // System.out.println("i=" + i + "    " + (String) list.get(i));
// // // }
// // // }
// // /*
// // * FtpClientUtil ftpClientUtil = new
// // * FtpClientUtil(ServerConfig.WEB_RESOURCE_SERVER,
// // * ServerConfig.WEB_RESOURCE_SERVER_PORT,
// // * ServerConfig.WEB_RESOURCE_USERNAME,
// // * ServerConfig.WEB_RESOURCE_PWD); ftpClientUtil.open(); boolean
// // * b=ftpClientUtil.deleteFile("/1/images/1.png");
// // * System.out.println(b);
// // */
// //
// // FtpClientUtil ftpClientUtil = new FtpClientUtil("42.96.199.129",
// // 21, "zhangyanbing", "zhangyanbing");
// //
// // ftpClientUtil.open();
// //
// // //boolean b = ftpClientUtil.upload("F:\\1.png", "1.png", "/");
// //
// // /*List<String> list=ftpClientUtil.readPathfile("/");
// //
// // for (String str : list) {
// //
// //
// // //System.out.println("lsit-----=" + str);
// // System.out.println("lsit--chinese---=" + str);
// //
// // }*/
// // String currentTimeMillis = String.valueOf(System
// // .currentTimeMillis());
// //
// // ftpClientUtil.copyFile("/ר�ҷ�̸/Daniel.flv",
// // "/flv/"+currentTimeMillis+".flv");
// //
// // //System.out.println("upload b=" + ftpClientUtil.isDirectory("/1.flv"));
// //
// // // System.out.println("upload b=" + b);
// //
// // } catch (Exception ex) {
// // ex.printStackTrace();
// // }
// // }
// //
//
// public boolean isDirectory(String dir)
// {
// boolean f = false;
// try
// {
// if (dir.indexOf(".flv") < 0 && dir.indexOf(".mp4") < 0 && dir.indexOf(".png")
// < 0 && dir.indexOf(".bat") < 0 && dir.indexOf(".pdf") < 0)
// {
// f = true;
// } else
// {
// f = false;
// }
//
// } catch (Exception e)
// {
// // Logs.error(e.toString());
// e.printStackTrace();
// return f;
// }
// return f;
// }
//
// public List<String> readPathfile(String filepath) throws
// FileNotFoundException, IOException
// {
//
// List<String> list = new ArrayList<String>();
//
// if (!isDirectory(filepath))
// {
//
// System.out.println("�ļ�");
//
// list.add(filepath);
// } else if (isDirectory(filepath))
// {
//
// System.out.println("�ļ���");
//
// List<String> filelist = getFileNameList(filepath);
//
// for (int i = 0; i < filelist.size(); i++)
// {
//
// // File readfile = new File(filepath + "\\" + filelist[i]);
//
// String file = StringUtil.utfToChinese(filelist.get(i));
//
// // String file=StringUtil.toUNICODE(filelist.get(i));
//
// System.out.println("file in dir " + file);
//
// if (!isDirectory(file))
// {
// list.add(file);
// } else if (isDirectory(file))
// {
// System.out.println("filepath  file====    " + "/" + file);
// list.addAll(readPathfile(file));
//
// }
//
// }
//
// }
//
// return list;
//
// }
//
// /**
// * �ݹ鴴��ftpĿ¼ add by huojianjun
// *
// * @param pathList
// * @throws Exception
// */
// public void createFtpDirectory(String ftpDirPath) throws Exception
// {
// ftpClient.ascii();
// StringTokenizer s = new StringTokenizer(ftpDirPath, "/");
// String pathName = "";
// while (s.hasMoreElements())
// {
// pathName = pathName + "/" + (String) s.nextElement();
// ftpClient.sendServer("XMKD " + pathName + "\r\n");
// int reply = ftpClient.readServerResponse();
// logger.info("++ftp:upFile:buildList, reply:" + reply + "++++++++++++");
// }
// ftpClient.binary();
// }
//
// /**
// * �ϴ�ftp�ļ� , �Զ�����Ŀ¼ add by huojianjun
// *
// * @param source
// * @param destination
// * @throws Exception
// */
// public boolean upload(String localDirectoryAndFileName, String ftpFileName,
// String ftpDirectory)
// {
// boolean flag = true ;
// try {
// String uploadfilePath ="/"+ftpDirectory + "/" + ftpFileName;
// System.out.println("+++begin+++uploadfilePath=="+uploadfilePath);
// createFtpDirectory(uploadfilePath.substring(0,
// uploadfilePath.lastIndexOf("/")));
// ftpClient.binary();
// TelnetOutputStream ftpOut = ftpClient.put(uploadfilePath);
// TelnetInputStream ftpIn = new TelnetInputStream(new
// FileInputStream(localDirectoryAndFileName), true);
// byte[] buf = new byte[2048];
// int bufsize = 0;
// while ((bufsize = ftpIn.read(buf, 0, buf.length)) != -1)
// {
// ftpOut.write(buf, 0, bufsize);
// }
// ftpIn.close();
// ftpOut.close();
// System.out.println("+++end+++uploadfilePath=="+uploadfilePath);
// }catch(Exception e){
// e.printStackTrace() ;
// flag = false ;
// }
// return flag ;
// }
//
// public static void main(String args[])
// {
//
// String sourcefilePath =
// "D:\\workSpace\\myEclipseSpace\\allin_manager_platform\\WebContent\\/html/news/2014/01/11/7.html";
// String ftpDirectory = "/home/staticimage/allin_static/html/news/2014/01/11";
// String filename = "11.html";
// try
// {
// FtpClientUtil ftpClientUtil = new FtpClientUtil("192.168.1.36",
// Integer.parseInt("21"), "page_upload", "654321");
// boolean openStatus = ftpClientUtil.open();
// if (openStatus)
// {
// System.out.println("openStatus=" + openStatus);
// ftpClientUtil.upload(sourcefilePath, filename, ftpDirectory);
// }
// System.out.println("222222222");
//
// } catch (Exception ex)
// {
// ex.printStackTrace();
// }
//
// }
//
// }