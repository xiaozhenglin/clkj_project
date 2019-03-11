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
 * 
 * @author bianj
 * @version 1.0.0 2019-02-23
 */
@Entity
@Table(name = "TBL_COMMAND_CATEGORY")
public class TblCommandCategoryEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -4847941703930909641L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COMMAND_CATAGORY_ID", unique = true )
    private Integer commandCatagoryId;

    /** categoryNmae */
    @Column(name = "CATEGORY_NMAE"    )
    private String categoryNmae;

    /** direction */
    @Column(name = "DIRECTION"    )
    private Integer direction;



    public Integer getCommandCatagoryId() {
		return commandCatagoryId;
	}

	public void setCommandCatagoryId(Integer commandCatagoryId) {
		this.commandCatagoryId = commandCatagoryId;
	}

	/**
     * 获取categoryNmae
     * 
     * @return categoryNmae
     */
    public String getCategoryNmae() {
        return this.categoryNmae;
    }

    /**
     * 设置categoryNmae
     * 
     * @param categoryNmae
     */
    public void setCategoryNmae(String categoryNmae) {
        this.categoryNmae = categoryNmae;
    }

    /**
     * 获取direction
     * 
     * @return direction
     */
    public Integer getDirection() {
        return this.direction;
    }

    /**
     * 设置direction
     * 
     * @param direction
     */
    public void setDirection(Integer direction) {
        this.direction = direction;
    }
}