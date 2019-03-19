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
 * TBL_COMPANY
 * 
 * @author bianj
 * @version 1.0.0 2019-02-23
 */
@Entity
@Table(name = "TBL_COMPANY")
public class TblCompanyEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -872400279247946532L;

    /** companyId */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COMPANY_ID", unique = true )
    private Integer companyId;

    /** nmae */
    @Column(name = "NAME"   )
    private String name;

    /** groupName */
    @Column(name = "GROUP_NAME"   )
    private String groupName;

    /** groupId */
    @Column(name = "GROUP_ID"    )
    private Integer groupId;
    
    @Column(name = "PARENT"    )
    private Integer parent;
     

    /**
     * 获取companyId
     * 
     * @return companyId
     */
    public Integer getCompanyId() {
        return this.companyId;
    }

    /**
     * 设置companyId
     * 
     * @param companyId
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

  

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
     * 获取groupName
     * 
     * @return groupName
     */
    public String getGroupName() {
        return this.groupName;
    }

    /**
     * 设置groupName
     * 
     * @param groupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}
    
    
}