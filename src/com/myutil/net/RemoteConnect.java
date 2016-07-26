package com.myutil.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/** 
* 2015-12-4 
* DES:网络连接
* author:JiBaoLe 
*/ 
public class RemoteConnect {


	private static String hostname = "192.168.1.39";
	private static String username = "root";
	private static String password = "all2013in";

	/**
	 * DES:根据登录信息,判断是否能连接对应服务器
	 */
	public static void execute(String hostname, String username,
			String password, String ssh_cmd) {
		// 指明连接主机的IP地址
		Connection conn = new Connection(hostname);
		Session ssh = null;
		try {
			// 连接到主机
			conn.connect();
			// 使用用户名和密码校验
			boolean isconn = conn.authenticateWithPassword(username, password);
			if (!isconn) {
				System.out.println("用户名称或者是密码不正确");
			} else {
				System.out.println("已经连接OK");
				ssh = conn.openSession();

				ssh.execCommand(ssh_cmd);
				// ssh.execCommand("perl /root/hello.pl");
				// 只允许使用一行命令，即ssh对象只能使用一次execCommand这个方法，
				// 多次使用则会出现异常
				// 使用多个命令用分号隔开
				// ssh.execCommand("cd /root; sh hello.sh");

				// 将Terminal屏幕上的文字全部打印出来
				InputStream is = new StreamGobbler(ssh.getStdout());
				BufferedReader brs = new BufferedReader(new InputStreamReader(
						is));
				while (true) {
					String line = brs.readLine();
					if (line == null) {
						break;
					}
					System.out.println(line);
				}  
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 连接的Session和Connection对象都需要关闭
			ssh.close();
			conn.close();
		}
	}

	public static void main(String[] args) {
		RemoteConnect.execute(hostname, username, password, "");
	}


}
