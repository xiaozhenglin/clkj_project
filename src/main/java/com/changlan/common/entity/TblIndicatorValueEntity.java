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
 * TBL_INDICATOR_VALUE
 * 
 * @author bianj
 * @version 1.0.0 2019-02-23
 */
@Entity
@Table(name = "TBL_INDICATOR_VALUE")
public class TblIndicatorValueEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 3360592462521988207L;

    /** indicatorId */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "INDICATOR_ID", unique = true )
    private Integer indicatorId;

    /** name */
    @Column(name = "NAME"   )
    private String name;

    /** indicatorCode */
    @Column(name = "INDICATOR_CODE"   )
    private String indicatorCode;

    /** categroryId */
    @Column(name = "CATEGORY_ID"    )
    private Integer categoryId;

    @Column(name = "UNIT" )
    private String unit;


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
     * 获取name
     * 
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置name
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取indicatorCode
     * 
     * @return indicatorCode
     */
    public String getIndicatorCode() {
        return this.indicatorCode;
    }

    /**
     * 设置indicatorCode
     * 
     * @param indicatorCode
     */
    public void setIndicatorCode(String indicatorCode) {
        this.indicatorCode = indicatorCode;
    }

    public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	

}