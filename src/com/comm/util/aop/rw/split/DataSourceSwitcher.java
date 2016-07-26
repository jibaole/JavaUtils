package com.comm.util.aop.rw.split;

import java.util.Date;

import org.springframework.util.Assert;

import com.comm.util.RandomUtil;

public class DataSourceSwitcher {
	@SuppressWarnings("rawtypes")
	private static final ThreadLocal contextHolder = new ThreadLocal();

	@SuppressWarnings("unchecked")
	public static void setDataSource(String dataSource) {
		// Assert.notNull(dataSource, "dataSource cannot be null");
	
		contextHolder.set(dataSource);
	}

	public static void setMaster() {
		try {
			clearDataSource();
			setDataSource("master");

			System.out.println("======logContent=switchto: master" );
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void setSlave() {
		try {
			clearDataSource();
			// 随机调用slave
			StringBuffer slaveString = new StringBuffer("");

			slaveString.append("slave");
			// slaveString.append(RandomUtil.getRandom(2));

			setDataSource(slaveString.toString());

			// 记录随机调用的日志
			StringBuffer logContent = new StringBuffer("");

			/*logContent.append(DateUtil.formatDate(new Date(),
					"yyyy-MM-dd hh:mm:ss"));*/
			logContent.append("   ");
			logContent.append(" invoke ");
			logContent.append(slaveString.toString());

			// System.out.println("setSlave:=" + logContent);
			System.out.println("======logContent=switchto: slave" + logContent);
			// FileUtil.appendMethod("/opt/interface_invoke_slave_mysql.log",
			// logContent.toString()+"\n");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String getDataSource() {
		return (String) contextHolder.get();
	}

	public static void clearDataSource() {
		contextHolder.remove();
	}
}
