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
 * TBL_USER_GROUP
 * 
 * @author bianj
 * @version 1.0.0 2019-02-23
 */
@Entity
@Table(name = "TBL_USER_GROUP")
public class TblUserGroupEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -8174353608885574913L;

    /** userGroupId */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_GROUP_ID", unique = true )
    private Integer userGroupId;

    /** name */
    @Column(name = "NAME"   )
    private String name;

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
}