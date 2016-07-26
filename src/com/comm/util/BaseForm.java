package com.comm.util;

import java.io.Serializable;

import javax.ws.rs.FormParam;

public class BaseForm implements Serializable {
	
	private static final long serialVersionUID = 67567645651L;

	@FormParam("page")
	public Integer page;

	@FormParam("pageSize")
	public Integer pageSize;

	@FormParam("firstResult")
	public Integer firstResult = 0;

	@FormParam("maxResult")
	public Integer maxResult = Integer.MAX_VALUE;
	
	@FormParam("sortType")
	public int sortType;
	
	
	@FormParam("ids")
	public String ids;
	

	public String getIds()
	{
		return ids;
	}

	public void setIds(String ids)
	{
		this.ids = ids;
	}

	public int getSortType()
	{
		return sortType;
	}

	public void setSortType(int sortType)
	{
		this.sortType = sortType;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getFirstResult() {
		if (null != page && page > 1) {
			return (page - 1) * pageSize;
		} else {
			return 0;
		}
	}

	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}

	public Integer getMaxResult() {
		return pageSize;
	}

	public void setMaxResult(Integer maxResult) {
		this.maxResult = maxResult;
	}

}
