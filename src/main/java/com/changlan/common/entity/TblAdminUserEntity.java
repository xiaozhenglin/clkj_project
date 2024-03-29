package com.changlan.common.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.changlan.common.pojo.BasicInfo;
import com.changlan.user.pojo.UserStatus;

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

    /** image */
    @Column(name = "IMAGE"   )
    private String image;
    
    @Column(name = "ID_CARD"   )
    private String idcard;	
    
    @Column(name = "PHONE"   )
    private String  phone;	
    
    @Column(name = "STATUS")
//    @Enumerated(EnumType.STRING)
    private String status;	
   
    @Column(name = "IS_TEMP"   )
    private Integer isTemp;
    
    @Column(name = "ADD_TIME"   )
    private Date addTime;
    
    @Column(name = "LAST_LOGIN_IN"   )
    private Date lastLoginIn;
    
    /** copanyId */
    @Column(name = "COMPANY_ID"   )
    private Integer companyId;
    
    /** copanyId */
    @Column(name = "LINE_ID"   )
    private String lineId;
    
    @Column(name = "REMOVE_FLAGE"   )
    private Integer removeFlage;
    
    @Column(name = "TRUE_NAME"   )
    private String trueName;
    
    @Transient	
	private String redirctUrl ; //跳转地址
    
    @Transient	
	private String companyName ;
    
    @Transient	
	private String userGroupName ; 
   
    @Transient
    private String userid;

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
    
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getIsTemp() {
		return isTemp;
	}

	public void setIsTemp(Integer isTemp) {
		this.isTemp = isTemp;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getLastLoginIn() {
		return lastLoginIn;
	}

	public void setLastLoginIn(Date lastLoginIn) {
		this.lastLoginIn = lastLoginIn;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public Integer getRemoveFlage() {
		return removeFlage;
	}

	public void setRemoveFlage(Integer removeFlage) {
		this.removeFlage = removeFlage;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getRedirctUrl() {
		return redirctUrl;
	}

	public void setRedirctUrl(String redirctUrl) {
		this.redirctUrl = redirctUrl;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUserGroupName() {
		return userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
    		
}