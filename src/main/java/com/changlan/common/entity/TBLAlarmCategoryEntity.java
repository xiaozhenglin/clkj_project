package com.changlan.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_ALARM_CATEGORY")
public class TBLAlarmCategoryEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ALARM_CATEGORY_ID", unique = true )
	private Integer alarmCategoryId;
	
    @Column(name = "NAME", unique = true )
	private String name;
    
    @Column(name = "EXCEPTION_DESC"   )
    private String exceptionDesc;

	public Integer getAlarmCategoryId() {
		return alarmCategoryId;
	}

	public void setAlarmCategoryId(Integer alarmCategoryId) {
		this.alarmCategoryId = alarmCategoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExceptionDesc() {
		return exceptionDesc;
	}

	public void setExceptionDesc(String exceptionDesc) {
		this.exceptionDesc = exceptionDesc;
	}
    
}
