package com.changlan.indicator.action;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.command.service.ICommandCategoryService;
import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblCommandCategoryEntity;
import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.entity.TblIndicatorCategoriesEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.indicator.service.IIndicatorCategoryService;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.point.service.ICompanyGropService;

@RestController
@RequestMapping("/admin/indicator/category")
public class IndicatorCategoryController extends BaseController{

	@Autowired
	ICrudService crudService;
	
	@Autowired
	IIndicatorCategoryService  indicatorCategoryService;
	
	//修改或者保存
	@RequestMapping("/save")
	@Transactional
	public ResponseEntity<Object>  save(TblIndicatorCategoriesEntity entity ) throws Exception { 
		Boolean exist = indicatorCategoryService.existCatergoryName(entity);
		if(exist) {
			throw new MyDefineException(PoinErrorType.NAME_EXIST);
		}
		TblIndicatorCategoriesEntity update = (TblIndicatorCategoriesEntity)crudService.update(entity, true); 
		if(update == null) {
			throw new MyDefineException(PoinErrorType.SAVE_EROOR);
		}
		return success(update);
	}

	@RequestMapping("/list")
	public ResponseEntity<Object>  companyGropList(Integer id) {
		List<TblIndicatorCategoriesEntity> list = indicatorCategoryService.getAll(id);
		return success(list);
	}
	
	@RequestMapping("/delete")
	@Transactional
	public ResponseEntity<Object>  delete(TblIndicatorCategoriesEntity entity) throws MyDefineException { 
		TblIndicatorCategoriesEntity find = (TblIndicatorCategoriesEntity)crudService.get(entity.getCategoryId(),TblIndicatorCategoriesEntity.class,true);
		if(find == null) {
			throw new MyDefineException(PoinErrorType.NOT_EXIST);
		}
		Boolean delete = crudService.deleteBySql("DELETE FROM TBL_INDICATOR_CATEGORIES WHERE CATEGORY_ID = " +entity.getCategoryId() , true);
		return success(delete);
	}
	
}
