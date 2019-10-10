package com.changlan.point.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.entity.TblTemperatureDTSDataEntity;
import com.changlan.common.entity.TblTemperatureDataEntity;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.indicator.pojo.IndiCatorValueDetail;
import com.changlan.other.entity.DeviceDataColl;
import com.changlan.point.pojo.PartDischargeDataDetail;
import com.changlan.point.pojo.PointDataDetail;
import com.changlan.point.pojo.TemperatureDataDetail;
import com.changlan.point.pojo.TemperatureDtsDataDetail;

public class CommonDataTableVO {
    private String pointName;
    private String indicators;
	private Integer indicatorId; //指标id
	private String indicatorCode; //指标吗
	private String indicatorName; //指标名称
	private String visualType; 
	//private String picture_url;   //静态页面路径 
	//private String video_url;
	//private Integer record_id;
	
	private String unit; //指标单位
	private List<IndicatorValueVO> results =  new ArrayList<IndicatorValueVO>();

	public CommonDataTableVO CommonPoinDataTableVO(Integer indicatorId,List<PointDataDetail> dataDetails) {
		CommonDataTableVO vo = new CommonDataTableVO();
		List<IndicatorValueVO> results =  new ArrayList<IndicatorValueVO>();
		//this.indicatorId = indicatorId;
		if(!ListUtil.isEmpty(dataDetails)) {
			for(PointDataDetail dataDetail : dataDetails ) {
				vo.setIndicatorId(indicatorId);
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
							
				
				
				IndicatorValueVO value = new IndicatorValueVO(pointData.getValue(), pointData.getRecordTime() ,indicatorValue.getIndicatorId() );
				results.add(value);
				vo.setResults(results);
				
			}
		}else {
			vo = null;
		}
		return vo;
	}
	
	public CommonDataTableVO CommonPoinDataTableVOSinger(Integer indicatorId,List<PointDataDetail> dataDetails) {
		if(!ListUtil.isEmpty(dataDetails)) {
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
			
			
			IndicatorValueVO value = new IndicatorValueVO(pointData.getValue(), pointData.getRecordTime(),indicatorValue.getIndicatorId());
			results.add(value);
		
			vo.setResults(results);
			return vo;
		}
		return null;
	}
	
	public CommonDataTableVO  CommonTemperatureDataTableVOSinger(Integer indicatorId,List<TemperatureDataDetail> dataDetails) {
		if(!ListUtil.isEmpty(dataDetails)) {
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
			
			
			IndicatorValueVO value = new IndicatorValueVO(pointData.getValue(), pointData.getRecordTime(),indicatorValue.getIndicatorId());
			results.add(value);
		
		vo.setResults(results);
		return vo;
		}
		return null;
		
	}
	
	public CommonDataTableVO  CommonTemperatureDSTDataTableVOSinger(Integer indicatorId,List<TemperatureDtsDataDetail> dataDetails) {
		if(!ListUtil.isEmpty(dataDetails)) {
			CommonDataTableVO vo = new CommonDataTableVO();
			List<IndicatorValueVO> results =  new ArrayList<IndicatorValueVO>();
			//this.indicatorId = indicatorId;
			vo.setIndicatorId(indicatorId);
	
			TemperatureDtsDataDetail dataDetail = dataDetails.get(0);
			IndiCatorValueDetail valueDetail = dataDetail.getValueDetail(); 
			TblTemperatureDTSDataEntity pointData = dataDetail.getTemperatureData(); 
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
			
			
			IndicatorValueVO value = new IndicatorValueVO(pointData.getValue(), pointData.getRecordTime(),indicatorValue.getIndicatorId());
			results.add(value);
		
			vo.setResults(results);
			return vo;
		}
		return null;
		
	}
	
	public CommonDataTableVO  CommonTemperatureDataTableVO(Integer indicatorId,List<TemperatureDataDetail> dataDetails) {
		CommonDataTableVO vo = new CommonDataTableVO();
		List<IndicatorValueVO> results =  new ArrayList<IndicatorValueVO>();
		//this.indicatorId = indicatorId;
		if(!ListUtil.isEmpty(dataDetails)) {
			for(TemperatureDataDetail dataDetail : dataDetails ) {
				vo.setIndicatorId(indicatorId);
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
				
				
				if(indicatorValue.getName().indexOf("DTS")>-1) {//分布式光纤测温
					IndicatorValueVO value = new IndicatorValueVO(pointData.getValue(), pointData.getRecordTime(),pointData.getPointDataId());
					results.add(value);
				}else {
					IndicatorValueVO value = new IndicatorValueVO(pointData.getValue(), pointData.getRecordTime(),indicatorValue.getIndicatorId());
					results.add(value);
				}
				vo.setResults(results);
				//return vo;
			}
		}else {
			vo = null;
		}
		return vo;
		
	}
	
	public CommonDataTableVO  CommonTemperatureDSTDataTableVO(Integer indicatorId,List<TemperatureDtsDataDetail> dataDetails) {
		CommonDataTableVO vo = new CommonDataTableVO();
		List<IndicatorValueVO> results =  new ArrayList<IndicatorValueVO>();
		//this.indicatorId = indicatorId;
		vo.setIndicatorId(indicatorId);
		for(TemperatureDtsDataDetail dataDetail : dataDetails ) {
			IndiCatorValueDetail valueDetail = dataDetail.getValueDetail(); 
			TblTemperatureDTSDataEntity pointData = dataDetail.getTemperatureData(); 
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
			
			
			IndicatorValueVO value = new IndicatorValueVO(pointData.getValue(), pointData.getRecordTime(),indicatorValue.getIndicatorId());
			results.add(value);
		}
		vo.setResults(results);
		return vo;
		
	}
	
	
	public CommonDataTableVO  CommonPartDischargeDataTableVO(Integer indicatorId,List<PartDischargeDataDetail> dataDetails) {
		CommonDataTableVO vo = new CommonDataTableVO();
		List<IndicatorValueVO> results =  new ArrayList<IndicatorValueVO>();
		if(!ListUtil.isEmpty(dataDetails)) {
			for(PartDischargeDataDetail dataDetail : dataDetails ) {
				vo.setIndicatorId(indicatorId);
				IndiCatorValueDetail valueDetail = dataDetail.getValueDetail(); 
				DeviceDataColl pointData = dataDetail.getDeviceDataCollData(); 
				TblPointsEntity point = dataDetail.getPoint(); 
				TblIndicatorValueEntity indicatorValue = valueDetail.getIndicatorValue(); 
				vo.setIndicatorId(indicatorValue.getIndicatorId()); 
				vo.setVisualType(indicatorValue.getVisualType()); 
				vo.setIndicatorCode(indicatorValue.getIndicatorCode());
		
				vo.setIndicatorName(indicatorValue.getName());
				
				//if(pointData.getRecord_id()!=null) {
					//vo.setRecord_id(pointData.getRecord_id());
				//}
				vo.setUnit(indicatorValue.getUnit());
				if(StringUtil.isNotEmpty(point.getPointName())) {
					vo.setPointName(point.getPointName());
				}
				if(StringUtil.isNotEmpty(point.getIndicators())) {
					vo.setIndicators(point.getIndicators());
				}
				
				IndicatorValueVO value = new IndicatorValueVO(pointData.getValue(), pointData.getRecordTime(),pointData.getRecord_id());
				results.add(value);
				vo.setResults(results);
				
			}
		}else {
			vo = null;
		}
		return vo;
	}
	
	public CommonDataTableVO  CommonPartDischargeDataTableVOSinger(Integer indicatorId,List<PartDischargeDataDetail> dataDetails) {
		if(!ListUtil.isEmpty(dataDetails)) {
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
			
			//if(pointData.getRecord_id()!=null) {
				//vo.setRecord_id(pointData.getRecord_id());
			//}
			vo.setUnit(indicatorValue.getUnit());
			if(StringUtil.isNotEmpty(point.getPointName())) {
				vo.setPointName(point.getPointName());
			}
			if(StringUtil.isNotEmpty(point.getIndicators())) {
				vo.setIndicators(point.getIndicators());
			}
			
			IndicatorValueVO value = new IndicatorValueVO(pointData.getValue(), pointData.getRecordTime(),pointData.getRecord_id());
			results.add(value);
		
			vo.setResults(results);
			return vo;
		}
		return null;
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

	

	

	//public Integer getRecord_id() {
		//return record_id;
	//}

	//public void setRecord_id(Integer record_id) {
		//this.record_id = record_id;
	//}

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
