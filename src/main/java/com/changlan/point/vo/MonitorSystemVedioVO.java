package com.changlan.point.vo;

import java.util.ArrayList;
import java.util.List;

import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblMonitorSystemEntity;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.point.pojo.LineDetail;
import com.changlan.point.service.ILineService;

public class MonitorSystemVedioVO {
	
	private Integer monitorSystemId;
    private String  title;
    private String  easy_vedio_url; //视频地址,以逗号分隔
    private String  snap_url;
	//private List<ChannelLineVedioVO> companyLinesVOs = new ArrayList<ChannelLineVedioVO>(); //三级包含多条线路信息
	
	public MonitorSystemVedioVO(Integer monitorSystemId, List<ChannelLineVO> companyLinesVOs) {
		this.monitorSystemId = monitorSystemId;
		
	}
	
//	public MonitorSystemVO(TblMonitorSystemEntity monitor) { 
//		this.monitorSystemId = monitor.getMonitorId();
//		this.title = monitor.getName();
//		List<LineDetail>  list = getLines(monitor.getMonitorId());
//		for(LineDetail detail : list) {
//			ChannelLineVO VO = new ChannelLineVO(detail);
//			companyLinesVOs.add(VO);
//		}
//	}


	public MonitorSystemVedioVO(TblMonitorSystemEntity monitor, Integer channelId) { 
		this.monitorSystemId = monitor.getMonitorId();
		this.title = monitor.getName();
		if(StringUtil.isNotEmpty(monitor.getEasy_vedio_url())) {
			this.easy_vedio_url = monitor.getEasy_vedio_url();
			this.snap_url = monitor.getSnap_url();
		}
		List<LineDetail>  list = getLinesByMonitorIdAndChannelId(monitor.getMonitorId(),channelId);
		for(LineDetail detail : list) {
			ChannelLineVedioVO VO = new ChannelLineVedioVO(detail);
			//companyLinesVOs.add(VO);
		}
	}

	private List<LineDetail> getLinesByMonitorIdAndChannelId(Integer monitorId, Integer channelId) {
		ILineService lineService = SpringUtil.getBean(ILineService.class);
		TblLinesEntity entity = new TblLinesEntity();
		entity.setMonitorId(monitorId);
		entity.setChannelId(channelId);
		List<LineDetail> all = lineService.getAll(entity); 
		return all;
	}

//	private List<LineDetail> getLines(Integer monitorId) {
//		ILineService lineService = SpringUtil.getBean(ILineService.class);
//		TblLinesEntity entity = new TblLinesEntity();
//		entity.setMonitorId(monitorId);
//		List<LineDetail> all = lineService.getAll(entity); 
//		return all;
//	}

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
