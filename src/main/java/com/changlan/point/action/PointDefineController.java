package com.changlan.point.action;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblPointCategoryEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.point.pojo.LineDetail;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.point.pojo.PointInfoDetail;
import com.changlan.point.service.ILineService;
import com.changlan.point.service.IPointDefineService;

@RestController
@RequestMapping("/admin/point/define")
public class PointDefineController extends BaseController{
	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private IPointDefineService pointDefineService;
	
	//修改或者保存
	@RequestMapping("/save")
	@Transactional
	public ResponseEntity<Object>  lineSave(TblPointsEntity entity ) throws Exception { 
		Boolean exist = pointDefineService.existPointpName(entity);
		if(exist) {
			throw new MyDefineException(PoinErrorType.NAME_EXIST.getCode(), PoinErrorType.NAME_EXIST.getName(), false, null);
		}
		TblPointsEntity update = (TblPointsEntity)crudService.update(entity, true); 
		if(update==null) {
			throw new MyDefineException(PoinErrorType.SAVE_EROOR.getCode(), PoinErrorType.SAVE_EROOR.getName(), false, null);
		}
		return success(update);
	}
	
	@RequestMapping("/list") 
	public ResponseEntity<Object>  list(TblPointsEntity entity) {
		Page<PointInfoDetail> list = pointDefineService.getPage(entity,getPage()); 
		return success(list);
	}
	
	@RequestMapping("/delete")
	@Transactional
	public ResponseEntity<Object>  delete(TblPointsEntity entity) throws MyDefineException { 
		TblPointsEntity companyEntity = (TblPointsEntity)crudService.get(entity.getPointId(),TblPointsEntity.class,true);
		if(companyEntity == null) {
			throw new MyDefineException(PoinErrorType.POINT_NOT_EXIST);
		}
		Boolean delete = crudService.delete(entity, true);
		return success(delete);
	}
	
}
