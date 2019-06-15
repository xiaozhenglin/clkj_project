package com.changlan.alarm.pojo;

import com.changlan.common.entity.TBLAlarmCategoryEntity;
import com.changlan.common.entity.TblAlarmRuleEntity;
import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;

public class TblAlarmRuleDetail {
	private TblAlarmRuleEntity alarmRule; //报警规则
	private TBLAlarmCategoryEntity alarmCategory; //报警规则类别
	private TblIndicatorValueEntity indicator;//指标信息
	private TblPointsEntity point;//监控点信息
	
	
	public TblAlarmRuleDetail(TblAlarmRuleEntity alarmRule) {
		ICrudService crudService = SpringUtil.getICrudService(); 
		this.alarmRule = alarmRule;
		this.alarmCategory = (TBLAlarmCategoryEntity)crudService.get(alarmRule.getAlarmCategoryId(), TBLAlarmCategoryEntity.class, true);
		this.point = (TblPointsEntity)crudService.get(alarmRule.getPointId(), TblPointsEntity.class, true);
		this.indicator = (TblIndicatorValueEntity)crudService.get(alarmRule.getIndicatorValueId(), TblIndicatorValueEntity.class, true);
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
