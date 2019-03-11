package com.changlan.point.pojo;

import com.changlan.common.pojo.PointStatus;

public class SimplePoint {
	private String registPackage;
	private PointStatus status;
	public SimplePoint(String registPackage, PointStatus status) {
		super();
		this.registPackage = registPackage;
		this.status = status;
	}
	public SimplePoint() {
		super();
	}
	public String getRegistPackage() {
		return registPackage;
	}
	public void setRegistPackage(String registPackage) {
		this.registPackage = registPackage;
	}
	public PointStatus getStatus() {
		return status;
	}
	public void setStatus(PointStatus status) {
		this.status = status;
	}
	
}
