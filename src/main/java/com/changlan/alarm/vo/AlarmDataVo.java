package com.changlan.alarm.vo;

import com.changlan.alarm.pojo.TblAlarmDataDetail;
import com.changlan.common.entity.TBLAlarmCategoryEntity;
import com.changlan.common.entity.TblAlarmDownRecordEntity;
import com.changlan.common.entity.TblAlarmRuleEntity;
import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblPointAlamDataEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;
import com.changlan.point.pojo.PointStatus;

public class AlarmDataVo {
	
	private TblPointAlamDataEntity alarmData;
	private TblAlarmDownRecordEntity downRecord ;
	
	public AlarmDataVo(TblAlarmDataDetail detail) {
		TblPointsEntity point = detail.getPoint(); 
		TblPointAlamDataEntity alarmData = detail.getAlarmData(); 
		TblAlarmRuleEntity alarmRule = detail.getAlarmRule(); 
		TblIndicatorValueEntity indicator = detail.getIndicator(); 
				
		this.downRecord = detail.getDownRecord();
		this.alarmData = alarmData;
		this.alarmData.setUnit(indicator.getUnit());
		this.alarmData.setPointName(point.getPointName());
		this.alarmData.setIndicatorName(indicator.getName());
		ICrudService crudService = SpringUtil.getICrudService();
		if(alarmRule.getAlarmCategoryId()!=null) {
			TBLAlarmCategoryEntity alarmCategory = (TBLAlarmCategoryEntity)crudService.get(alarmRule.getAlarmCategoryId(), TBLAlarmCategoryEntity.class, true);
			String content = alarmRule.getRuleName() + "号报警规则" + indicator.getName() + "指标" + alarmCategory.getExceptionDesc() ;
			this.alarmData.setContent(content);
		}
		
		if(point.getLineId()!=null) {
			TblLinesEntity line = (TblLinesEntity)crudService.get(point.getLineId(), TblLinesEntity.class, true);
			if(line !=null) {
				this.alarmData.setLineId(line.getLineId()); 
				this.alarmData.setLineName(line.getLineName());
			}
		}
	}
	
	public AlarmDataVo() {
		super();
	}

	public TblPointAlamDataEntity getAlarmData() {
		return alarmData;
	}

	public void setAlarmData(TblPointAlamDataEntity alarmData) {
		this.alarmData = alarmData;
	}

	public TblAlarmDownRecordEntity getDownRecord() {
		return downRecord;
	}

	public void setDownRecord(TblAlarmDownRecordEntity downRecord) {
		this.downRecord = downRecord;
	}
	
	
	
}
