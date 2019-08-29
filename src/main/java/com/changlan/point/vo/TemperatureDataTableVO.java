package com.changlan.point.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblTemperatureDataEntity;
import com.changlan.indicator.pojo.IndiCatorValueDetail;
import com.changlan.indicator.pojo.VisualType;
import com.changlan.point.pojo.PointDataDetail;
import com.changlan.point.pojo.TemperatureDataDetail;
import com.changlan.point.vo.IndicatorValueVO;


public class TemperatureDataTableVO {

	private Integer indicatorId; //指标id
	private String indicatorCode; //指标吗
	private String indicatorName; //指标名称
	private String visualType = VisualType.NUMBER.toString();
	private List<IndicatorValueVO> results =  new ArrayList<IndicatorValueVO>();

	public TemperatureDataTableVO(Integer indicatorId,List<TemperatureDataDetail> dataDetails) {
		this.indicatorId = indicatorId;
		for(TemperatureDataDetail dataDetail : dataDetails ) {
			IndiCatorValueDetail valueDetail = dataDetail.getValueDetail(); 
			TblTemperatureDataEntity pointData = dataDetail.getTemperatureData(); 
			TblIndicatorValueEntity indicatorValue = valueDetail.getIndicatorValue(); 
			this.indicatorCode = indicatorValue.getIndicatorCode();
			this.indicatorName = indicatorValue.getName();
			IndicatorValueVO vo = new IndicatorValueVO(pointData.getValue(), pointData.getRecordTime(),pointData.getIndicatorId());
			results.add(vo);
		}
	}

	public TemperatureDataTableVO() {
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

	public String getVisualType() {
		return visualType;
	}

	public void setVisualType(String visualType) {
		this.visualType = visualType;
	}


	
}
