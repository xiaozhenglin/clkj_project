package com.changlan.point.pojo;

public enum RedirectType {
	MANAGER_BACK_GROUD("管理后台","/upfront/center/main"), 
	MONITOR_SCREEN("监控大屏","/upfront/comprehensive/situation-home"),
	MONITOR_CENTER("监控中心","/upfront/center/monitoring-center");
	
	private String code;
	private String url;
	
	private RedirectType(String code, String url) {
		this.code = code;
		this.url = url;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
		
	
}
