package com.changlan.point.vo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import com.changlan.common.entity.TblCompanyChannelEntity;
import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;
import com.changlan.point.pojo.LineDetail;
import com.changlan.point.service.IChannelService;
import com.changlan.point.service.ILineService;

public class CompanyVo {
	
    private Integer companyId;
    private String  title;
	private List<ChannelVO> ChannelVOS = new ArrayList<ChannelVO>(); //二级包含多条通道信息

	public CompanyVo(TblCompanyEntity company) {
		super();
		this.companyId = company.getCompanyId();
		this.title = company.getName();
		List<TblCompanyChannelEntity> channels =  new ArrayList<TblCompanyChannelEntity>();
		channels =  getChannels(company);
		
		for(TblCompanyChannelEntity entity : channels) {
			ChannelVO vo  = new ChannelVO(entity);
			ChannelVOS.add(vo);
		}
	}

	private List<TblCompanyChannelEntity> getChannels(TblCompanyEntity company) {
		IChannelService channelService = SpringUtil.getBean(IChannelService.class);
		TblCompanyChannelEntity entity = new TblCompanyChannelEntity();
		entity.setCompanyId(company.getCompanyId()); 
		return channelService.getAllChannel(entity);
	}


	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<ChannelVO> getChannelVOS() {
		return ChannelVOS;
	}

	public void setChannelVOS(List<ChannelVO> channelVOS) {
		ChannelVOS = channelVOS;
	}

	

}
