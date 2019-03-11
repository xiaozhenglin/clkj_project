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
 * TBL_USER_GROUP_FUN
 * 
 * @author bianj
 * @version 1.0.0 2019-02-23
 */
@Entity
@Table(name = "TBL_USER_GROUP_FUN")
public class TblUserGroupFunEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -2908312372253311816L;

    /** groupFuncId */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GROUP_FUNC_ID", unique = true )
    private Integer groupFuncId;

    /** userGroupId */
    @Column(name = "USER_GROUP_ID"    )
    private Integer userGroupId;

    /** funcId */
    @Column(name = "FUNC_ID"    )
    private Integer funcId;

    /**
     * 获取groupFuncId
     * 
     * @return groupFuncId
     */
    public Integer getGroupFuncId() {
        return this.groupFuncId;
    }

    /**
     * 设置groupFuncId
     * 
     * @param groupFuncId
     */
    public void setGroupFuncId(Integer groupFuncId) {
        this.groupFuncId = groupFuncId;
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
     * 获取funcId
     * 
     * @return funcId
     */
    public Integer getFuncId() {
        return this.funcId;
    }

    /**
     * 设置funcId
     * 
     * @param funcId
     */
    public void setFuncId(Integer funcId) {
        this.funcId = funcId;
    }
}