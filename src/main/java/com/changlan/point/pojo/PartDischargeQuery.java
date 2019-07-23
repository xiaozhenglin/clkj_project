package com.changlan.point.pojo;

import com.changlan.common.entity.TblTemperatureDataEntity;
import com.changlan.other.entity.DeviceDataColl;


public class PartDischargeQuery extends DeviceDataColl{
	private Integer lineId;
	private Long begin;
	private Long end;
	private String dataType;
	private String indicatorIds;

	public String getIndicatorIds() {
		return indicatorIds;
	}

	public void setIndicatorIds(String indicatorIds) {
		this.indicatorIds = indicatorIds;
	}

	public Integer getLineId() {
		return lineId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	public Long getBegin() {
		return begin;
	}

	public void setBegin(Long begin) {
		this.begin = begin;
	}

	public Long getEnd() {
		return end;
	}

	public void setEnd(Long end) {
		this.end = end;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

}
