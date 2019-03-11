package com.changlan.point.action;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.point.service.ICompanyGropService;
import com.changlan.point.service.ICompanyInfoService;

@RestController
@RequestMapping("/admin/company/group")
public class CompanyGroupController extends BaseController{
	
	@Autowired
	ICrudService crudService;
	
	@Autowired
	ICompanyGropService  companyGroupService;
	
	//修改或者保存
	@RequestMapping("/save")
	@Transactional
	public ResponseEntity<Object>  functionList(TblCompanyGroupEntity entity ) throws Exception { 
		Boolean exist = companyGroupService.existGroupName(entity);
		if(exist) {
			throw new MyDefineException(PoinErrorType.COMPANY_GROUP_NAME_EXIST.getCode(), PoinErrorType.COMPANY_GROUP_NAME_EXIST.getName(), false, null);
		}
		TblCompanyGroupEntity update = (TblCompanyGroupEntity)crudService.update(entity, true); 
		if(update == null) {
			throw new MyDefineException(PoinErrorType.SAVE_EROOR.getCode(), PoinErrorType.SAVE_EROOR.getName(), false, null);
		}
		return success(update);
	}

	@RequestMapping("/list")
	public ResponseEntity<Object>  companyGropList(TblCompanyGroupEntity group) {
		List<TblCompanyGroupEntity> list = companyGroupService.getAllGroup(group);
		return success(list);
	}
	
}