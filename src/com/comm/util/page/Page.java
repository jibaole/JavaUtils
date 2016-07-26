package com.comm.util.page;

import java.util.List;


/**
 * Page is not a domain object but is used to store and fetch page information.
 * 
 * @author andy.li
 */
public class Page {
	
	public final static int DEFAULT_PAGE_SIZE=10; 
	
    private int total;
    private List items;   //分页集合

    private int pageIndex;
    private int pageSize;
    private int pageCount;
    
    public Page(int pageIndex, int pageSize) {
        // check:
        if(pageIndex<1)
            pageIndex = 1;
        if(pageSize<1)
            pageSize = 1;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public Page(List items,
   		   int totalCount , int pageSize, int pageIndex){
        // check:
        if(pageIndex<1)
            pageIndex = 1;
        if(pageSize<1)
            pageSize = 1;
        this.total = totalCount;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.items=items;
        
        this.total = total;
        if(pageSize==0){
        	pageSize=10;
        }

        pageCount = total / pageSize + (total%pageSize==0 ? 0 : 1);
        // adjust pageIndex:
        if(total==0) {
            if(pageIndex!=1)
                throw new IndexOutOfBoundsException("Page index out of range.");
        }
        else {
            if(pageIndex>pageCount)
                throw new IndexOutOfBoundsException("Page index out of range.");
        }
    }
    
    public Page(int pageIndex) {
        this(pageIndex, DEFAULT_PAGE_SIZE);
    }

    public int getPageIndex() { return pageIndex; }

    public int getPageSize() { return pageSize; }

    public int getPageCount() { return pageCount; }

    public int getTotalCount() { return total; }

    public int getFirstResult() { return (pageIndex-1)*pageSize; }

    public boolean getHasPrevious() { return pageIndex>1; }

    public boolean getHasNext() { return pageIndex<pageCount; }

    public void setTotal(int total) {
        this.total = total;
        if(pageSize==0){
        	pageSize=10;
        }

        pageCount = total / pageSize + (total%pageSize==0 ? 0 : 1);
        // adjust pageIndex:
        if(total==0) {
            if(pageIndex!=1)
                throw new IndexOutOfBoundsException("Page index out of range.");
        }
        else {
            if(pageIndex>pageCount)
                throw new IndexOutOfBoundsException("Page index out of range.");
        }
    }

    public boolean isEmpty() {
        return total==0;
    }
	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}
    
	public int getTotal() {
	
		return total;
	}

	
	/*public void setTotal(int total) {
	
		this.total = total;
	}*/

	public Page() { }

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	
}
