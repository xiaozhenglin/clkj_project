package com.changlan.common.action;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.changlan.common.configuration.UploadConfiguration;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.util.FileUtil;
import com.changlan.common.util.UUIDUtil;
import com.changlan.user.pojo.UserErrorType;

@RestController
public class UploadFileController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 上传图片
	 * @param IdForm
	 * @return RestResult<Object>
	 */
	@RequestMapping(value = "/admin/uploadImg")
	public ResponseEntity<Object> uploadImg(MultipartFile file) throws Exception{
		logger.info(file.getOriginalFilename()); 
		String newImageName = UUIDUtil.getUUID() + file.getOriginalFilename();
		String newRealpath = UploadConfiguration.getUploadPath() + "/" + newImageName;
		File newFile = new File(newRealpath);
		try {
			file.transferTo(newFile);
		} catch (Exception e) {
			logger.info("上传出错"+e.getMessage());
			throw new MyDefineException(UserErrorType.UPLOAD_ERROR);
		}
		return success(newRealpath);
	}
	 

}
