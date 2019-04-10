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
    private String image;

    /** pointAddress */
    @Column(name = "POINT_ADDRESS",unique = true   )
    private String pointAddress;

    /** longLati */
    @Column(name = "LONG_LATI"  )
    private String longLati;

    /** indicators */
    @Column(name = "INDICATORS"   )
    private String indicators;

    /** pointName */
    @Column(name = "POINT_NAME"   )
    private String pointName;

    /** phones */
    @Column(name = "PHONES"   )
    private String phones;

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
    private String smsNumber;

    @Column(name = "LINE_ORDER"    )
    private Integer lineOrder;
    
    @Column(name = "IS_CORNER"    )
    private Integer isCorner;

	public Integer getPointId() {
		return pointId;
	}

	public void setPointId(Integer pointId) {
		this.pointId = pointId;
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
    	


    
    
}