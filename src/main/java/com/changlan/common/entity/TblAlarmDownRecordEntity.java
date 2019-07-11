package com.changlan.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_ALARM_DOWN_RECORD")
public class TblAlarmDownRecordEntity implements java.io.Serializable {

    /** adminUserId */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ALAM_DOWN_RECORD_ID", unique = true )
    private Integer alamDownRecordId;

    @Column(name = "REASON" )
    private String reason;
    
    @Column(name = "RECORD_TIME" )
    private Date recordTime;
    
    @Column(name = "RECORD_USER"   )
    private String recordUser;
    
    @Column(name = "DOWN_RESULT"   )
    private String downResult;
    
    @Column(name = "ALARM_DATA_ID"   )
    private Integer alarmDataId;
    
    @Column(name = "POINT_ID"   )
    private Integer point_id; //监控点id
    
    
	public Integer getPoint_id() {
		return point_id;
	}

	public void setPoint_id(Integer point_id) {
		this.point_id = point_id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public String getRecordUser() {
		return recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	public String getDownResult() {
		return downResult;
	}

	public void setDownResult(String downResult) {
		this.downResult = downResult;
	}

	public Integer getAlamDownRecordId() {
		return alamDownRecordId;
	}

	public void setAlamDownRecordId(Integer alamDownRecordId) {
		this.alamDownRecordId = alamDownRecordId;
	}

	public Integer getAlarmDataId() {
		return alarmDataId;
	}

	public void setAlarmDataId(Integer alarmDataId) {
		this.alarmDataId = alarmDataId;
	}

	
    

}
