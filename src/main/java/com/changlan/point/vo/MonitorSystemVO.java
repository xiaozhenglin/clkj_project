package com.changlan.point.vo;

import java.util.ArrayList;
import java.util.List;

import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblMonitorSystemEntity;
import com.changlan.common.util.SpringUtil;
import com.changlan.point.pojo.LineDetail;
import com.changlan.point.service.ILineService;

public class MonitorSystemVO {
	
	private Integer monitorSystemId;
    private String  title;
	private List<ChannelLineVO> companyLinesVOs = new ArrayList<ChannelLineVO>(); //三级包含多条线路信息
	
	public MonitorSystemVO(Integer monitorSystemId, List<ChannelLineVO> companyLinesVOs) {
		this.monitorSystemId = monitorSystemId;
		
	}
	
	public MonitorSystemVO(TblMonitorSystemEntity monitor) { 
		this.monitorSystemId = monitor.getMonitorId();
		this.title = monitor.getName();
		List<LineDetail>  list = getLines(monitor.getMonitorId());
		for(LineDetail detail : list) {
			ChannelLineVO VO = new ChannelLineVO(detail);
			companyLinesVOs.add(VO);
		}
	}


	private List<LineDetail> getLines(Integer monitorId) {
		ILineService lineService = SpringUtil.getBean(ILineService.class);
		TblLinesEntity entity = new TblLinesEntity();
		entity.setMonitorId(monitorId);
		List<LineDetail> all = lineService.getAll(entity); 
		return all;
	}

	public Integer getMonitorSystemId() {
		return monitorSystemId;
	}

	public void setMonitorSystemId(Integer monitorSystemId) {
		this.monitorSystemId = monitorSystemId;
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
	
}
