package com.changlan.alarm.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.alarm.pojo.TblAlarmDataDetail;
import com.changlan.alarm.service.IAlarmDataService;
import com.changlan.alarm.service.IAlarmService;
import com.changlan.alarm.vo.AlarmDataVo;
import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TBLAlarmCategoryEntity;
import com.changlan.common.entity.TblAlarmDownRecordEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblPointAlamDataEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.PageUtil;
import com.changlan.point.pojo.PointDataDetail;
import com.changlan.point.vo.PoinDataVo;
import com.changlan.user.pojo.LoginUser;

@RestController
@RequestMapping("/admin/alarm/data")
public class AlarmDataController extends  BaseController{
	
	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private IAlarmDataService alarmDataService;
	
	@RequestMapping("/list")
	public ResponseEntity<Object>  list(TblPointAlamDataEntity entity) {  
//		List<AlarmDataVo> result = new ArrayList<AlarmDataVo>();
//		Page<TblAlarmDataDetail> details = alarmDataService.getPage(entity,getPage());
//		for(TblAlarmDataDetail detail : details) {
//			AlarmDataVo  vo = new AlarmDataVo(detail);
//			result.add(vo);
//		}
//		return success(new PageImpl(result, getPage(), details.getTotalElements()));
		Page<TblAlarmDataDetail> details = alarmDataService.getPage(entity,getPage());
		return success(details);
	}
	
//	未加权限
//	@RequestMapping("/info")
//	public ResponseEntity<Object>  list(Integer id) {  
//		TblAlarmDataDetail detail = alarmDataService.getDetail(id);
//		return success(detail);
//	}
	
	
	@RequestMapping("/down/record") 
	public ResponseEntity<Object>  downRecord(Integer alarmDataId,String reason,String downResult) throws Exception { 
		TblAlarmDownRecordEntity  entity = new TblAlarmDownRecordEntity();
		entity.setDownResult(downResult);
		entity.setReason(reason); 
//		if(LoginUser.getCurrentUser()!=null) {
//			entity.setRecordUser(userIsLogin().getAdminUserId()); 
//		}
//		entity.setRecordTime(new Date()); 
//		entity.setPointDataId(pointDataId); 
//		entity = (TblAlarmDownRecordEntity)crudService.update(entity, true);
//		
//		TblPoinDataEntity pointData = (TblPoinDataEntity)crudService.get(pointDataId, TblPoinDataEntity.class, true); 
//		pointData.setAlarmDown("报警已处理");
//		pointData.setAlarmDownRecord(entity.getAlamDownRecordId());
//		crudService.update(pointData, true);
		return success(true);
	}
	
	//监控点数据报警进行处理 记录列表
	@RequestMapping("/down/record/list") 
	public ResponseEntity<Object>  downRecordList(TblAlarmDownRecordEntity entity) throws Exception { 
		Map map = new HashMap();
//		if(entity.getAlamDownRecordId()!=null) {
//			map.put("alamDownRecordId", new ParamMatcher(entity.getAlamDownRecordId())); 
//		}
//		if(entity.getPointDataId()!=null) {
//			map.put("pointDataId",new ParamMatcher(entity.getPointDataId()));
//		}
//		Object result= crudService.findByMoreFiledAndPage(TblAlarmDownRecordEntity.class, map, true, getPage());
		return success(null);
	}
}
