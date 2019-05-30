package com.changlan.common.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.changlan.common.configuration.UploadConfiguration;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.util.FileUtil;
import com.changlan.common.util.StringUtil;
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
	
	//未加入权限表 日志文件读取
	@RequestMapping(value = "/admin/log/list")
	public ResponseEntity<Object> getLog(String url) throws Exception{
		logger.info(url); 
		File file = new File(url);
		try {
			List<File> fileList = FileUtil.getFileList("D:\\changlan\\adminplat\\console-2019-05-24.log", new ArrayList<File>()); 
			return success(fileList);
		} catch (Exception e) {
			throw e;
		}
	}
	
	 
	public static void main(String[] args) {
		File file = new File("D:\\changlan\\adminplat\\console-2019-05-24.log");
		try {
			String readToBuffer = FileUtil.readToBuffer(file);
			System.out.println(readToBuffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<File> fileList = FileUtil.getFileList("D:\\changlan\\adminplat", new ArrayList<File>()); 
		System.out.println(fileList.size()); 
		for(File file2 : fileList) {
			System.out.println(file2.getAbsolutePath() + "-----"+ file2.getName()); 
		}
	}
	
	
	
}
