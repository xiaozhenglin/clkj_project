package com.changlan.point.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.entity.TblTemperatureDataEntity;
import com.changlan.common.util.StringUtil;
import com.changlan.indicator.pojo.IndiCatorValueDetail;
import com.changlan.other.entity.DeviceDataColl;
import com.changlan.point.pojo.PartDischargeDataDetail;
import com.changlan.point.pojo.PointDataDetail;
import com.changlan.point.pojo.TemperatureDataDetail;

public class CommonDataTableVO {
    private String pointName;
    private String indicators;
	private Integer indicatorId; //指标id
	private String indicatorCode; //指标吗
	private String indicatorName; //指标名称
	private String visualType; 
	private String picture_url;   //静态页面路径 
	private String video_url;
	private String record_id;
	
	private String unit; //指标单位
	private List<IndicatorValueVO> results =  new ArrayList<IndicatorValueVO>();

	public CommonDataTableVO CommonPoinDataTableVO(Integer indicatorId,List<PointDataDetail> dataDetails) {
		CommonDataTableVO vo = new CommonDataTableVO();
		List<IndicatorValueVO> results =  new ArrayList<IndicatorValueVO>();
		//this.indicatorId = indicatorId;
		vo.setIndicatorId(indicatorId);
		for(PointDataDetail dataDetail : dataDetails ) {
			IndiCatorValueDetail valueDetail = dataDetail.getValueDetail(); 
			TblPoinDataEntity pointData = dataDetail.getPointData(); 
			TblPointsEntity point = dataDetail.getPoint(); 
			TblIndicatorValueEntity indicatorValue = valueDetail.getIndicatorValue(); 
			
			vo.setIndicatorId(indicatorValue.getIndicatorId()); 
			vo.setVisualType(indicatorValue.getVisualType()); 
			vo.setIndicatorCode(indicatorValue.getIndicatorCode());
		
			vo.setIndicatorName(indicatorValue.getName());
			
			vo.setUnit(indicatorValue.getUnit());
		    
			if(StringUtil.isNotEmpty(point.getPointName())) {
				vo.setPointName(point.getPointName());
			}
			if(StringUtil.isNotEmpty(point.getIndicators())) {
				vo.setIndicators(point.getIndicators());
			}
						
			if(StringUtil.isNotEmpty(point.getPicture_url())) {
				vo.setPicture_url(point.getPicture_url());
			}
			if(StringUtil.isNotEmpty(point.getVideo_url())) {
				vo.setVideo_url(point.getVideo_url());
		    }
			
			IndicatorValueVO value = new IndicatorValueVO(pointData.getValue(), pointData.getRecordTime());
			results.add(value);
		}
		vo.setResults(results);
		return vo;
	}
	
	public CommonDataTableVO CommonPoinDataTableVOSinger(Integer indicatorId,List<PointDataDetail> dataDetails) {
		CommonDataTableVO vo = new CommonDataTableVO();
		List<IndicatorValueVO> results =  new ArrayList<IndicatorValueVO>();
		//this.indicatorId = indicatorId;
		vo.setIndicatorId(indicatorId);
		PointDataDetail dataDetail = dataDetails.get(0) ;  //得到第一条数据 
			IndiCatorValueDetail valueDetail = dataDetail.getValueDetail(); 
			TblPoinDataEntity pointData = dataDetail.getPointData(); 
			TblPointsEntity point = dataDetail.getPoint(); 
			TblIndicatorValueEntity indicatorValue = valueDetail.getIndicatorValue(); 
			vo.setIndicatorCode(indicatorValue.getIndicatorCode());
			vo.setIndicatorId(indicatorValue.getIndicatorId()); 
			vo.setVisualType(indicatorValue.getVisualType()); 
			vo.setIndicatorName(indicatorValue.getName());
			
			vo.setUnit(indicatorValue.getUnit());
			if(StringUtil.isNotEmpty(point.getPointName())) {
				vo.setPointName(point.getPointName());
			}
			if(StringUtil.isNotEmpty(point.getIndicators())) {
				vo.setIndicators(point.getIndicators());
			}
			
			if(StringUtil.isNotEmpty(point.getPicture_url())) {
				vo.setPicture_url(point.getPicture_url());
			}
			if(StringUtil.isNotEmpty(point.getVideo_url())) {
				vo.setVideo_url(point.getVideo_url());
		    }
			
			IndicatorValueVO value = new IndicatorValueVO(pointData.getValue(), pointData.getRecordTime());
			results.add(value);
		
		vo.setResults(results);
		return vo;
	}
	
	public CommonDataTableVO  CommonTemperatureDataTableVOSinger(Integer indicatorId,List<TemperatureDataDetail> dataDetails) {
		CommonDataTableVO vo = new CommonDataTableVO();
		List<IndicatorValueVO> results =  new ArrayList<IndicatorValueVO>();
		//this.indicatorId = indicatorId;
		vo.setIndicatorId(indicatorId);
		TemperatureDataDetail dataDetail = dataDetails.get(0);
			IndiCatorValueDetail valueDetail = dataDetail.getValueDetail(); 
			TblTemperatureDataEntity pointData = dataDetail.getTemperatureData(); 
			TblPointsEntity point = dataDetail.getPoint(); 
			TblIndicatorValueEntity indicatorValue = valueDetail.getIndicatorValue(); 
			vo.setIndicatorCode(indicatorValue.getIndicatorCode());
			vo.setIndicatorId(indicatorValue.getIndicatorId()); 
			vo.setVisualType(indicatorValue.getVisualType()); 
			vo.setIndicatorName(indicatorValue.getName());
		
			vo.setUnit(indicatorValue.getUnit());

			if(StringUtil.isNotEmpty(point.getPointName())) {
				vo.setPointName(point.getPointName());
			}
			if(StringUtil.isNotEmpty(point.getIndicators())) {
				vo.setIndicators(point.getIndicators());
			}
			if(StringUtil.isNotEmpty(point.getPicture_url())) {
				vo.setPicture_url(point.getPicture_url());
			}
			if(StringUtil.isNotEmpty(point.getVideo_url())) {
				vo.setVideo_url(point.getVideo_url());
		    }
			
			IndicatorValueVO value = new IndicatorValueVO(pointData.getValue(), pointData.getRecordTime());
			results.add(value);
		
		vo.setResults(results);
		return vo;
		
	}
	
	public CommonDataTableVO  CommonTemperatureDataTableVO(Integer indicatorId,List<TemperatureDataDetail> dataDetails) {
		CommonDataTableVO vo = new CommonDataTableVO();
		List<IndicatorValueVO> results =  new ArrayList<IndicatorValueVO>();
		//this.indicatorId = indicatorId;
		vo.setIndicatorId(indicatorId);
		for(TemperatureDataDetail dataDetail : dataDetails ) {
			IndiCatorValueDetail valueDetail = dataDetail.getValueDetail(); 
			TblTemperatureDataEntity pointData = dataDetail.getTemperatureData(); 
			TblPointsEntity point = dataDetail.getPoint(); 
			TblIndicatorValueEntity indicatorValue = valueDetail.getIndicatorValue(); 
			vo.setIndicatorCode(indicatorValue.getIndicatorCode());
			vo.setIndicatorId(indicatorValue.getIndicatorId()); 
			vo.setVisualType(indicatorValue.getVisualType()); 
			vo.setIndicatorName(indicatorValue.getName());
		
			vo.setUnit(indicatorValue.getUnit());
			if(StringUtil.isNotEmpty(point.getPointName())) {
				vo.setPointName(point.getPointName());
			}
			if(StringUtil.isNotEmpty(point.getIndicators())) {
				vo.setIndicators(point.getIndicators());
			}
			if(StringUtil.isNotEmpty(point.getPicture_url())) {
				vo.setPicture_url(point.getPicture_url());
			}
			if(StringUtil.isNotEmpty(point.getVideo_url())) {
				vo.setVideo_url(point.getVideo_url());
		    }
			
			IndicatorValueVO value = new IndicatorValueVO(pointData.getValue(), pointData.getRecordTime());
			results.add(value);
		}
		vo.setResults(results);
		return vo;
		
	}
	
	public CommonDataTableVO  CommonPartDischargeDataTableVO(Integer indicatorId,List<PartDischargeDataDetail> dataDetails) {
		CommonDataTableVO vo = new CommonDataTableVO();
		List<IndicatorValueVO> results =  new ArrayList<IndicatorValueVO>();
		vo.setIndicatorId(indicatorId);
		for(PartDischargeDataDetail dataDetail : dataDetails ) {
			IndiCatorValueDetail valueDetail = dataDetail.getValueDetail(); 
			DeviceDataColl pointData = dataDetail.getDeviceDataCollData(); 
			TblPointsEntity point = dataDetail.getPoint(); 
			TblIndicatorValueEntity indicatorValue = valueDetail.getIndicatorValue(); 
			vo.setIndicatorId(indicatorValue.getIndicatorId()); 
			vo.setVisualType(indicatorValue.getVisualType()); 
			vo.setIndicatorCode(indicatorValue.getIndicatorCode());
	
			vo.setIndicatorName(indicatorValue.getName());
			
			if(StringUtil.isNotEmpty(pointData.getRecord_id())) {
				vo.setRecord_id(pointData.getRecord_id());
			}
			vo.setUnit(indicatorValue.getUnit());
			if(StringUtil.isNotEmpty(point.getPointName())) {
				vo.setPointName(point.getPointName());
			}
			if(StringUtil.isNotEmpty(point.getIndicators())) {
				vo.setIndicators(point.getIndicators());
			}
			if(StringUtil.isNotEmpty(point.getPicture_url())) {
				vo.setPicture_url(point.getPicture_url());
			}
			if(StringUtil.isNotEmpty(point.getVideo_url())) {
				vo.setVideo_url(point.getVideo_url());
		    }
			IndicatorValueVO value = new IndicatorValueVO(pointData.getValue(), pointData.getRecordTime());
			results.add(value);
		}
		vo.setResults(results);
		return vo;
	}
	
	public CommonDataTableVO  CommonPartDischargeDataTableVOSinger(Integer indicatorId,List<PartDischargeDataDetail> dataDetails) {
		CommonDataTableVO vo = new CommonDataTableVO();
		List<IndicatorValueVO> results =  new ArrayList<IndicatorValueVO>();
		vo.setIndicatorId(indicatorId);
		PartDischargeDataDetail dataDetail = dataDetails.get(0);
			IndiCatorValueDetail valueDetail = dataDetail.getValueDetail(); 
			DeviceDataColl pointData = dataDetail.getDeviceDataCollData(); 
			TblPointsEntity point = dataDetail.getPoint(); 
			TblIndicatorValueEntity indicatorValue = valueDetail.getIndicatorValue(); 
			vo.setIndicatorId(indicatorValue.getIndicatorId()); 
			vo.setVisualType(indicatorValue.getVisualType()); 
			vo.setIndicatorCode(indicatorValue.getIndicatorCode());
	
			vo.setIndicatorName(indicatorValue.getName());
			
			if(StringUtil.isNotEmpty(pointData.getRecord_id())) {
				vo.setRecord_id(pointData.getRecord_id());
			}
			vo.setUnit(indicatorValue.getUnit());
			if(StringUtil.isNotEmpty(point.getPointName())) {
				vo.setPointName(point.getPointName());
			}
			if(StringUtil.isNotEmpty(point.getIndicators())) {
				vo.setIndicators(point.getIndicators());
			}
			if(StringUtil.isNotEmpty(point.getPicture_url())) {
				vo.setPicture_url(point.getPicture_url());
			}
			if(StringUtil.isNotEmpty(point.getVideo_url())) {
				vo.setVideo_url(point.getVideo_url());
		    }
			IndicatorValueVO value = new IndicatorValueVO(pointData.getValue(), pointData.getRecordTime());
			results.add(value);
		
		vo.setResults(results);
		return vo;
	}


	public CommonDataTableVO() {
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

	public String getPicture_url() {
		return picture_url;
	}

	public void setPicture_url(String picture_url) {
		this.picture_url = picture_url;
	}

	public String getVideo_url() {
		return video_url;
	}

	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}

	public String getRecord_id() {
		return record_id;
	}

	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}

	public String getVisualType() {
		return visualType;
	}

	public void setVisualType(String visualType) {
		this.visualType = visualType;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public String getIndicators() {
		return indicators;
	}

	public void setIndicators(String indicators) {
		this.indicators = indicators;
	}
	
	
}
