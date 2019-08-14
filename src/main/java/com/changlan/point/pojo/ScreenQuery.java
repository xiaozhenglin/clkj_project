package com.changlan.point.pojo;

public class ScreenQuery {
	private String lineName;
	private String pointName;
	private Integer LineId;
	private Integer pointId;
	private Integer channelId;
	private String pointNameOrLineName;
	private Integer isCorner;
	private String dianyaLever;

	public Long begin;
	public Long end;
	
	
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public Integer getLineId() {
		return LineId;
	}
	public void setLineId(Integer lineId) {
		LineId = lineId;
	}
	public Integer getPointId() {
		return pointId;
	}
	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}
	public String getPointNameOrLineName() {
		return pointNameOrLineName;
	}
	public void setPointNameOrLineName(String pointNameOrLineName) {
		this.pointNameOrLineName = pointNameOrLineName;
	}
	public Long getBegin() {
		return begin;
	}
	public void setBegin(Long begin) {
		this.begin = begin;
	}
	public Long getEnd() {
		return end;
	}
	public void setEnd(Long end) {
		this.end = end;
	}
	public Integer getIsCorner() {
		return isCorner;
	}
	public void setIsCorner(Integer isCorner) {
		this.isCorner = isCorner;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getDianyaLever() {
		return dianyaLever;
	}
	public void setDianyaLever(String dianyaLever) {
		this.dianyaLever = dianyaLever;
	}
	
	
	
}
