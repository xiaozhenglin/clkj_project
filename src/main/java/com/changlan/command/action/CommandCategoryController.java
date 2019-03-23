package com.changlan.command.action;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.command.pojo.CommandCategoryDetail;
import com.changlan.command.service.ICommandCategoryService;
import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblCommandCategoryEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.point.pojo.CompanyDetail;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.point.service.ICompanyInfoService;

@RestController
@RequestMapping("/admin/command/category")
public class CommandCategoryController extends BaseController{
	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private ICommandCategoryService commandCategoryService;
	
//	@RequestMapping("/save")
//	@Transactional
//	public ResponseEntity<Object>  save(TblCommandCategoryEntity category) throws MyDefineException {
//		Boolean existName = commandCategoryService.existName(category); 
//		if(existName) {
//			throw new MyDefineException(PoinErrorType.NAME_EXIST);
//		}
//		TblCommandCategoryEntity entity = commandCategoryService.save(category); 
//		if(entity ==null) {
//			throw new MyDefineException(PoinErrorType.SAVE_EROOR);
//		}
//		return success(entity);
//	}
	
	@RequestMapping("/list")
	public ResponseEntity<Object>  lineList(Integer id) {
		 List<TblCommandCategoryEntity> result = commandCategoryService.categoryList(id) ; 
		 return success(result) ;
	}
	
}
