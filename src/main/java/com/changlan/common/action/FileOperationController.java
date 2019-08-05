package com.changlan.common.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

import com.changlan.common.configuration.UploadConfiguration;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblAlarmDownRecordEntity;
import com.changlan.common.entity.TblDvdEntity;
import com.changlan.common.entity.TblMonitorSystemEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.pojo.TblDvdQuery;
import com.changlan.common.service.FileOperationService;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.UUIDUtil;
import com.changlan.user.pojo.LoginUser;
import com.changlan.user.pojo.UserErrorType;

@RestController
@RequestMapping("/admin/picture")
public class FileOperationController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ICrudService crudService;

	@Autowired
	private FileOperationService fileOperationService;
	
	/**
	 * 新增上传图片
	 * @param IdForm
	 * @return RestResult<Object>
	 */
	@RequestMapping(value = "/create")
	public ResponseEntity<Object> uploadImg(TblDvdEntity entity) throws Exception{
		TblAdminUserEntity currentUser = LoginUser.getCurrentUser();
		if(currentUser!=null) {
			entity.setRecordUser(currentUser.getAdminUserId());
		}
		entity.setCreatetime(new Date());
		Object save = crudService.update(entity, true); 
		return success(save);
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
		TblAdminUserEntity currentUser = LoginUser.getCurrentUser();
		if(currentUser!=null) {
			entity.setRecordUser(currentUser.getAdminUserId());
		}
		entity.setModifytime(new Date());
		Object save = crudService.update(entity, true); 
		return success(save);
	}
	
	//展示图片
	@RequestMapping(value = "/list")
	public ResponseEntity<Object> getPage(TblDvdQuery query) throws Exception {
		List<Object> all = crudService.getAll(TblDvdEntity.class, true); 
		return success(all);
	}
	
}
