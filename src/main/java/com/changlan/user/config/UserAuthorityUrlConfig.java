package com.changlan.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class UserAuthorityUrlConfig {
	
	public static String notRequireAuthorityUrl;

	public static String getNotRequireAuthorityUrl() {
		return notRequireAuthorityUrl;
	}
	
	@Value("${not_require_user_authority_url}")
	public  void setNotRequireAuthorityUrl(String notRequireAuthorityUrl) {
		UserAuthorityUrlConfig.notRequireAuthorityUrl = notRequireAuthorityUrl;
	}
	
	
	
}
