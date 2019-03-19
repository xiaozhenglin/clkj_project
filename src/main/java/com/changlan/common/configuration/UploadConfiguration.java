package com.changlan.common.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Component
@Configuration
public class UploadConfiguration {

	public static String zipPath;

	public static String uploadPath;


	@Value("${upload_image.zipPath}")
	public  void setZipPath(String zipPath) {
		UploadConfiguration.zipPath = zipPath;
	}
	
	public static String getZipPath() {
		return zipPath;
	}

	@Value("${upload_image.uploadPath}")
	public  void setUploadPath(String uploadPath) {
		UploadConfiguration.uploadPath = uploadPath;
	}
	
	public static String getUploadPath() {
		return uploadPath;
	}

}
