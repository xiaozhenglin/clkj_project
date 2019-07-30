package com.changlan.alarm.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.alarm.service.IAlarmRuleService;
import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TBLAlarmCategoryEntity;
import com.changlan.common.entity.TblAlarmRuleEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.point.pojo.PoinErrorType;

@RestController
@RequestMapping("/admin/alarm/category")
public class AlarmCategoryController extends BaseController{
	
	@Autowired
	ICrudService crudService;

	@RequestMapping("/list")
	public ResponseEntity<Object>  list(Integer id) {  
		Map map = new HashMap();
		if(id!=null) {
			map.put("alarmCategoryId", new ParamMatcher(id));
		}
		List<TBLAlarmCategoryEntity> list = crudService.findByMoreFiled(TBLAlarmCategoryEntity.class, map, true);
		return success(list);
	} 
	
	
}
