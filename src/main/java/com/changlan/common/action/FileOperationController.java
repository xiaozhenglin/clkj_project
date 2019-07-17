package com.changlan.common.action;

import java.io.File;

import java.util.Date;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.changlan.common.configuration.UploadConfiguration;
import com.changlan.common.entity.TblAlarmDownRecordEntity;
import com.changlan.common.entity.TblDvdEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.pojo.TblDvdQuery;
import com.changlan.common.service.FileOperationService;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.UUIDUtil;
import com.changlan.user.pojo.UserErrorType;

@RestController
@RequestMapping("/admin/picture")
public class FileOperationController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ICrudService crudService;

	@Autowired
	private FileOperationService fileOperationService;
//	@Autowired
//	private  ResourceLoader resourceLoader;
	
	/**
	 * 新增上传图片
	 * @param IdForm
	 * @return RestResult<Object>
	 */
	@RequestMapping(value = "/create")
	public ResponseEntity<Object> uploadImg(String pointId ,MultipartFile file) throws Exception{
		//MultipartFile file = null ;
		//logger.info(file.getOriginalFilename()); 
		String newImageName = UUIDUtil.getUUID() + file.getOriginalFilename();
		String newRealpath = UploadConfiguration.getUploadPath() + "/" + newImageName;
		File newFile = new File(newRealpath);								
		try {
			file.transferTo(newFile);
		} catch (Exception e) {
			logger.info("上传出错"+e.getMessage());
			throw new MyDefineException(UserErrorType.UPLOAD_ERROR);
		}
		
		TblDvdEntity entity = new TblDvdEntity();
		entity.setName(newImageName);
		entity.setCreatetime(new Date());
		entity.setPointId(Integer.parseInt(pointId));
		entity.setRecordUser(userIsLogin().getAdminUserId());
		entity.setPicture_url(newRealpath);
		
		crudService.save(entity, true);
		return success("create");
	}
		
	//图片删除
	@RequestMapping(value = "/delete")
	public ResponseEntity<Object> deleteImg(TblDvdEntity entity) throws Exception{
		List<TblDvdEntity> list = fileOperationService.getAll(entity.getDvd_id(),entity.getName(),entity.getPointId());
		if(ListUtil.isEmpty(list)) {
			throw new MyDefineException("0000","没有找到记录",false,null); 
		}
		Boolean isSuccess = crudService.deleteBySql("DELETE FROM TBL_DVD WHERE dvd_id ="+entity.getDvd_id(), true); 
		return success(isSuccess);
	}
	
	//图片修改
	@RequestMapping(value = "/modify")
	public ResponseEntity<Object> modifyImg(TblDvdEntity entity) throws Exception{
		if(entity.getDvd_id()!=null) {
			entity.setModifytime(new Date());
			entity.setRecordUser(userIsLogin().getAdminUserId());
			TblDvdEntity update = (TblDvdEntity)crudService.update(entity, true); 
			return success(update);
		}
		return success(null);
	}
	

	//展示图片
	@RequestMapping(value = "/list")
	public ResponseEntity<Object> getPage(TblDvdQuery query) throws Exception {
		Page<TblDvdEntity> result = fileOperationService.getPage(query,getPage());
		return success(result);
	
	}
	
	
	
	
}
