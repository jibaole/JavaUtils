package com.comm.util.page;



@SuppressWarnings("unchecked")
public class SystemContext {

	public static final String GULP_COMMAND = "gulp INWHtml --env ";
	
	public static final String GULP_DIR_COMMAND = "gulp INWDir --env ";
	
	
	private static ThreadLocal offset = new ThreadLocal();
	private static ThreadLocal pagesize = new ThreadLocal();
	
	public static void setOffset(int _offset){
		offset.set(_offset);
	}
	
	public static int getOffset(){
		Integer os = (Integer)offset.get();
		if(os == null){
			return 0;
		}
		return os;
	}
	
	public static void removeOffset(){
		offset.remove();
	}
	
	public static void setPagesize(int _pagesize){
		pagesize.set(_pagesize);
	}
	
	public static int getPagesize(){
		Integer ps = (Integer)pagesize.get();
		if(ps == null){
			return Integer.MAX_VALUE;
		}
		return ps;
	}
	
	public static void removePagesize(){
		pagesize.remove();
	}
}
