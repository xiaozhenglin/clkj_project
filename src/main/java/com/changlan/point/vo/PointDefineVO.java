package com.changlan.point.vo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.changlan.common.entity.TblIndicatorCategoriesEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblPointCategoryEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.point.pojo.PointInfoDetail;

public class PointDefineVO {
  
	private Integer pointId;
	private String pointName; 
	private String status;   //设备状态
    private String pointAddress;
    private String longLati;
    private String phones;   //负责人电话
    private String personName; //负责人姓名
    private String personCompanyName; // 负责人公司名称
    
    private String pointRegistPackage;
//    private String portName;
//    private Integer portBound;
    private String ip; // ip地址
    private String smsNumber; //短信猫sms卡号
    private Integer lineOrder; //在线路中的次序
    private Integer isCorner=0;//是否是拐角
    
    private Integer pointCatagoryId;//监控点类别
    private String  pointCatagoryName; //监控类别名称
    
    private List<TblIndicatorCategoriesEntity>  indicatorCategorys =  new ArrayList();//指标类别
	private String indicators; //指标类别id  多个以逗号分隔
	
//	private Integer indicatorCategoryId; //指标类别
//	private String  indicatorCategoryName;//指标类别名称
	
	private String  lineName; //线路名称
	private Integer lineId;	//线路id
	private String  lineAddress; //线路地址


	public PointDefineVO() {
		super();
	}


	public PointDefineVO(PointInfoDetail defineDetail) {
		TblPointsEntity point = defineDetail.getPoint(); 
		if(point!=null) {
			this.pointId = point.getPointId();
			this.pointName = point.getPointName();
			this.status = point.getStatus();
			this.pointAddress = point.getPointAddress();
			this.longLati = point.getLongLati();
			this.phones = point.getPhones();
			this.pointRegistPackage = point.getPointRegistPackage();
			
			this.smsNumber = point.getSmsNumber(); //短信猫sms卡号
			this.lineOrder = point.getLineOrder(); //在线路中的次序
			this.isCorner =  point.getIsCorner();
			this.ip = point.getIp();
			this.indicators = point.getIndicators(); 
		}
		
		TblPointCategoryEntity category = defineDetail.getCategory();
		if(category!=null) {
			this.pointCatagoryId = category.getPointCatgoryId();
			this.pointCatagoryName = category.getPontCatagoryName() ;
		}
		
		this.indicatorCategorys = defineDetail.getIndicatorCategory();	
		
		TblLinesEntity line = defineDetail.getLine(); 
		if(line!=null) {
			this.lineId = line.getLineId(); 
			this.lineName = line.getLineName();
			this.lineAddress = line.getCenterAddress();
		}
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



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getPointAddress() {
		return pointAddress;
	}



	public void setPointAddress(String pointAddress) {
		this.pointAddress = pointAddress;
	}



	public String getLongLati() {
		return longLati;
	}



	public void setLongLati(String longLati) {
		this.longLati = longLati;
	}



	public String getPhones() {
		return phones;
	}



	public void setPhones(String phones) {
		this.phones = phones;
	}



	public String getPointRegistPackage() {
		return pointRegistPackage;
	}



	public void setPointRegistPackage(String pointRegistPackage) {
		this.pointRegistPackage = pointRegistPackage;
	}



	public Integer getPointCatagoryId() {
		return pointCatagoryId;
	}



	public void setPointCatagoryId(Integer pointCatagoryId) {
		this.pointCatagoryId = pointCatagoryId;
	}



	public String getPointCatagoryName() {
		return pointCatagoryName;
	}



	public void setPointCatagoryName(String pointCatagoryName) {
		this.pointCatagoryName = pointCatagoryName;
	}


//	public List<TblIndicatorCategoriesEntity> getIndicatorCategorys() {
//		return indicatorCategorys;
//	}
//
//
//	public void setIndicatorCategorys(List<TblIndicatorCategoriesEntity> indicatorCategorys) {
//		this.indicatorCategorys = indicatorCategorys;
//	}


//	public String getIndicators() {
//		return indicators;
//	}
//
//
//	public void setIndicators(String indicators) {
//		this.indicators = indicators;
//	}


	public String getLineName() {
		return lineName;
	}


	public void setLineName(String lineName) {
		this.lineName = lineName;
	}


	public Integer getLineId() {
		return lineId;
	}


	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}


	public String getSmsNumber() {
		return smsNumber;
	}


	public void setSmsNumber(String smsNumber) {
		this.smsNumber = smsNumber;
	}


	public Integer getLineOrder() {
		return lineOrder;
	}


	public void setLineOrder(Integer lineOrder) {
		this.lineOrder = lineOrder;
	}


	public Integer getIsCorner() {
		return isCorner;
	}


	public void setIsCorner(Integer isCorner) {
		this.isCorner = isCorner;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public String getLineAddress() {
		return lineAddress;
	}


	public void setLineAddress(String lineAddress) {
		this.lineAddress = lineAddress;
	}


	public List<TblIndicatorCategoriesEntity> getIndicatorCategorys() {
		return indicatorCategorys;
	}


	public void setIndicatorCategorys(List<TblIndicatorCategoriesEntity> indicatorCategorys) {
		this.indicatorCategorys = indicatorCategorys;
	}


	public String getIndicators() {
		return indicators;
	}


	public void setIndicators(String indicators) {
		this.indicators = indicators;
	}



	


	
	
}
