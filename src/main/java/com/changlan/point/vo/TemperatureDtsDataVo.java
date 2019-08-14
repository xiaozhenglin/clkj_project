package com.changlan.point.vo;

import java.io.Serializable;
import java.util.Date;

import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.entity.TblTemperatureDTSDataEntity;
import com.changlan.common.entity.TblTemperatureDataEntity;
import com.changlan.indicator.pojo.IndiCatorValueDetail;
import com.changlan.point.pojo.TemperatureDataDetail;
import com.changlan.point.pojo.TemperatureDtsDataDetail;


public class TemperatureDtsDataVo implements Serializable{

	private Integer pointId;
	private String pointName;
	private Integer indicatorId;
	private String indicatorName;
	private Integer pointDataId;
	private Integer refPointDataId;
	private String indicatorValue;
	private Integer indicatorCategoryId;
	private String indicatorCategoryName;
	private Date recordTime;

	public TemperatureDtsDataVo(TemperatureDtsDataDetail detail) {
		TblPointsEntity point = detail.getPoint();
		TblTemperatureDTSDataEntity  temperatureData = detail.getTemperatureData();
		IndiCatorValueDetail valueDetail = detail.getValueDetail();  
		this.pointId=point.getPointId();
		this.pointName = point.getPointName();
		this.indicatorId = valueDetail.getIndicatorValue().getIndicatorId();
		this.indicatorName = valueDetail.getIndicatorValue().getName();
		this.pointDataId = temperatureData.getPointDataId();
		this.refPointDataId = temperatureData.getRefPointDataId();
		this.indicatorValue = temperatureData.getValue();
		this.indicatorCategoryId = valueDetail.getCategory().getCategoryId();
		this.indicatorCategoryName = valueDetail.getCategory().getName();
		this.recordTime =  temperatureData.getRecordTime();
	}

	public TemperatureDtsDataVo() {
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

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public Integer getRefPointDataId() {
		return refPointDataId;
	}

	public void setRefPointDataId(Integer refPointDataId) {
		this.refPointDataId = refPointDataId;
	}
				
}
