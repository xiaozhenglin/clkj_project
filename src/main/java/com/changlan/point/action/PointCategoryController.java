package com.changlan.point.action;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblPointAlamDataEntity;
import com.changlan.common.entity.TblPointCategoryEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.point.pojo.CompanyDetail;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.point.service.IPointCategoryService;

@RestController
@RequestMapping("/admin/point/category")
public class PointCategoryController extends BaseController {
	@Autowired
	private ICrudService crudService;
	
	@Autowired
	IPointCategoryService categoryService;
	
	@RequestMapping("/save")
	@Transactional
	public ResponseEntity<Object>  save(TblPointCategoryEntity entity) throws MyDefineException { 
		Boolean exist = categoryService.existName(entity);
		if(exist) {
			throw new MyDefineException(PoinErrorType.POINT_CATEGORY_NAME_EXIST.getCode(), PoinErrorType.POINT_CATEGORY_NAME_EXIST.getName(), false, null);
		}
		TblPointCategoryEntity update = (TblPointCategoryEntity)crudService.update(entity, true); 
		if(update == null) {
			throw new MyDefineException(PoinErrorType.SAVE_EROOR.getCode(), PoinErrorType.SAVE_EROOR.getName(), false, null);
		}
		return success(update);
	}
	
	
	@RequestMapping("/list")
	public ResponseEntity<Object>  categoryList(TblPointCategoryEntity entity) {
		List<TblPointCategoryEntity> list = categoryService.getAll(entity); 
		return success(list);
	}
	
	@RequestMapping("/delete")
	@Transactional
	public ResponseEntity<Object>  delete(TblPointCategoryEntity entity) throws MyDefineException { 
		TblPointCategoryEntity find = (TblPointCategoryEntity)crudService.get(entity.getPointCatgoryId(),TblPointCategoryEntity.class,true);
		if(find == null) {
			throw new MyDefineException(PoinErrorType.POINT_CATEGORY_NOT_EXIST);
		}
		Boolean delete = crudService.deleteBySql("DELETE FROM TBL_POINT_CATEGORY WHERE POINT_CATGORY_ID = " +entity.getPointCatgoryId() , true); 
		return success(delete);
	}
}
