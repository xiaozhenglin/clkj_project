package com.changlan.common.entity;
import javax.persistence.GenerationType;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
    private Integer categroryId;

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
    private String dataFrom;
    
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
	
    
}