package com.comm.util.rest;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class HttpConnectionManager {

	private static ThreadSafeClientConnManager connectionManager;

	private static HttpParams params = new BasicHttpParams();
	/**
	 * 最大连接数
	 */
	public final static int MAX_TOTAL_CONNECTIONS = 3000;
	/**
	 * 获取连接的最大等待时间
	 */
	public final static int WAIT_TIMEOUT = 10000;
	/**
	 * 每个路由最大连接数
	 */
	public final static int MAX_ROUTE_CONNECTIONS = 3000;
	/**
	 * 连接超时时间
	 */
	public final static int CONNECT_TIMEOUT = 10000;

	static {

		HttpConnectionParams.setConnectionTimeout(params, CONNECT_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, WAIT_TIMEOUT);
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", 80, PlainSocketFactory
				.getSocketFactory()));
		connectionManager = new ThreadSafeClientConnManager(registry);
		connectionManager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
		connectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
		new EvictionConnectionThread(connectionManager).start();
	}

	public static HttpClient getHttpClient() {
		return new DefaultHttpClient(connectionManager, params);
	}

}

class EvictionConnectionThread extends Thread {
	private Log log=LogFactory.getLog(EvictionConnectionThread.class);
	private final ClientConnectionManager connMgr;

	public EvictionConnectionThread(ClientConnectionManager connMgr) {
		this.connMgr = connMgr;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000*60*5);
				log.info("EvictionConnectionThread evict closed connection from the pool "+Calendar.getInstance().getTime());
				connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
