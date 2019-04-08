package com.changlan.indicator.action;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblIndicatorCategoriesEntity;
import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.indicator.pojo.IndiCatorValueDetail;
import com.changlan.indicator.service.IIndicatorCategoryService;
import com.changlan.indicator.service.IIndicatoryValueService;
import com.changlan.point.pojo.PoinErrorType;

@RestController
@RequestMapping("/admin/indicator/value")
public class IndicatorValueController extends  BaseController{

	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private IIndicatoryValueService  indicatorValueService;
	
	//修改或者保存
	@RequestMapping("/save")
	@Transactional
	public ResponseEntity<Object>  save(TblIndicatorValueEntity entity ) throws Exception { 
		Boolean exist = indicatorValueService.existName(entity);
		if(exist) {
			throw new MyDefineException(PoinErrorType.NAME_EXIST);
		}
		TblIndicatorValueEntity update = (TblIndicatorValueEntity)crudService.update(entity, true); 
		if(update == null) {
			throw new MyDefineException(PoinErrorType.SAVE_EROOR);
		}
		return success(update);
	}

	@RequestMapping("/list")
	public ResponseEntity<Object>  list(Integer id,Integer categoryId) {
		List<IndiCatorValueDetail> list = indicatorValueService.getAll(id,categoryId);
		return success(list);
	}
	
	//未加入权限表
	@RequestMapping("/delete")
	@Transactional
	public ResponseEntity<Object>  delete(TblIndicatorValueEntity entity) throws MyDefineException { 
		TblIndicatorValueEntity find = (TblIndicatorValueEntity)crudService.get(entity.getIndicatorId(),TblIndicatorValueEntity.class,true);
		if(find == null) {
			throw new MyDefineException(PoinErrorType.NOT_EXIST);
		}
		Boolean delete = crudService.delete(entity, true);
		return success(delete);
	}
	
}
