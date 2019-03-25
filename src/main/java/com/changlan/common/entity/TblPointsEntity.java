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

import com.changlan.common.pojo.PointStatus;

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

    /**
     * 获取pointId
     * 
     * @return pointId
     */
    public Integer getPointId() {
        return this.pointId;
    }

    /**
     * 设置pointId
     * 
     * @param pointId
     */
    public void setPointId(Integer pointId) {
        this.pointId = pointId;
    }

    /**
     * 获取lineId
     * 
     * @return lineId
     */
    public Integer getLineId() {
        return this.lineId;
    }

    /**
     * 设置lineId
     * 
     * @param lineId
     */
    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
     * 获取image
     * 
     * @return image
     */
    public String getImage() {
        return this.image;
    }

    /**
     * 设置image
     * 
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * 获取pointAddress
     * 
     * @return pointAddress
     */
    public String getPointAddress() {
        return this.pointAddress;
    }

    /**
     * 设置pointAddress
     * 
     * @param pointAddress
     */
    public void setPointAddress(String pointAddress) {
        this.pointAddress = pointAddress;
    }

    /**
     * 获取longLati
     * 
     * @return longLati
     */
    public String getLongLati() {
        return this.longLati;
    }

    /**
     * 设置longLati
     * 
     * @param longLati
     */
    public void setLongLati(String longLati) {
        this.longLati = longLati;
    }

	/**
     * 获取indicators
     * 
     * @return indicators
     */
    public String getIndicators() {
        return this.indicators;
    }

    /**
     * 设置indicators
     * 
     * @param indicators
     */
    public void setIndicators(String indicators) {
        this.indicators = indicators;
    }

    /**
     * 获取pointName
     * 
     * @return pointName
     */
    public String getPointName() {
        return this.pointName;
    }

    /**
     * 设置pointName
     * 
     * @param pointName
     */
    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    /**
     * 获取phones
     * 
     * @return phones
     */
    public String getPhones() {
        return this.phones;
    }

    /**
     * 设置phones
     * 
     * @param phones
     */
    public void setPhones(String phones) {
        this.phones = phones;
    }

    /**
     * 获取pointCatagoryId
     * 
     * @return pointCatagoryId
     */
    public Integer getPointCatagoryId() {
        return this.pointCatagoryId;
    }

    /**
     * 设置pointCatagoryId
     * 
     * @param pointCatagoryId
     */
    public void setPointCatagoryId(Integer pointCatagoryId) {
        this.pointCatagoryId = pointCatagoryId;
    }

	public String getPointRegistPackage() {
		return pointRegistPackage;
	}

	public void setPointRegistPackage(String pointRegistPackage) {
		this.pointRegistPackage = pointRegistPackage;
	}
    
    
}