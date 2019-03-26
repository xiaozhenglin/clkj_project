package com.changlan.point.action;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblAlarmDownRecordEntity;
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
	
	//数据表 未加权限
	@RequestMapping("/table") 
	public ResponseEntity<Object>  table(Date begin,Date end,Integer categroryId) {
		List list = pointDataService.getTable(begin,end,categroryId); 
		return success(list);
	}
	
//	@RequestMapping("/update") 
//	public ResponseEntity<Object>  update(TblPoinDataEntity entity) {
//		TblPoinDataEntity update = pointDataService.update(entity); 
//		return success(update);
//	}
//	
//	//监控点数据报警进行处理
//	@RequestMapping("/down/record") 
//	public ResponseEntity<Object>  downRecord(String reason,String downResult) throws Exception { 
//		TblAlarmDownRecordEntity  entity = new TblAlarmDownRecordEntity();
//		entity.setDownResult(downResult);
//		entity.setReason(reason); 
//		entity.setRecordUser(userIsLogin().getAdminUserId()); 
//		entity.setRecordTime(new Date()); 
//		Object update = crudService.update(entity, true);
//		return success(update);
//	}
	
}
