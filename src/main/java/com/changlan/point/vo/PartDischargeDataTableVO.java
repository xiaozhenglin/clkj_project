package com.changlan.point.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.indicator.pojo.IndiCatorValueDetail;
import com.changlan.indicator.pojo.VisualType;
import com.changlan.other.entity.DeviceDataColl;
import com.changlan.point.pojo.PartDischargeDataDetail;
import com.changlan.point.pojo.PointDataDetail;

public class PartDischargeDataTableVO {

	private Integer indicatorId; //指标id
	private String indicatorCode; //指标吗
	private String indicatorName; //指标名称
	private String unit; //指标单位
	private String visualType = VisualType.NUMBER.toString();
	private List<IndicatorValueVO> results =  new ArrayList<IndicatorValueVO>();

	public PartDischargeDataTableVO(Integer indicatorId,List<PartDischargeDataDetail> dataDetails) {
		this.indicatorId = indicatorId;
		for(PartDischargeDataDetail dataDetail : dataDetails ) {
			IndiCatorValueDetail valueDetail = dataDetail.getValueDetail(); 
			DeviceDataColl pointData = dataDetail.getDeviceDataCollData();
			TblIndicatorValueEntity indicatorValue = valueDetail.getIndicatorValue(); 
			this.indicatorCode = indicatorValue.getIndicatorCode();
			this.indicatorName = indicatorValue.getName();
			this.unit = indicatorValue.getUnit();
			IndicatorValueVO vo = new IndicatorValueVO(pointData.getValue(), pointData.getRecordTime());
			results.add(vo);
		}
	}

	public PartDischargeDataTableVO() {
		super();
	}

	public List<IndicatorValueVO> getResults() {
		return results;
	}

	public void setResults(List<IndicatorValueVO> results) {
		this.results = results;
	}

	public Integer getIndicatorId() {
		return indicatorId;
	}

	public void setIndicatorId(Integer indicatorId) {
		this.indicatorId = indicatorId;
	}

	public String getIndicatorCode() {
		return indicatorCode;
	}

	public void setIndicatorCode(String indicatorCode) {
		this.indicatorCode = indicatorCode;
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

	public String getVisualType() {
		return visualType;
	}

	public void setVisualType(String visualType) {
		this.visualType = visualType;
	}


	
}
