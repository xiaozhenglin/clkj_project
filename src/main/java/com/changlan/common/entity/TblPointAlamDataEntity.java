package com.changlan.common.entity;
import javax.persistence.GenerationType;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * TBL_POINT_ALAM_DATA
 * 
 * @author bianj
 * @version 1.0.0 2019-02-23
 */
@Entity
@Table(name = "TBL_POINT_ALAM_DATA")
public class TblPointAlamDataEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -7061358098448830506L;

    /** alarmId */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ALARM_ID", unique = true )
    private Integer alarmId;

    /** alarmDate */
    @Column(name = "ALARM_DATE", nullable = true)
    private Date alarmDate;

    /** indicatorValue */
    @Column(name = "VALUE")
    private String value;

    /** indicatorId */
    @Column(name = "INDICATOR_ID"  )
    private Integer indicatorId;

    /** categroryId */
    @Column(name = "CATEGRORY_ID" )
    private Integer categroryId; //指标类别id

    /** isNotice */
    @Column(name = "IS_NOTICE" )
    private Integer isNotice;

    /** alarmRuleId */
    @Column(name = "ALARM_RULE_ID"  )
    private Integer alarmRuleId;
    
    @Column(name = "POINT_ID"  )
    private Integer pointId;
    
    @Column(name = "CONTRAST_DATA_ID"  )
    private Integer contrastDataId;
    
    @Column(name = "CURRENT_DATA_ID"  )
    private Integer currentDataId;
    
    @Column(name = "DATA_FROM"  )
    private String dataFrom; //来源数据表名
    
    @Column(name = "ALAM_DOWN_RECORD_ID"  )
    private Integer alarmDownRecordId;
    
    @Column(name ="DOWN_STATUS")
    private String downStatus; // AlarmDownType 
    
    @Column(name ="CONTENT")
    private String content; //报警内容
    

    @Transient
	private String  pointName;
    
    @Transient
   	private Integer  lineId;
    
    @Transient
   	private String  lineName;
    
    @Transient
	private String  indicatorName;
    
    @Transient
	private String  unit; //指标单位

	/**
     * 获取alarmId
     * 
     * @return alarmId
     */
    public Integer getAlarmId() {
        return this.alarmId;
    }

    /**
     * 设置alarmId
     * 
     * @param alarmId
     */
    public void setAlarmId(Integer alarmId) {
        this.alarmId = alarmId;
    }

    /**
     * 获取alarmDate
     * 
     * @return alarmDate
     */
    public Date getAlarmDate() {
        return this.alarmDate;
    }

    /**
     * 设置alarmDate
     * 
     * @param alarmDate
     */
    public void setAlarmDate(Date alarmDate) {
        this.alarmDate = alarmDate;
    }


    public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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
     * 获取isNotice
     * 
     * @return isNotice
     */
    public Integer getIsNotice() {
        return this.isNotice;
    }

    /**
     * 设置isNotice
     * 
     * @param isNotice
     */
    public void setIsNotice(Integer isNotice) {
        this.isNotice = isNotice;
    }

    /**
     * 获取alarmRuleId
     * 
     * @return alarmRuleId
     */
    public Integer getAlarmRuleId() {
        return this.alarmRuleId;
    }

    /**
     * 设置alarmRuleId
     * 
     * @param alarmRuleId
     */
    public void setAlarmRuleId(Integer alarmRuleId) {
        this.alarmRuleId = alarmRuleId;
    }

	public Integer getPointId() {
		return pointId;
	}

	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}

	public Integer getContrastDataId() {
		return contrastDataId;
	}

	public void setContrastDataId(Integer contrastDataId) {
		this.contrastDataId = contrastDataId;
	}

	public Integer getCurrentDataId() {
		return currentDataId;
	}

	public void setCurrentDataId(Integer currentDataId) {
		this.currentDataId = currentDataId;
	}

	public String getDataFrom() {
		return dataFrom;
	}

	public void setDataFrom(String dataFrom) {
		this.dataFrom = dataFrom;
	}

	public Integer getAlarmDownRecordId() {
		return alarmDownRecordId;
	}

	public void setAlarmDownRecordId(Integer alarmDownRecordId) {
		this.alarmDownRecordId = alarmDownRecordId;
	}

	public String getDownStatus() {
		return downStatus;
	}

	public void setDownStatus(String downStatus) {
		this.downStatus = downStatus;
	}

	public String getIndicatorName() {
		return indicatorName;
	}

	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getLineId() {
		return lineId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
    
}