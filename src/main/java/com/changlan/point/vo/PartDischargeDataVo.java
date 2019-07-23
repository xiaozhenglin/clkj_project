package com.changlan.point.vo;

import java.io.Serializable;
import java.util.Date;

import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.indicator.pojo.IndiCatorValueDetail;
import com.changlan.other.entity.DeviceDataColl;
import com.changlan.point.pojo.PartDischargeDataDetail;
import com.changlan.point.pojo.PointDataDetail;

public class PartDischargeDataVo implements Serializable{

	private Integer pointDataId;
	private Integer pointId;
	private String pointName;
	private Integer indicatorId;
	private String indicatorName;
	private String  unit; //指标单位
	private String indicatorValue;
	private Integer indicatorCategoryId;
	private String indicatorCategoryName;
	private Date recordTime;

	public PartDischargeDataVo(PartDischargeDataDetail detail) {
		TblPointsEntity point = detail.getPoint();
		DeviceDataColl pointData = detail.getDeviceDataCollData();
		IndiCatorValueDetail valueDetail = detail.getValueDetail();  
		this.pointId=point.getPointId();
		this.pointName = point.getPointName();
		this.indicatorId = valueDetail.getIndicatorValue().getIndicatorId();
		this.indicatorName = valueDetail.getIndicatorValue().getName();
		this.pointDataId = pointData.getPointDataId();
		this.indicatorValue = pointData.getValue();
		this.unit = valueDetail.getIndicatorValue().getUnit();
		this.indicatorCategoryId = valueDetail.getCategory().getCategoryId();
		this.indicatorCategoryName = valueDetail.getCategory().getName();
		this.recordTime =  pointData.getRecordTime();
	}

	public PartDischargeDataVo() {
		super();
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

	public Integer getPointDataId() {
		return pointDataId;
	}

	public void setPointDataId(Integer pointDataId) {
		this.pointDataId = pointDataId;
	}

	public String getIndicatorValue() {
		return indicatorValue;
	}

	public void setIndicatorValue(String indicatorValue) {
		this.indicatorValue = indicatorValue;
	}

	public Integer getIndicatorCategoryId() {
		return indicatorCategoryId;
	}

	public void setIndicatorCategoryId(Integer indicatorCategoryId) {
		this.indicatorCategoryId = indicatorCategoryId;
	}

	public String getIndicatorCategoryName() {
		return indicatorCategoryName;
	}

	public void setIndicatorCategoryName(String indicatorCategoryName) {
		this.indicatorCategoryName = indicatorCategoryName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	
	
}
