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
 * TBL_POINT_CATEGORY
 * 
 * @author bianj
 * @version 1.0.0 2019-02-23
 */
@Entity
@Table(name = "TBL_POINT_CATEGORY")
public class TblPointCategoryEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 6347206490860428800L;

    /** pointCatgoryId */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "POINT_CATGORY_ID", unique = true )
    private Integer pointCatgoryId;

    /** pontCatagoryName */
    @Column(name = "PONT_CATAGORY_NAME"   )
    private String pontCatagoryName;

    /**
     * 获取pointCatgoryId
     * 
     * @return pointCatgoryId
     */
    public Integer getPointCatgoryId() {
        return this.pointCatgoryId;
    }

    /**
     * 设置pointCatgoryId
     * 
     * @param pointCatgoryId
     */
    public void setPointCatgoryId(Integer pointCatgoryId) {
        this.pointCatgoryId = pointCatgoryId;
    }

    /**
     * 获取pontCatagoryName
     * 
     * @return pontCatagoryName
     */
    public String getPontCatagoryName() {
        return this.pontCatagoryName;
    }

    /**
     * 设置pontCatagoryName
     * 
     * @param pontCatagoryName
     */
    public void setPontCatagoryName(String pontCatagoryName) {
        this.pontCatagoryName = pontCatagoryName;
    }

    
    
}