package com.changlan.point.action;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.point.pojo.PointDataDetail;
import com.changlan.point.pojo.PointInfoDetail;
import com.changlan.point.service.IPointDataService;
import com.changlan.point.service.IPointDefineService;

@RestController
@RequestMapping("/admin/point/data")
public class PoinDataController extends BaseController{
	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private IPointDataService pointDataService;
	
	@RequestMapping("/list") 
	public ResponseEntity<Object>  list(TblPoinDataEntity entity) {
		Page<PointDataDetail> list = pointDataService.getAll(entity,getPage()); 
		return success(list);
	}
}
