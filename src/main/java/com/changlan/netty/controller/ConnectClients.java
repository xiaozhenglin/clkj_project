package com.changlan.netty.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import io.netty.channel.Channel;

public class ConnectClients {
	
	//记录监控点ip地址 对应的通道 
	public static Map<String,Channel> clients = new HashMap(); 
	
}
