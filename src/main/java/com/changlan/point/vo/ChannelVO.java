package com.changlan.point.vo;

import java.util.ArrayList;
import java.util.List;

import com.changlan.common.entity.TblCompanyChannelEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.util.SpringUtil;
import com.changlan.point.pojo.LineDetail;
import com.changlan.point.service.ILineService;

public class ChannelVO {
	
	private Integer channelId;
	private String  title;
	private List<ChannelLineVO> companyLinesVOs = new ArrayList<ChannelLineVO>(); //三级包含多条线路信息
	  
	public ChannelVO() {
		super();
	}
	
	public ChannelVO(TblCompanyChannelEntity entity) { 
		this.channelId = entity.getChannelId();
		this.title = entity.getName();
		List<LineDetail>  lines = getLines(entity.getChannelId());
		for(LineDetail line : lines) {
			ChannelLineVO lineVO = new ChannelLineVO(line);
			companyLinesVOs.add(lineVO);
		}
		
	}

	private List<LineDetail> getLines(Integer channelId) {
		ILineService lineService = SpringUtil.getBean(ILineService.class);
		TblLinesEntity entity = new TblLinesEntity();
		entity.setChannelId(channelId);
		List<LineDetail> all = lineService.getAll(entity); 
		return all;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<ChannelLineVO> getCompanyLinesVOs() {
		return companyLinesVOs;
	}
	public void setCompanyLinesVOs(List<ChannelLineVO> companyLinesVOs) {
		this.companyLinesVOs = companyLinesVOs;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	  
	  
	  
}
