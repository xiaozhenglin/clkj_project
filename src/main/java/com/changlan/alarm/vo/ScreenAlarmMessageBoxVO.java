package com.changlan.alarm.vo;

import java.util.Date;

import com.changlan.alarm.pojo.TblAlarmDataDetail;
import com.changlan.common.entity.TblAlarmRuleEntity;
import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblPointAlamDataEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.indicator.pojo.IndiCatorValueDetail;
import com.changlan.indicator.pojo.VisualType;
import com.changlan.point.pojo.PointDataDetail;

public class ScreenAlarmMessageBoxVO {
	
	private Integer alarmDataId;
	private Integer pointId;
	private String pointName; 
	private Integer indicatorId;
	private String indicatorName;
	private String indicatorValue;
	private String visualType;
	private String  unit; //指标单位
	private Date recordTime; //报警时间
	private String content ; //报警内容

	public ScreenAlarmMessageBoxVO(TblAlarmDataDetail detail) {
		TblPointsEntity point = detail.getPoint(); 
		TblPointAlamDataEntity alarmData = detail.getAlarmData(); 
		TblAlarmRuleEntity alarmRule = detail.getAlarmRule(); 
		TblIndicatorValueEntity indicator = detail.getIndicator(); 
		
		if(point!=null) {
			this.pointId=point.getPointId();
			this.pointName = point.getPointName();
		}
	
		if(indicator!=null) {
			this.indicatorId = indicator.getIndicatorId();
			this.indicatorName = indicator.getName();
			this.unit = indicator.getUnit();
			if(StringUtil.isNotEmpty(indicator.getVisualType())) {
				this.visualType = indicator.getVisualType();
//				this.visualType = VisualType.valueOf(indicator.getVisualType()).getName();
			}
		}
	
		
		if(alarmData!=null) {
			this.alarmDataId = alarmData.getAlarmId();
			this.indicatorValue = alarmData.getValue();
			this.recordTime =  alarmData.getAlarmDate();
			this.content = alarmData.getContent();
		}
	
		
		
//		ICrudService crudService = SpringUtil.getICrudService();
//		if(point.getLineId()!=null) {
//			TblLinesEntity line = (TblLinesEntity)crudService.get(point.getLineId(), TblLinesEntity.class, true);
//			if(line !=null) {
//				this.setLineId(line.getLineId()); 
//				this.setLineName(line.getLineName());
//			}
//		}
	}

	public Integer getAlarmDataId() {
		return alarmDataId;
	}

	public void setAlarmDataId(Integer alarmDataId) {
		this.alarmDataId = alarmDataId;
	}

	public Integer getPointId() {
		return pointId;
	}

	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public Integer getIndicatorId() {
		return indicatorId;
	}

	public void setIndicatorId(Integer indicatorId) {
		this.indicatorId = indicatorId;
	}

	public String getIndicatorName() {
		return indicatorName;
	}

	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getIndicatorValue() {
		return indicatorValue;
	}

	public void setIndicatorValue(String indicatorValue) {
		this.indicatorValue = indicatorValue;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getVisualType() {
		return visualType;
	}

	public void setVisualType(String visualType) {
		this.visualType = visualType;
	}
	
	
}
