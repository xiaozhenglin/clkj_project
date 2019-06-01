package com.changlan.point.action;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblCompanyChannelEntity;
import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.point.service.IChannelService;
import com.changlan.point.service.ICompanyGropService;

@RestController
@RequestMapping("/admin/company/channel")
public class ChannelController extends BaseController{
	
	@Autowired
	ICrudService crudService;
	
	@Autowired
	IChannelService  channelService;
	
	//修改或者保存
	@RequestMapping("/save")
	@Transactional
	public ResponseEntity<Object>  functionList(TblCompanyChannelEntity entity ) throws Exception { 
		Boolean exist = channelService.existChannelName(entity);
		if(exist) {
			throw new MyDefineException(PoinErrorType.COMPANY_CHANNEL_NAME_EXIST);
		}
		TblCompanyGroupEntity update = (TblCompanyGroupEntity)crudService.update(entity, true); 
		if(update == null) {
			throw new MyDefineException(PoinErrorType.SAVE_EROOR);
		}
		return success(update);
	}

	@RequestMapping("/list")
	public ResponseEntity<Object>  companyGropList(TblCompanyChannelEntity entity) {
		List<TblCompanyChannelEntity> list = channelService.getAllChannel(entity);
		return success(list);
	}
	
	
//	@RequestMapping("/delete")
//	@Transactional
//	public ResponseEntity<Object>  delete(TblCompanyChannelEntity entity) throws MyDefineException { 
//		TblCompanyGroupEntity companyEntity = (TblCompanyGroupEntity)crudService.get(entity.getGroupId(),TblCompanyGroupEntity.class,true);
//		if(companyEntity != null) {
//			Boolean deleteBySql = crudService.deleteBySql("DELETE FROM TBL_COMPANY_GROUP WHERE GROUP_ID = " +entity.getGroupId() , true);
//			return success(deleteBySql);
//		}
//		return success(false);
//	}

}
