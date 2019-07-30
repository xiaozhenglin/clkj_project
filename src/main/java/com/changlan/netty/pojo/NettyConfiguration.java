package com.changlan.netty.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class NettyConfiguration {
	
//	public static String nettyIp ;
	public static int nettyPort;
	
	public static int httpNettyPort;
	
//	public static String getNettyIp() {
//		return nettyIp;
//	}
	
//	@Value("${netty_server_host}")
//	public  void setNettyIp(String nettyIp) {
//		NettyConfiguration.nettyIp = nettyIp;
//	}
	public static int getNettyPort() {
		return nettyPort;
	}
	
	@Value("${netty_server_port}")
	public  void setNettyPort(int nettyPort) {
		NettyConfiguration.nettyPort = nettyPort;
	}

	public static int getHttpNettyPort() {
		return httpNettyPort;
	}
	
	@Value("${http_netty_server_port}")
	public  void setHttpNettyPort(int httpNettyPort) {
		NettyConfiguration.httpNettyPort = httpNettyPort;
	}	


	
	
}
