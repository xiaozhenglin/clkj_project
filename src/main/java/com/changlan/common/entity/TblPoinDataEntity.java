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
import org.hibernate.annotations.GenericGenerator;

import com.changlan.alarm.pojo.AlarmDownType;

/**
 * TBL_POIN_DATA
 * 
 * @author bianj
 * @version 1.0.0 2019-02-23
 */
@Entity
@Table(name = "TBL_POIN_DATA")
public class TblPoinDataEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -4369942184577385527L;

    /** pointDataId */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "POINT_DATA_ID", unique = true )
    private Integer pointDataId;

    /** categroryId */
    @Column(name = "CATEGRORY_ID"    )
    private Integer categroryId; //指标类别id

    /** indicatorId */
    @Column(name = "INDICATOR_ID"    )
    private Integer indicatorId;

    /** value */
    @Column(name = "VALUE"   )
    private String value;

    /** recordTime */
    @Column(name = "RECORD_TIME", nullable = true)
    private Date recordTime;

    /** isEarlyWarning */
    @Column(name = "IS_EARLY_WARNING"    )
    private Integer isEarlyWarning;

    /** isAlarm */
    @Column(name = "IS_ALARM"    )
    private Integer isAlarm;

    /** pointId */
    @Column(name = "POINT_ID"    )
    private Integer pointId;

    /** pointName */
    @Column(name = "POINT_NAME"    )
    private String pointName;

    /** pointCatagoryId */
    @Column(name = "POINT_CATAGORY_ID"    )
    private Integer pointCatagoryId;
    
    @Column(name = "PROTOCOL_ID"    )
    private Integer protocolId;
    
    @Column(name = "ELARLY_ALAM_DATA_ID"    )
    private String elarlyAlamDataId;

    @Column(name = "ALARM_DATA_ID"    )
    private String alarmDataId;

    @Column(name = "ALARM_DOWN_RECORD"    )
    private Integer alarmDownRecord;
    
    @Column(name = "ALARM_DOWN" )
//    @Enumerated(EnumType.STRING)
    private String alarmDown;

    
    /**
     * 获取pointDataId
     * 
     * @return pointDataId
     */
    public Integer getPointDataId() {
        return this.pointDataId;
    }

    /**
     * 设置pointDataId
     * 
     * @param pointDataId
     */
    public void setPointDataId(Integer pointDataId) {
        this.pointDataId = pointDataId;
    }

    /**
     * 获取categroryId
     * 
     * @return categroryId
     */
    public Integer getCategroryId() {
        return this.categroryId;
    }

    /**
     * 设置categroryId
     * 
     * @param categroryId
     */
    public void setCategroryId(Integer categroryId) {
        this.categroryId = categroryId;
    }

    /**
     * 获取indicatorId
     * 
     * @return indicatorId
     */
    public Integer getIndicatorId() {
        return this.indicatorId;
    }

    /**
     * 设置indicatorId
     * 
     * @param indicatorId
     */
    public void setIndicatorId(Integer indicatorId) {
        this.indicatorId = indicatorId;
    }

    /**
     * 获取value
     * 
     * @return value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * 设置value
     * 
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取recordTime
     * 
     * @return recordTime
     */
    public Date getRecordTime() {
        return this.recordTime;
    }

    /**
     * 设置recordTime
     * 
     * @param recordTime
     */
    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    /**
     * 获取isEarlyWarning
     * 
     * @return isEarlyWarning
     */
    public Integer getIsEarlyWarning() {
        return this.isEarlyWarning;
    }

    /**
     * 设置isEarlyWarning
     * 
     * @param isEarlyWarning
     */
    public void setIsEarlyWarning(Integer isEarlyWarning) {
        this.isEarlyWarning = isEarlyWarning;
    }

    /**
     * 获取isAlarm
     * 
     * @return isAlarm
     */
    public Integer getIsAlarm() {
        return this.isAlarm;
    }

    /**
     * 设置isAlarm
     * 
     * @param isAlarm
     */
    public void setIsAlarm(Integer isAlarm) {
        this.isAlarm = isAlarm;
    }

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

	public Integer getProtocolId() {
		return protocolId;
	}

	public void setProtocolId(Integer protocolId) {
		this.protocolId = protocolId;
	}

	public String getAlarmDataId() {
		return alarmDataId;
	}

	public void setAlarmDataId(String alarmDataId) {
		this.alarmDataId = alarmDataId;
	}

	public Integer getAlarmDownRecord() {
		return alarmDownRecord;
	}

	public void setAlarmDownRecord(Integer alarmDownRecord) {
		this.alarmDownRecord = alarmDownRecord;
	}

	public String getAlarmDown() {
		return alarmDown;
	}

	public void setAlarmDown(String alarmDown) {
		this.alarmDown = alarmDown;
	}

	public String getElarlyAlamDataId() {
		return elarlyAlamDataId;
	}

	public void setElarlyAlamDataId(String elarlyAlamDataId) {
		this.elarlyAlamDataId = elarlyAlamDataId;
	}
    
    
}