package com.changlan.alarm.pojo;

import com.changlan.common.entity.TBLAlarmCategoryEntity;
import com.changlan.common.entity.TblAlarmRuleEntity;
import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;

public class TblAlarmRuleDetail {
	private TblAlarmRuleEntity alarmRule;
	private TBLAlarmCategoryEntity alarmCategory;
	private TblIndicatorValueEntity indicator;
	private TblPointsEntity point;
	
	
	public TblAlarmRuleDetail(TblAlarmRuleEntity alarmRule) {
		ICrudService crudService = SpringUtil.getICrudService(); 
		this.alarmRule = alarmRule;
		this.alarmCategory = (TBLAlarmCategoryEntity)crudService.get(alarmRule.getAlarmCategoryId(), TBLAlarmCategoryEntity.class, true);
		this.point = (TblPointsEntity)crudService.get(alarmRule.getPointId(), TblPointsEntity.class, true);
		this.indicator = (TblIndicatorValueEntity)crudService.get(alarmRule.getAlarmCategoryId(), TblIndicatorValueEntity.class, true);
	}
	
	
	public TblAlarmRuleDetail() {
		super();
	}


	public TblAlarmRuleEntity getAlarmRule() {
		return alarmRule;
	}
	public void setAlarmRule(TblAlarmRuleEntity alarmRule) {
		this.alarmRule = alarmRule;
	}
	public TblIndicatorValueEntity getIndicator() {
		return indicator;
	}
	public void setIndicator(TblIndicatorValueEntity indicator) {
		this.indicator = indicator;
	}
	public TBLAlarmCategoryEntity getAlarmCategory() {
		return alarmCategory;
	}
	public void setAlarmCategory(TBLAlarmCategoryEntity alarmCategory) {
		this.alarmCategory = alarmCategory;
	}


	public TblPointsEntity getPoint() {
		return point;
	}


	public void setPoint(TblPointsEntity point) {
		this.point = point;
	}
	
}
