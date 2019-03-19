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
 * TBL_USER_FUNCTION
 * 
 * @author bianj
 * @version 1.0.0 2019-02-23
 */
@Entity
@Table(name = "TBL_USER_FUNCTION")
public class TblUserFunctionEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 5188935181796974439L;

    /** userFunId */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_FUN_ID", unique = true )
    private Integer userFunId;

    /** adminUserId */
    @Column(name = "ADMIN_USER_ID"  )
    private String adminUserId;

    /** funcId */
    @Column(name = "FUNC_ID" )
    private Integer funcId;

    /**
     * 获取userFunId
     * 
     * @return userFunId
     */
    public Integer getUserFunId() {
        return this.userFunId;
    }

    /**
     * 设置userFunId
     * 
     * @param userFunId
     */
    public void setUserFunId(Integer userFunId) {
        this.userFunId = userFunId;
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