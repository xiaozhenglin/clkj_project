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
	ICrudService crudService;
	
	@Autowired
	IIndicatoryValueService  indicatorValueService;
	
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

}
