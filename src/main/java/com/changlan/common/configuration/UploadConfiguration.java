package com.changlan.common.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Component
@Configuration
public class UploadConfiguration {

	public static String uploadPath;

	@Value("${upload_image.path}")
	public  void setUploadPath(String uploadPath) {
		UploadConfiguration.uploadPath = uploadPath;
	}
	
	public static String getUploadPath() {
		return uploadPath;
	}

}
