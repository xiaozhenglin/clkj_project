package com.changlan.point.vo;

import java.util.ArrayList;
import java.util.List;

import com.changlan.common.entity.TblCompanyChannelEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblMonitorSystemEntity;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.point.pojo.LineDetail;
import com.changlan.point.service.ILineService;
import com.changlan.point.service.IMonitorSystemService;

public class ChannelVO {
	
	private Integer channelId;
	private String  title;
	private String  easy_vedio_url;
	private String  snap_url;
	private List<MonitorSystemVO> MonitorSystemVOs = new ArrayList<MonitorSystemVO>(); //三级包含多条线路信息
	  
	public ChannelVO() {
		super();
	}
	
	public ChannelVO(TblCompanyChannelEntity entity) { 
		this.channelId = entity.getChannelId();
		this.title = entity.getName();
		this.easy_vedio_url = easy_vedio_url;
		this.snap_url = snap_url;
		String monitorIds = entity.getMonitor_ids();
		  List<TblMonitorSystemEntity> list = getMonitorSystem(monitorIds);
		  for(TblMonitorSystemEntity monitor : list)
		  {
			  MonitorSystemVO VO = new MonitorSystemVO(monitor,channelId);
			  MonitorSystemVOs.add(VO); }
		 
	}

	private List<TblMonitorSystemEntity> getMonitorSystem(String monitorIds) {
		IMonitorSystemService service = SpringUtil.getBean(IMonitorSystemService.class);
		String [] monitorArray = monitorIds.split(",");
		//entity.setChannelId(channelId);
		List<TblMonitorSystemEntity> list = new ArrayList<TblMonitorSystemEntity>();
		list.clear();
		for(String monitor : monitorArray) {		
			TblMonitorSystemEntity entity = new TblMonitorSystemEntity();
			entity.setMonitorId(Integer.parseInt(monitor));
			List<TblMonitorSystemEntity> all = service.getAll(entity); 
			if(!ListUtil.isEmpty(all)) {
				list.add(all.get(0));
			}
		}
		return list;
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

	public String getEasy_vedio_url() {
		return easy_vedio_url;
	}

	public void setEasy_vedio_url(String easy_vedio_url) {
		this.easy_vedio_url = easy_vedio_url;
	}

	public String getSnap_url() {
		return snap_url;
	}

	public void setSnap_url(String snap_url) {
		this.snap_url = snap_url;
	}	  
	 
	
}
