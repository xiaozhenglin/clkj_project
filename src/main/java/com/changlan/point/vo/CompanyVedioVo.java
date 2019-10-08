package com.changlan.point.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import com.changlan.common.entity.TblCompanyChannelEntity;
import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.point.pojo.LineDetail;
import com.changlan.point.service.IChannelService;
import com.changlan.point.service.ILineService;

public class CompanyVedioVo {
	
    private Integer companyId;
    private String  title;
    private String  easy_vedio_url; //视频地址,以逗号分隔
    private String  snap_url;  //快照地址
	private List<ChannelVedioVO> ChannelVOS = new ArrayList<ChannelVedioVO>(); //二级包含多条通道信息

	public CompanyVedioVo(TblCompanyEntity company) {
		super();
		this.companyId = company.getCompanyId();
		this.title = company.getName();
		List<TblCompanyChannelEntity> channels =  new ArrayList<TblCompanyChannelEntity>();
		channels =  getChannels(company);
		
		for(TblCompanyChannelEntity entity : channels) {
			ChannelVedioVO vo  = new ChannelVedioVO(entity);
			if(StringUtil.isEmpty(this.easy_vedio_url)) {				
				this.easy_vedio_url = vo.getEasy_vedio_url();
				this.snap_url = vo.getSnap_url();
			}else if(StringUtil.isNotEmpty(vo.getEasy_vedio_url())) {
				this.easy_vedio_url = this.easy_vedio_url + "," + vo.getEasy_vedio_url();
				this.snap_url = this.snap_url + "," + vo.getSnap_url();
			}
			
			if(StringUtil.isEmpty(this.easy_vedio_url)) {
				this.easy_vedio_url = "";
				this.snap_url = "";
			}
			List<MonitorSystemVedioVO> systemList  = vo.getMonitorSystemVOs();
			for(MonitorSystemVedioVO system : systemList) {
				if(StringUtil.isNotEmpty(system.getEasy_vedio_url())) {
					this.easy_vedio_url = this.easy_vedio_url + "," + system.getEasy_vedio_url();
					this.snap_url = this.snap_url + "," + system.getSnap_url();
				}
				Integer monitorId = system.getMonitorSystemId();
				Integer channelId = entity.getChannelId();
				List<LineDetail> lineList  = getLinesByMonitorIdAndChannelId(monitorId,channelId); //system.getCompanyLinesVOs();
				for(LineDetail line : lineList) {
					Map map = new HashMap();
					if(line.getLine().getLineId()!=null) {
						map.put("lineId", new ParamMatcher(line.getLine().getLineId()));
					}
					ICrudService crudService = SpringUtil.getBean(ICrudService.class);
					List<TblPointsEntity> points = crudService.findByMoreFiled(TblPointsEntity.class, map, true);		
					for(TblPointsEntity point : points) {
						if(StringUtil.isNotEmpty(this.easy_vedio_url)){
							if(StringUtil.isNotEmpty(point.getVideo_url())) {
								this.easy_vedio_url = this.easy_vedio_url + "," + point.getVideo_url();
								this.snap_url = this.snap_url + "," + point.getSnap_url();
							}
						}else {
							if(StringUtil.isNotEmpty(point.getVideo_url())) {
								this.easy_vedio_url =  point.getVideo_url();
								this.snap_url =  point.getSnap_url();
							}
						}
					}
				}
			}
			//List<TblPointsEntity> list = vo.getMonitorSystemVOs()
			
			ChannelVOS.add(vo);
		}
//		this.channelCount = ChannelVOS.size();
	}

	private List<TblCompanyChannelEntity> getChannels(TblCompanyEntity company) {
		IChannelService channelService = SpringUtil.getBean(IChannelService.class);
		TblCompanyChannelEntity entity = new TblCompanyChannelEntity();
		entity.setCompanyId(company.getCompanyId()); 
		return channelService.getAllChannel(entity);
	}
	
	private List<LineDetail> getLinesByMonitorIdAndChannelId(Integer monitorId,Integer channelId) {
		ILineService lineService = SpringUtil.getBean(ILineService.class);
		TblLinesEntity entity = new TblLinesEntity();
		entity.setMonitorId(monitorId);
		entity.setChannelId(channelId);
		List<LineDetail> all = lineService.getAll(entity); 
		return all;
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

	public List<ChannelVedioVO> getChannelVOS() {
		return ChannelVOS;
	}

	public void setChannelVOS(List<ChannelVedioVO> channelVOS) {
		ChannelVOS = channelVOS;
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
