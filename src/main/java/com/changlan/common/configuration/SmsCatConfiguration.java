package com.changlan.common.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class SmsCatConfiguration {

	
	public static String serverPortName;
	
	public static String serverPortBound;

	public static String getServerPortName() {
		return serverPortName;
	}

	@Value("${smscat.serverPortName}")
	public static void setServerPortName(String serverPortName) {
		SmsCatConfiguration.serverPortName = serverPortName;
	}

	public static String getServerPortBound() {
		return serverPortBound;
	}

	@Value("${smscat.serverPortBound}")
	public static void setServerPortBound(String serverPortBound) {
		SmsCatConfiguration.serverPortBound = serverPortBound;
	}


	

}
