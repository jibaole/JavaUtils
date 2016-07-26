package com.comm.util.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfUtil {

	/**
	 * 将Office文档转换为PDF. 运行该函数需要用到OpenOffice, OpenOffice下载地址为
	 * http://www.openoffice.org/
	 * 
	 * <pre>
	 * 方法示例: 
	 * String sourcePath = "F:\\office\\source.doc"; 
	 * String destFile = "F:\\pdf\\dest.pdf"; 
	 * Converter.office2PDF(sourcePath, destFile);
	 * </pre>
	 * 
	 * @param sourceFile
	 *            源文件, 绝对路径. 可以是Office2003-2007全部格式的文档, Office2010的没测试. 包括.doc,
	 *            .docx, .xls, .xlsx, .ppt, .pptx等. 示例: F:\\office\\source.doc
	 * @param destFile
	 *            目标文件. 绝对路径. 示例: F:\\pdf\\dest.pdf
	 * @return 操作成功与否的提示信息. 如果返回 -1, 表示找不到源文件, 或url.properties配置错误; 如果返回 0,
	 *         则表示操作成功; 返回1, 则表示转换失败
	 */
	public static boolean office2PDF(String hostName, int port,
			String sourceFile, String destFile) {

		System.out.println("sourceFile=" + sourceFile);
		System.out.println("destFile=" + destFile);

		boolean returnBoolean = false;
		try {
			File inputFile = new File(sourceFile);
			if (!inputFile.exists()) {
				returnBoolean = false;// 找不到源文件, 则返回-1
			}

			// 如果目标路径不存在, 则新建该路径
			File outputFile = new File(destFile);
			if (!outputFile.getParentFile().exists()) {
				outputFile.getParentFile().mkdirs();
			}

			// Process pro = startServer();
			// connect to an OpenOffice.org instance running on port 8100
			// OpenOfficeConnection connection = new SocketOpenOfficeConnection(
			// hostName, 8100);
			// connection.connect();
			//
			// // convert
			// DocumentConverter converter = new OpenOfficeDocumentConverter(
			// coOnnection);
			//
			// converter.convert(inputFile, outputFile);
			//
			// // close the connection
			// connection.disconnect();
			// 关闭OpenOffice服务的进程
			// pro.destroy();

			returnBoolean = true;
		} catch (Exception e) {
			e.printStackTrace();
			returnBoolean = false;
		} finally {

		}

		return returnBoolean;
	}

	public static void main(String[] args) {
		//boolean a = PdfUtil.office2PDF("192.168.1.37", 8100, "/root/3.ppt",
		//		"/root/3.pdf");
		// boolean b=PdfUtil.office2PDF("/root/2.pptx", "/root/2.pdf");
	//	System.out.println("a=" + a);
		// System.out.println("b="+b);
		
		PdfUtil.writePdf("/opt/1440047990987.pdf", "123");
		
	}

	private static Process startServer() throws IOException {
		String OpenOffice_HOME = "D:\\Program Files\\OpenOffice.org 3";// 这里是OpenOffice的安装目录,
																		// 在我的项目中,为了便于拓展接口,没有直接写成这个样子,但是这样是绝对没问题的
		// 如果从文件中读取的URL地址最后一个字符不是 '\'，则添加'\'
		if (OpenOffice_HOME.charAt(OpenOffice_HOME.length() - 1) != '\\') {
			OpenOffice_HOME += "\\";
		}
		// 启动OpenOffice的服务
		String command = OpenOffice_HOME
				+ "program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";
		Process pro = Runtime.getRuntime().exec(command);
		return pro;
	}

	public static void writePdf(String local_file_name, String content) {

		Rectangle rectPageSize = new Rectangle(PageSize.A4);// 定义A4页面大小
		// rectPageSize = rectPageSize.rotate();// 加上这句可以实现页面的横置

		// 创建一个Document对象，设置上下左右缩进
		Document document = new Document(rectPageSize, 50, 50, 50, 50);
		try {
			// 生成名为 HelloWorld.pdf 的文档
			PdfWriter.getInstance(document, new FileOutputStream(
					local_file_name));

			// 设置中文字体
			/*
			 * BaseFont bfChinese = BaseFont.createFont("STSong-Light",
			 * "UniGB-UCS2-H", false);
			 */
			/**
			 * 新建一个字体,iText的方法 STSongStd-Light 是字体，在iTextAsian.jar 中以property为后缀
			 * UniGB-UCS2-H 是编码，在iTextAsian.jar 中以cmap为后缀 H 代表文字版式是 横版， 相应的 V 代表
			 * 竖版
			 */
			// Font fontChinese = new Font(bfChinese, 12, Font.NORMAL,
			// Color.GREEN);
			// Font FontChinese = new Font(bfChinese, 10F, 0);

			// 添加PDF文档的一些信息
			document.addTitle("Hello World example");
			document.addAuthor("Bruno Lowagie");
			document.addSubject("This example explains how to add metadata.");
			document.addKeywords("iText, Hello World, step 3, metadata");
			document.addCreator("My program using iText");
			// 打开文档，将要写入内容
			document.open();
			
			System.out.println("document="+document);
			
	//		System.out.println("Repeatable:" + multipartEntity.isRepeatable());
			
			
			// 插入一个段落
			document.add(new Paragraph(content));

			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			// 关闭打开的文档
			document.close();
		}
		

	}

}
