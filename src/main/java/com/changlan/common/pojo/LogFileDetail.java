package com.changlan.common.pojo;
import java.io.Serializable;

public class LogFileDetail {
     long beginTime;   //查询开始时间
     long endTime;     //查询结束时间
     String type;       //日志类别
     String provider;    //日志提供者
     String fileName;    //文件名
     String filePath;    //文件路径
   
     
     
	public LogFileDetail(long beginTime, long endTime, String type, String provider, String fileName) {
		super();
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.type = type;
		this.provider = provider;
		this.fileName = fileName;
		this.filePath = filePath;
	}
	
	public LogFileDetail() {
		super();
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
}
