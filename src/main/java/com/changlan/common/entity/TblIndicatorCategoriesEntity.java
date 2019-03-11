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
 * TBL_INDICATOR_CATEGORIES
 * 
 * @author bianj
 * @version 1.0.0 2019-02-23
 */
@Entity
@Table(name = "TBL_INDICATOR_CATEGORIES")
public class TblIndicatorCategoriesEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 4223220533929126096L;

    /** categroryId */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CATEGORY_ID", unique = true )
    private Integer categoryId;

    /** name */
    @Column(name = "NAME"   )
    private String name;

    /** categoryCode */
    @Column(name = "CATEGORY_CODE"   )
    private String categoryCode;

    /** parent */
    @Column(name = "PARENT"    )
    private Integer parent;

   
    public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
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
     * 获取categoryCode
     * 
     * @return categoryCode
     */
    public String getCategoryCode() {
        return this.categoryCode;
    }

    /**
     * 设置categoryCode
     * 
     * @param categoryCode
     */
    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    /**
     * 获取parent
     * 
     * @return parent
     */
    public Integer getParent() {
        return this.parent;
    }

    /**
     * 设置parent
     * 
     * @param parent
     */
    public void setParent(Integer parent) {
        this.parent = parent;
    }
}