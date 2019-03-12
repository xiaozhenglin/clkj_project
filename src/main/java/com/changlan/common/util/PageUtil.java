package com.changlan.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PageUtil<Class> {
	/**原集合*/
    private List content;
 
    /** 当前页 */
    private int number;

    /** 每页条数 */
    private int size ;

    /** 总页数 */
    private int totalPages = 0;

    /** 总数据条数 */
    private int totalElements = 0;

    /** 得到对象 */
    @SuppressWarnings("unchecked")
	public static PageUtil listToPage(List data,int pageNum,int pageSize) {
    	PageUtil page = new PageUtil();
    	page.setNumber(pageNum);
    	page.setSize(pageSize);
        if (data == null || data.isEmpty()) {
        	page.setContent(new ArrayList<>());
        	return page;
        }
        page.setContent(convertData(data,pageNum,pageSize));
        page.setTotalElements(data.size());
        page.setTotalPages((page.getTotalElements() + pageSize - 1) / pageSize );
        return page;
    }
 
    /**
     * 得到分页后的数据
     */
    public static List convertData(List data,int nowPage,int pageSize) {
        int fromIndex = (nowPage) * pageSize;
        if (fromIndex >= data.size()) {
            return Collections.emptyList();//空数组
        }
        if(fromIndex<0){
        	return Collections.emptyList();//空数组
        }
        int toIndex = (nowPage + 1) * pageSize;
        if (toIndex >= data.size()) {
            toIndex = data.size();
        }
        return data.subList(fromIndex, toIndex);
    }

	public List getContent() {
		return content;
	}

	public void setContent(List content) {
		this.content = content;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}
 

	
}