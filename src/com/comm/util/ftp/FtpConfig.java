/*package com.comm.util.ftp;

import java.io.Serializable;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.comm.mall.base.model.TbSystemConfig;
import com.comm.mall.base.model.TbSystemConfigGroup;

public class FtpConfig  extends HibernateDaoSupport implements Serializable {

	private static final long serialVersionUID = 2845361820359309895L;
	
	*//**登录用户ip**//*
	private String hostName;
	
	*//**登录端口**//*
	private int port;
	
	*//**登录用户名**//*
	private String loginName;
	
	*//**登录密码**//*
	private String password;
	
	*//**编码方式**//*
	private String encoding = "UTF-8";
	
	*//**浏览图片url**//*
	private String loginPath;
	
	private String[] fileNames;	

	public String getHostName() {
		return hostName;
	}
	public int getPort() {
		return port;
	}
	public String getLoginName() {
		return loginName;
	}
	public String getPassword() {
		return password;
	}
	public String getEncoding() {
		return encoding;
	}
	public String getLoginPath() {
		return loginPath;
	}
	public String[] getFileNames() {
		return fileNames;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public void setLoginPath(String loginPath) {
		this.loginPath = loginPath;
	}
	public void setFileNames(String[] fileNames) {
		this.fileNames = fileNames;
	}
	
	public FtpConfig(){
	}
	
	*//**
	 * 通过组名查询组id(配置组表)
	 * @param value 组名
	 * @return 
	 *//*
	public List<TbSystemConfigGroup> findByValue(final String value) 
	{
		List<TbSystemConfigGroup> list = (List<TbSystemConfigGroup>)this.getHibernateTemplate().find("from TbSystemConfigGroup where configGroupName=? and visible='1'", value);
		
		   List<TbSystemConfigGroup> list = this.getHibernateTemplate().execute(new HibernateCallback()
		   {

				public Object doInHibernate(Session session)
				throws HibernateException, SQLException 
			
				{
					List<TbSystemConfigGroup> list = session.createQuery("from TbSystemConfigGroup where configGroupName='"+value+"' and visible='1'").list();
					return list;
				}});

		return list;
		
	}
	
	*//**
	 * 通过组名称 查询改组下的所有配置信息
	 * @param value 组名称
	 * @return
	 *//*
	public List<TbSystemConfig> getBtyGroup(String value){
		
		List<TbSystemConfigGroup> list = findByValue(value);
		
		return findByGroupId(list.get(0).getConfigGroupId());
	}

	*//**
	 * 通过组id查询组下所有的配置信息(配置表)
	 * @param configGroupId (组id)
	 * @return
	 *//*
	public List<TbSystemConfig> findByGroupId(final Long configGroupId) 
	{
		List<TbSystemConfig> list = (List<TbSystemConfig>)this.getHibernateTemplate().find("from TbSystemConfig  where configGroupId=? and visible='1'", configGroupId);
		
		List<TbSystemConfig> list =  this.getHibernateTemplate().execute(new HibernateCallback()
		{

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException 
					{
						List<TbSystemConfigGroup> list = session.createQuery("from TbSystemConfig  where configGroupId="+configGroupId+" and visible='1'").list();
						return list;
					}});
					
			return list;
	}
	
	*//**
	 * 初始化配置信息
	 *//*
	public void init(){
		
		List<TbSystemConfig> configlist = getBtyGroup("ftp上传组");
		
		if(null != configlist){
		for(int i=0;i<configlist.size();i++){
			TbSystemConfig config = configlist.get(i);
			if(config.getConfigName().equals("ftp服务器外网IP")){
				this.hostName=config.getConfigValue();
			}
			if(config.getConfigName().equals("ftp端口")){
				this.port=Integer.valueOf(config.getConfigValue());
			}
			if(config.getConfigName().equals("ftp用户名")){
				this.loginName = config.getConfigValue();
			}
			if(config.getConfigName().equals("ftp密码")){
				this.password = config.getConfigValue();
			}
			if(config.getConfigName().equals("浏览图片")){
				this.loginPath = config.getConfigValue();
			}
		}
		}
	}
	
	

}*/