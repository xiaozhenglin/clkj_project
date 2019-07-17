package com.changlan.common.entity;

import java.io.Serializable;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="TBL_DVD")
public class TblDvdEntity implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)

	private Integer dvd_id;	
	
	private String  name; //名称
		
	private Date createtime; //创建时间
	private Date modifytime; //修改时间

	@Column(name="PICTURE_URL")
	private String picture_url; //图片地址
	
	@Column(name="VIDEO_URL")
	private String vidio_url; //视频地址 
	
	@Column(name = "RECORD_USER"   )
    private String recordUser;
	
	@Column(name="POINT_ID")
	private Integer pointId;

	
	
	public String getRecordUser() {
		return recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	

	public String getVidio_url() {
		return vidio_url;
	}

	public void setVidio_url(String vidio_url) {
		this.vidio_url = vidio_url;
	}

	public Integer getDvd_id() {
		return dvd_id;
	}

	public void setDvd_id(Integer dvd_id) {
		this.dvd_id = dvd_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture_url() {
		return picture_url;
	}

	public void setPicture_url(String picture_url) {
		this.picture_url = picture_url;
	}



	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	public Integer getPointId() {
		return pointId;
	}

	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}
				
}
