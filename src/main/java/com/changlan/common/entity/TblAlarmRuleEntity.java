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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * TBL_ALARM_RULE
 * 
 * @author bianj
 * @version 1.0.0 2019-02-23
 */
@Entity
@Table(name = "TBL_ALARM_RULE")
public class TblAlarmRuleEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 728474305746890158L;

    /** alarmRuleId */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ALARM_RULE_ID", unique = true )
    private Integer alarmRuleId;

    /** ruleName */
    @Column(name = "RULE_NAME"   )
    private String ruleName;
    
    @Column(name = "ALARM_CATEGORY_ID"   )
    private Integer alarmCategoryId;
    
    @Column(name = "TOP_LIMIT"   )
    private Integer topLimit;
    
    @Column(name = "LOWER_LIMIT"   )
    private Integer lowerLimit;
    
    @Column(name = "TOP_ALARM"   )
    private Integer topAlarm;	
    
    @Column(name = "LOWER_ALARM"   )
    private Integer lowerAlarm;	
    
    @Column(name = "NORMAL"   )
    private Integer normal;	
    
    @Column(name = "ABNOMAL"   )
    private Integer abnomal;	
    
    @Column(name = "INDICATOR_CATEGORY_ID"   )
    private Integer indicatorCategoryId;	
    
    @Column(name = "INDICATOR_VALUE_ID"   )
    private Integer indicatorValueId;	
    
    @Column(name = "POINT_ID"   )
    private Integer pointId;
    
    @Column(name = "COMPARISON"   )
    private Integer comparison;  //用于负载的对比指标用

	public Integer getAlarmRuleId() {
		return alarmRuleId;
	}

	public void setAlarmRuleId(Integer alarmRuleId) {
		this.alarmRuleId = alarmRuleId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public Integer getAlarmCategoryId() {
		return alarmCategoryId;
	}

	public void setAlarmCategoryId(Integer alarmCategoryId) {
		this.alarmCategoryId = alarmCategoryId;
	}

	public Integer getTopLimit() {
		return topLimit;
	}

	public void setTopLimit(Integer topLimit) {
		this.topLimit = topLimit;
	}

	public Integer getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(Integer lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public Integer getTopAlarm() {
		return topAlarm;
	}

	public void setTopAlarm(Integer topAlarm) {
		this.topAlarm = topAlarm;
	}

	public Integer getLowerAlarm() {
		return lowerAlarm;
	}

	public void setLowerAlarm(Integer lowerAlarm) {
		this.lowerAlarm = lowerAlarm;
	}

	public Integer getNormal() {
		return normal;
	}

	public void setNormal(Integer normal) {
		this.normal = normal;
	}

	public Integer getAbnomal() {
		return abnomal;
	}

	public void setAbnomal(Integer abnomal) {
		this.abnomal = abnomal;
	}

	public Integer getIndicatorCategoryId() {
		return indicatorCategoryId;
	}

	public void setIndicatorCategoryId(Integer indicatorCategoryId) {
		this.indicatorCategoryId = indicatorCategoryId;
	}

	public Integer getIndicatorValueId() {
		return indicatorValueId;
	}

	public void setIndicatorValueId(Integer indicatorValueId) {
		this.indicatorValueId = indicatorValueId;
	}

	public Integer getPointId() {
		return pointId;
	}

	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}

	public Integer getComparison() {
		return comparison;
	}

	public void setComparison(Integer comparison) {
		this.comparison = comparison;
	}

	
  
}