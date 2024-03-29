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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.changlan.point.pojo.LineStatus;

/**
 * TBL_LINES
 * 
 * @author bianj
 * @version 1.0.0 2019-02-23
 */
@Entity
@Table(name = "TBL_LINES")
public class TblLinesEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -4930936044831868179L;

    /** lineId */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LINE_ID", unique = true )
    private Integer lineId;

    /** companyId */
    @Column(name = "MONITOR_ID"    )
    private Integer monitorId;

    /** lineName */
    @Column(name = "LINE_NAME"   )
    private String lineName;

    /** lineAddress */
    @Column(name = "LINE_ADDRESS"   )
    private String lineAddress;

    /** centerAddress */
    @Column(name = "CENTER_ADDRESS"   )
    private String centerAddress;

    /** parentId */
    @Column(name = "PARENT_ID"    )
    private Integer parentId;

    /** parentName */
    @Column(name = "PARENT_NAME"   )
    private String parentName;

    /** status */
    @Column(name = "STATUS"    )
//    @Enumerated(EnumType.STRING)
    private String status;

    /** color */
    @Column(name = "COLOR"   )
    private String color;

    /** pointCatagoryIds */
    @Column(name = "POINT_CATAGORY_IDS"   )
    private String pointCatagoryIds;

    @Column(name = "LINE_LENGTH"   )
    private Double lineLength;
    
    @Column(name = "ADD_TIME"   )
    private Date addTime;
    
    @Column(name = "PICTURL_ADDRESS"   )
    private String picture_address;
    
    @Column(name = "DIANYA_LEVEL"   )
    private String dianyaLever; //电压等级
    
    @Column(name = "CHANNEL_ID"   )
    private Integer channelId; //电压等级
    
    @Column(name = "IS_CONTROL_CHANNEL"   )
    private String is_control_channel; //是否作用于隧道
    
    @Transient
    private String channelName;
          
    @Column(name = "ON_LINE_RATIO"   )
    private String onLineRatio;  //在线率
    
    @Column(name = "IMAGE_IN_MAP"   )
    private String imageInMap;  //在地图中的图标名称
   
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

	public Integer getMonitorId() {
		return monitorId;
	}

	public void setMonitorId(Integer monitorId) {
		this.monitorId = monitorId;
	}
		
	

	public String getIs_control_channel() {
		return is_control_channel;
	}

	public void setIs_control_channel(String is_control_channel) {
		this.is_control_channel = is_control_channel;
	}

	/**
     * 获取lineName
     * 
     * @return lineName
     */
    public String getLineName() {
        return this.lineName;
    }

    /**
     * 设置lineName
     * 
     * @param lineName
     */
    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    /**
     * 获取lineAddress
     * 
     * @return lineAddress
     */
    public String getLineAddress() {
        return this.lineAddress;
    }

    /**
     * 设置lineAddress
     * 
     * @param lineAddress
     */
    public void setLineAddress(String lineAddress) {
        this.lineAddress = lineAddress;
    }

    /**
     * 获取centerAddress
     * 
     * @return centerAddress
     */
    public String getCenterAddress() {
        return this.centerAddress;
    }

    /**
     * 设置centerAddress
     * 
     * @param centerAddress
     */
    public void setCenterAddress(String centerAddress) {
        this.centerAddress = centerAddress;
    }

    /**
     * 获取parentId
     * 
     * @return parentId
     */
    public Integer getParentId() {
        return this.parentId;
    }

    /**
     * 设置parentId
     * 
     * @param parentId
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取parentName
     * 
     * @return parentName
     */
    public String getParentName() {
        return this.parentName;
    }

    /**
     * 设置parentName
     * 
     * @param parentName
     */
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

//    public LineStatus getStatus() {
//		return status;
//	}
//
//	public void setStatus(LineStatus status) {
//		this.status = status;
//	}

    
	/**
     * 获取color
     * 
     * @return color
     */
    public String getColor() {
        return this.color;
    }

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
     * 设置color
     * 
     * @param color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * 获取pointCatagoryIds
     * 
     * @return pointCatagoryIds
     */
    public String getPointCatagoryIds() {
        return this.pointCatagoryIds;
    }

    /**
     * 设置pointCatagoryIds
     * 
     * @param pointCatagoryIds
     */
    public void setPointCatagoryIds(String pointCatagoryIds) {
        this.pointCatagoryIds = pointCatagoryIds;
    }

	public Double getLineLength() {
		return lineLength;
	}

	public void setLineLength(Double lineLength) {
		this.lineLength = lineLength;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getPicture_address() {
		return picture_address;
	}

	public void setPicture_address(String picture_address) {
		this.picture_address = picture_address;
	}

	public String getDianyaLever() {
		return dianyaLever;
	}

	public void setDianyaLever(String dianyaLever) {
		this.dianyaLever = dianyaLever;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getOnLineRatio() {
		return onLineRatio;
	}

	public void setOnLineRatio(String onLineRatio) {
		this.onLineRatio = onLineRatio;
	}

	public String getImageInMap() {
		return imageInMap;
	}

	public void setImageInMap(String imageInMap) {
		this.imageInMap = imageInMap;
	}

	
	
	
    	        
}