package com.changlan.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_COMPANY_CHANNEL")
public class TblCompanyChannelEntity implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CHANNEL_ID", unique = true )
    private Integer channelId;

    /** NAME */
    @Column(name = "NAME"   )
    private String name;
    
    @Column(name = "COMPANY_ID", unique = true )
    private Integer companyId;
    
    @Column(name = "MONITOR_IDS"   )
    private String monitor_ids;

    @Column(name = "EASY_VEDIO_URL"   )
    private String easy_vedio_url;
    
	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
		
	public String getMonitor_ids() {
		return monitor_ids;
	}

	public void setMonitor_ids(String monitor_ids) {
		this.monitor_ids = monitor_ids;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getEasy_vedio_url() {
		return easy_vedio_url;
	}

	public void setEasy_vedio_url(String easy_vedio_url) {
		this.easy_vedio_url = easy_vedio_url;
	}
        
    
}
