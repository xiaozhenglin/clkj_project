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
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * TBL_MSG_DATA
 * 
 * @author bianj
 * @version 1.0.0 2019-02-23
 */
@Entity
@Table(name = "TBL_MSG_DATA")
public class TblMsgDataEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -7351418158703149777L;

    /** msgId */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MSG_ID", unique = true )
    private Integer msgId;

    /** sendTime */
    @Column(name = "SEND_TIME", nullable = true)
    private Date sendTime;

    /** content */
    @Column(name = "CONTENT" )
    private String content;

    /** phoneOrEmail */
    @Column(name = "PHONE_OR_EMAIL"   )
    private String phoneOrEmail;

    /** msgType */
    @Column(name = "MSG_TYPE"    )
    private String msgType;

    /** adminUserId */
    @Column(name = "ADMIN_USER_ID"    )
    private String adminUserId;
    
    @Column(name = "DIRECTION"    )
    private Integer direction;
    
    /**
     * 获取msgId
     * 
     * @return msgId
     */
    public Integer getMsgId() {
        return this.msgId;
    }

    /**
     * 设置msgId
     * 
     * @param msgId
     */
    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    /**
     * 获取sendTime
     * 
     * @return sendTime
     */
    public Date getSendTime() {
        return this.sendTime;
    }

    /**
     * 设置sendTime
     * 
     * @param sendTime
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 获取content
     * 
     * @return content
     */
    public String getContent() {
        return this.content;
    }

    /**
     * 设置content
     * 
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取phoneOrEmail
     * 
     * @return phoneOrEmail
     */
    public String getPhoneOrEmail() {
        return this.phoneOrEmail;
    }

    /**
     * 设置phoneOrEmail
     * 
     * @param phoneOrEmail
     */
    public void setPhoneOrEmail(String phoneOrEmail) {
        this.phoneOrEmail = phoneOrEmail;
    }

    /**
     * 获取msgType
     * 
     * @return msgType
     */
    public String getMsgType() {
        return this.msgType;
    }

    /**
     * 设置msgType
     * 
     * @param msgType
     */
    public void setMsgType(String msgType) {
        this.msgType = msgType;
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

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}
    
    
}