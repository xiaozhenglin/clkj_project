package com.changlan.common.entity;

import java.io.Serializable;

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
    private String recordTime;
    
    @Column(name = "RECORD_USER"   )
    private String recordUser;
    
    @Column(name = "DOWN_RESULT"   )
    private String downResult;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
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
	
    

}
