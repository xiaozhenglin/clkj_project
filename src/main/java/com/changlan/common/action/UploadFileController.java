package com.changlan.common.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.changlan.common.configuration.UploadConfiguration;
import com.changlan.common.entity.TblSystemVarEntity;
import com.changlan.common.pojo.LogFileDetail;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.FileUtil;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.common.util.UUIDUtil;
import com.changlan.user.pojo.UserErrorType;

@RestController
public class UploadFileController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private  ICrudService crudService;

//	@Autowired
//	private  ResourceLoader resourceLoader;
	
	/**
	 * 上传图片
	 * @param IdForm
	 * @return RestResult<Object>
	 */
	@RequestMapping(value = "/admin/uploadImg")
	public ResponseEntity<Object> uploadImg(MultipartFile file) throws Exception{
		logger.info(file.getOriginalFilename()); 
//		String newImageName = UUIDUtil.getUUID() + file.getOriginalFilename();
		//String newRealpath = UploadConfiguration.getUploadPath() + "/" + file.getOriginalFilename();
		ICrudService crudService = SpringUtil.getBean(ICrudService.class);
    	Map map = new HashMap();
    	map.clear();
 		map.put("systemCode", new ParamMatcher("fileUploadPath"));
    	TblSystemVarEntity TblSystemVar  =  (TblSystemVarEntity) crudService.findOneByMoreFiled(TblSystemVarEntity.class,map,true);
		String  newRealpath  =  TblSystemVar.getSystemValue()  + "/" + file.getOriginalFilename();
		File newFile = new File(newRealpath); 
		try {
			file.transferTo(newFile);
		} catch (Exception e) {
			logger.info("上传出错"+e.getMessage());
			throw new MyDefineException(UserErrorType.UPLOAD_ERROR);
		}
		return success(newRealpath);
	}
		
	@RequestMapping(value = "/admin/downLogFile")
	public ResponseEntity<Object> downLogFile(String filePath) throws Exception{
		//System.out.println(filePath);
		HttpServletResponse response = getResponse();
		HttpServletRequest request = getReqeust();
		try {
			File file = new File(filePath);
			InputStream inputStream = new FileInputStream(file);
            OutputStream outputStream = response.getOutputStream();       
            //指明为下载
            response.setContentType("application/x-download");         
            response.addHeader("Content-Disposition", "attachment;fileName="+file.getName());   // 设置文件名
            response.setCharacterEncoding("UTF-8"); 
            //把输入流copy到输出流
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return success(filePath);
	}

	//不需要权限，图片文件
	@RequestMapping(value = "/admin/file/list")
	public ResponseEntity<Object> getLog(String url) throws Exception {
		logger.info("文件地址 ->"+url); 
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<LogFileDetail> result = new ArrayList<LogFileDetail>();
		try {
			//List<File> fileList = FileUtil.getFileList("E:\\adminplat", new ArrayList<File>()); 
			List<File> fileList = FileUtil.getFileList(url, new ArrayList<File>()); 
			for(File file : fileList) {
				LogFileDetail logDetail = new LogFileDetail();
				long time = file.lastModified();
				//if
				logDetail.setBeginTime(time);
				logDetail.setEndTime(time);
				logDetail.setType("console");
				logDetail.setProvider("");
				logDetail.setFileName(file.getName());
				logDetail.setFilePath(url);
                result.add(logDetail);				
			    
			}			
			return success(new PageImpl<LogFileDetail>(result));
		} catch (Exception e) {
			throw e;
		} 
//		  try {
//	            String pat h = Paths.get("D:\\uploads", "02ffedfb88f34a1988390349f6ffb91d20190306145009.png").toString();
//	            Resource resource = resourceLoader.getResource("file:" + path);
//	            return ResponseEntity.ok(resource);
//	        } catch (Exception e) {
//	            throw e;
//	        }
	}
	
	
	public static void main(String[] args) {
//		File file = new File("D:\\changlan\\adminplat\\console-2019-05-24.log");
//		try {
//			String readToBuffer = FileUtil.readToBuffer(file);
//			System.out.println(readToBuffer);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		List<File> fileList = FileUtil.getFileList("E:\\adminplat", new ArrayList<File>()); 
		System.out.println(fileList.size()); 
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		for(File file2 : fileList) {
			System.out.println(file2.getAbsolutePath() + "-----"+ file2.getName()+ "-----" + df.format(new Date(file2.lastModified()))); 
		}
	}
	
}
