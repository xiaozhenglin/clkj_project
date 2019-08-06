package com.changlan.point.vo;

import java.util.ArrayList;
import java.util.List;

import com.changlan.common.entity.TblCompanyChannelEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblMonitorSystemEntity;
import com.changlan.common.util.SpringUtil;
import com.changlan.point.pojo.LineDetail;
import com.changlan.point.service.ILineService;
import com.changlan.point.service.IMonitorSystemService;

public class ChannelVO {
	
	private Integer channelId;
	private String  title;
	private List<MonitorSystemVO> MonitorSystemVOs = new ArrayList<MonitorSystemVO>(); //三级包含多条线路信息
	  
	public ChannelVO() {
		super();
	}
	
	public ChannelVO(TblCompanyChannelEntity entity) { 
		this.channelId = entity.getChannelId();
		this.title = entity.getName();
		
		  List<TblMonitorSystemEntity> list = getMonitorSystem();
		  for(TblMonitorSystemEntity monitor : list)
		  {
			  MonitorSystemVO VO = new MonitorSystemVO(monitor);
			  MonitorSystemVOs.add(VO); }
		 
	}

	private List<TblMonitorSystemEntity> getMonitorSystem() {
		IMonitorSystemService service = SpringUtil.getBean(IMonitorSystemService.class);
		TblMonitorSystemEntity entity = new TblMonitorSystemEntity();
		//entity.setChannelId(channelId);
		List<TblMonitorSystemEntity> all = service.getAll(entity); 
		return all;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public List<MonitorSystemVO> getMonitorSystemVOs() {
		return MonitorSystemVOs;
	}

	public void setMonitorSystemVOs(List<MonitorSystemVO> monitorSystemVOs) {
		MonitorSystemVOs = monitorSystemVOs;
	}	  
	  
}
