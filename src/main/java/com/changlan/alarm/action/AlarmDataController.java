package com.changlan.alarm.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.alarm.pojo.AlarmDataQuery;
import com.changlan.alarm.pojo.AlarmDownRecordQuery;
import com.changlan.alarm.pojo.AlarmDownType;
import com.changlan.alarm.pojo.TblAlarmDataDetail;
import com.changlan.alarm.service.IAlarmDataService;
import com.changlan.alarm.service.IAlarmService;
import com.changlan.alarm.vo.AlarmDataVo;
import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TBLAlarmCategoryEntity;
import com.changlan.common.entity.TblAlarmDownRecordEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblPointAlamDataEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.PageUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.point.pojo.PoinErrorType;
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
	public ResponseEntity<Object>  list(AlarmDataQuery query) {  
		List<AlarmDataVo> result = new ArrayList<AlarmDataVo>();
		Page<TblAlarmDataDetail> details = alarmDataService.getPage(query,getPageAndOrderBy("ALARM_DATE"));
		for(TblAlarmDataDetail detail : details) {
			AlarmDataVo  vo = new AlarmDataVo(detail);
			result.add(vo);
		}
		return success(new PageImpl(result, getPage(), details.getTotalElements()));
//		Page<TblAlarmDataDetail> details = alarmDataService.getPage(entity,getPage());
//		return success(details);
	} 
	
//	未加权限
//	@RequestMapping("/info")
//	public ResponseEntity<Object>  list(Integer id) {  
//		TblAlarmDataDetail detail = alarmDataService.getDetail(id);
//		return success(detail);
//	}
	
	//修改为已读状态
	@RequestMapping("/already/read") 
	public ResponseEntity<Object>  read(TblPointAlamDataEntity entity) throws Exception { 
		if(entity.getAlarmId()!=null) {
			TblPointAlamDataEntity alarmData = (TblPointAlamDataEntity)crudService.get(entity.getAlarmId(), TblPointAlamDataEntity.class, true);
			alarmData.setDownStatus(AlarmDownType.ALREADY_READ.toString());
			Object update = crudService.update(alarmData, true);
			return success(update);
		}
		return success(null); 
	}
	
	 
	//监控点数据报警进行处理记录
	@RequestMapping("/down/record") 
	public ResponseEntity<Object>  downRecord(TblAlarmDownRecordEntity entity ,String userid) throws Exception { 
		if(entity.getAlarmDataId()!=null) {
			entity.setRecordTime(new Date());
			if(StringUtil.isNotEmpty(userIsLogin().getAdminUserId())) {
				entity.setRecordUser(userIsLogin().getAdminUserId());
			}else {
				Iterator iter =  LoginUser.loginAppMap.entrySet().iterator();
				while (iter.hasNext()) {
					    Map.Entry entry = (Map.Entry) iter.next();
					    //Object key = entry.getKey();
					    String value = (String) entry.getValue();
					    if(value == userid) {
					    	break;
					    }else {
					    	throw new MyDefineException(PoinErrorType.APP_ILLEGAL_LOGIN);
					    }
				}
			}
			TblAlarmDownRecordEntity update = (TblAlarmDownRecordEntity)crudService.update(entity, true); 
			TblPointAlamDataEntity alarmData = (TblPointAlamDataEntity)crudService.get(entity.getAlarmDataId(), TblPointAlamDataEntity.class, true);
			alarmData.setAlarmDownRecordId(update.getAlamDownRecordId());
			alarmData.setDownStatus(AlarmDownType.DOWN.toString());
			crudService.update(alarmData, true);
			return success(update.getAlamDownRecordId());
		}
		return success(null); 
	} 
	
	//监控点数据报警进行处理 记录列表
	@RequestMapping("/down/record/list") 
	public ResponseEntity<Object>  downRecordList(AlarmDownRecordQuery query) throws Exception { 
		Page<TblAlarmDownRecordEntity> result = alarmDataService.getPage(query, getPageAndOrderBy("RECORD_TIME"));
		return success(result);
	}
	
}
