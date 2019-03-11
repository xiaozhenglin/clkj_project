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
 * TBL_COMPANY_GROUP
 * 
 * @author bianj
 * @version 1.0.0 2019-02-23
 */
@Entity
@Table(name = "TBL_COMPANY_GROUP")
public class TblCompanyGroupEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -933517590759937451L;

    /** groupId */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GROUP_ID", unique = true )
    private Integer groupId;

    /** phone */
    @Column(name = "PHONE"   )
    private String phone;

    /** isCompany */
    @Column(name = "IS_COMPANY"    )
    private Integer isCompany;

    /** name */
    @Column(name = "NAME"   )
    private String name;

    /** parent */
    @Column(name = "PARENT"    )
    private Integer parent;

    public TblCompanyGroupEntity(TblCompanyGroupEntity groupInfo) {
    	this.groupId = groupInfo.getGroupId();
		this.phone = groupInfo.getPhone();
		this.isCompany = groupInfo.isCompany;
		this.name = groupInfo.getName();
		this.parent = groupInfo.getParent();
	}
    
	public TblCompanyGroupEntity(Integer groupId, String phone, Integer isCompany, String name, Integer parent) {
		super();
		this.groupId = groupId;
		this.phone = phone;
		this.isCompany = isCompany;
		this.name = name;
		this.parent = parent;
	}

	public TblCompanyGroupEntity() {
		super();
	}

	/**
     * 获取groupId
     * 
     * @return groupId
     */
    public Integer getGroupId() {
        return this.groupId;
    }

    /**
     * 设置groupId
     * 
     * @param groupId
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * 获取phone
     * 
     * @return phone
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * 设置phone
     * 
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取isCompany
     * 
     * @return isCompany
     */
    public Integer getIsCompany() {
        return this.isCompany;
    }

    /**
     * 设置isCompany
     * 
     * @param isCompany
     */
    public void setIsCompany(Integer isCompany) {
        this.isCompany = isCompany;
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