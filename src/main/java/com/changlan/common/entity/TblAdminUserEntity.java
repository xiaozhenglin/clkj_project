package com.changlan.common.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.changlan.common.pojo.BasicInfo;

/**
 * TBL_ADMIN_USER
 * 
 * @author bianj
 * @version 1.0.0 2019-02-23
 */
@Entity
@Table(name = "TBL_ADMIN_USER")
public class TblAdminUserEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -2819301106527109744L;

    /** adminUserId */
    @Id
    @Column(name = "ADMIN_USER_ID", unique = true )
    private String adminUserId;

    /** name */
    @Column(name = "NAME"   )
    private String name;

    /** pass */
    @Column(name = "PASS"    )
    private String pass;

    /** userGroupId */
    @Column(name = "USER_GROUP_ID"    )
    private Integer userGroupId;

    /** copanyId */
    @Column(name = "COPANY_ID"   )
    private String copanyId;

    /** image */
    @Column(name = "IMAGE"   )
    private String image;

    public TblAdminUserEntity(TblAdminUserEntity user) {
    	TblAdminUserEntity entity = new TblAdminUserEntity();
    	entity = user;
	}

	public TblAdminUserEntity() {
		super();
	}


	/**
     * 获取adminUserId
     * 
     * @return adminUserId
     */
    public String getAdminUserId() {
        return this.adminUserId;
    }

    /**
     * 设置adminUserId
     * 
     * @param adminUserId
     */
    public void setAdminUserId(String adminUserId) {
        this.adminUserId = adminUserId;
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
     * 获取pass
     * 
     * @return pass
     */
    public String getPass() {
        return this.pass;
    }

    /**
     * 设置pass
     * 
     * @param pass
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * 获取userGroupId
     * 
     * @return userGroupId
     */
    public Integer getUserGroupId() {
        return this.userGroupId;
    }

    /**
     * 设置userGroupId
     * 
     * @param userGroupId
     */
    public void setUserGroupId(Integer userGroupId) {
        this.userGroupId = userGroupId;
    }

    /**
     * 获取copanyId
     * 
     * @return copanyId
     */
    public String getCopanyId() {
        return this.copanyId;
    }

    /**
     * 设置copanyId
     * 
     * @param copanyId
     */
    public void setCopanyId(String copanyId) {
        this.copanyId = copanyId;
    }

    /**
     * 获取image
     * 
     * @return image
     */
    public String getImage() {
        return this.image;
    }

    /**
     * 设置image
     * 
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
    }
}