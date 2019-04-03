package com.changlan.alarm.action;

import java.util.List;

import javax.persistence.Column;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.alarm.pojo.TblAlarmRuleDetail;
import com.changlan.alarm.service.IAlarmRuleService;
import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblAlarmRuleEntity;
import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.indicator.pojo.IndiCatorValueDetail;
import com.changlan.indicator.service.IIndicatoryValueService;
import com.changlan.point.pojo.PoinErrorType;

@RestController
@RequestMapping("/admin/alarm/rule")
public class AlarmRuleController  extends  BaseController{

	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private IAlarmRuleService  alarmRuleService;
	
	//修改或者保存
	@RequestMapping("/save")
	@Transactional
	public ResponseEntity<Object>  save(TblAlarmRuleEntity entity ) throws Exception { 
		Boolean exist = alarmRuleService.existName(entity);
		if(exist) {
			throw new MyDefineException(PoinErrorType.NAME_EXIST);
		}
		TblAlarmRuleEntity update = (TblAlarmRuleEntity)crudService.update(entity, true); 
		if(update == null) {
			throw new MyDefineException(PoinErrorType.SAVE_EROOR);
		}
		return success(update);
	}

	@RequestMapping("/list")
	public ResponseEntity<Object>  list(TblAlarmRuleEntity entity) {  
//		List<TblAlarmRuleEntity> list = alarmRuleService.getAll(entity.getAlarmRuleId(),entity.getIndicatorValueId(),entity.getPointId());
		Page<TblAlarmRuleDetail> result = alarmRuleService.getPage(entity,getPage());
		return success(result);
	}
	
//	未加权限
//	@RequestMapping("/info")
//	public ResponseEntity<Object>  info(Integer id) {  
//		TblAlarmRuleDetail list = alarmRuleService.getDetail(id);
//		return success(list);
//	}
	
}