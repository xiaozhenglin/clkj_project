/*
 * Welcome to use the TableGo Tools.
 * 
 * http://vipbooks.iteye.com
 * http://blog.csdn.net/vipbooks
 * http://www.cnblogs.com/vipbooks
 * 
 * Author:bianj
 * Email:edinsker@163.com
 * Version:5.8.0
 */
package com.changlan.common.entity;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.changlan.point.pojo.PointStatus;

/**
 * TBL_POINTS
 * 
 * @author bianj
 * @version 1.0.0 2019-02-23
 */
@Entity
@Table(name = "TBL_POINTS")
public class TblPointsEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 7130919469668817236L;

    /** pointId */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "POINT_ID", unique = true )
    private Integer pointId;

    /** lineId */
    @Column(name = "LINE_ID"    )
    private Integer lineId;

    /** status */
    @Column(name = "STATUS"    )
//    @Enumerated(EnumType.STRING)
    private String status;

    /** image */
    @Column(name = "IMAGE"   )
    private String image; //图标地址

    /** pointAddress */
    @Column(name = "POINT_ADDRESS",unique = true   )
    private String pointAddress;

    /** longLati */
    @Column(name = "LONG_LATI"  )
    private String longLati;

    /** indicators */
    @Column(name = "INDICATORS"   )
    private String indicators;//指标类别多个

    /** pointName */
    @Column(name = "POINT_NAME"   )
    private String pointName;

    /** phones */
    @Column(name = "PHONES"   )
    private String phones; //运维人员号码

    /** pointCatagoryId */
    @Column(name = "POINT_CATAGORY_ID"    )
    private Integer pointCatagoryId;
    
    @Column(name = "POINT_REGIST_PACKAGE"    )
    private String pointRegistPackage;
    
    @Column(name = "PORT_NAME"    )
    private String portName;
    
    @Column(name = "PORT_BOUND"    )
    private Integer portBound;

    @Column(name = "SMS_NUMBER"    )
    private String smsNumber; //短信猫卡号

    @Column(name = "LINE_ORDER"    )
    private Integer lineOrder;
    
    @Column(name = "IS_CORNER"    )
    private Integer isCorner;
    
    @Column(name = "REMOVE_FLAGE"   )
    private Integer removeFlage;
    
    @Column(name = "IP"    )
    private String ip;
    
    @Column(name = "PRINCIPAL"    )
    private String principal; //负责人姓名
    
    @Column(name = "COMPANY"    )
    private String company;  //负责人公司名称
    
    @Column(name = "VIDEO_URL"    )
    private String video_url;
    
    @Column(name = "PICTURE_URL"    )
    private String picture_url;
    
    @Column(name = "POINT_TYPE"    )
    private String pointType;
    
    @Column(name = "IMAGE_IN_MAP"    )
    private String imageInMap;
    
    @Column(name = "SNAP_URL"    )
    private String snap_url;

	public Integer getPointId() {
		return pointId;
	}

	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}
    
	public String getVideo_url() {
		return video_url;
	}

	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}

	public String getPicture_url() {
		return picture_url;
	}

	public void setPicture_url(String picture_url) {
		this.picture_url = picture_url;
	}

	public Integer getLineId() {
		return lineId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public String getIndicators() {
		return indicators;
	}

	public void setIndicators(String indicators) {
		this.indicators = indicators;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	public Integer getPointCatagoryId() {
		return pointCatagoryId;
	}

	public void setPointCatagoryId(Integer pointCatagoryId) {
		this.pointCatagoryId = pointCatagoryId;
	}

	public String getPointRegistPackage() {
		return pointRegistPackage;
	}

	public void setPointRegistPackage(String pointRegistPackage) {
		this.pointRegistPackage = pointRegistPackage;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public Integer getPortBound() {
		return portBound;
	}

	public void setPortBound(Integer portBound) {
		this.portBound = portBound;
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

	public Integer getRemoveFlage() {
		return removeFlage;
	}

	public void setRemoveFlage(Integer removeFlage) {
		this.removeFlage = removeFlage;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPointType() {
		return pointType;
	}

	public void setPointType(String pointType) {
		this.pointType = pointType;
	}

	public String getImageInMap() {
		return imageInMap;
	}

	public void setImageInMap(String imageInMap) {
		this.imageInMap = imageInMap;
	}
    	
	

    
    
}