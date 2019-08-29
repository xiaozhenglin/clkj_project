package com.changlan.point.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.indicator.pojo.IndiCatorValueDetail;
import com.changlan.indicator.pojo.VisualType;
import com.changlan.point.pojo.PointDataDetail;

public class PoinDataTableVO {

	private Integer indicatorId; //指标id
	private String indicatorCode; //指标吗
	private String indicatorName; //指标名称
	private String unit; //指标单位
	private String visualType ;

	private List<IndicatorValueVO> results =  new ArrayList<IndicatorValueVO>();

	public PoinDataTableVO(Integer indicatorId,List<PointDataDetail> dataDetails) {
		this.indicatorId = indicatorId;
		for(PointDataDetail dataDetail : dataDetails ) {
			IndiCatorValueDetail valueDetail = dataDetail.getValueDetail(); 
			TblPoinDataEntity pointData = dataDetail.getPointData(); 
			TblIndicatorValueEntity indicatorValue = valueDetail.getIndicatorValue(); 
			this.indicatorCode = indicatorValue.getIndicatorCode();
			this.indicatorName = indicatorValue.getName();
			this.unit = indicatorValue.getUnit();
			this.visualType = indicatorValue.getVisualType();
			IndicatorValueVO vo = new IndicatorValueVO(pointData.getValue(), pointData.getRecordTime(),pointData.getIndicatorId());
			results.add(vo);
		}
	}

	public PoinDataTableVO() {
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
