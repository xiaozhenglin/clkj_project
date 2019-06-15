package com.changlan.alarm.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.changlan.common.entity.TblPointAlamDataEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.PageUtil;
import com.changlan.point.pojo.PointDataDetail;
import com.changlan.point.vo.PoinDataVo;

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
	
}
