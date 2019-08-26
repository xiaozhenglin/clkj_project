package com.changlan.point.vo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import com.changlan.common.entity.TblCompanyChannelEntity;
import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblPointsEntity;
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
			}
			if(StringUtil.isNotEmpty(vo.getEasy_vedio_url())) {
				this.easy_vedio_url = this.easy_vedio_url + "," + vo.getEasy_vedio_url();
			}
			List<MonitorSystemVedioVO> systemList  = vo.getMonitorSystemVOs();
			for(MonitorSystemVedioVO system : systemList) {
				if(StringUtil.isNotEmpty(system.getEasy_vedio_url())) {
					this.easy_vedio_url = this.easy_vedio_url + "," + system.getEasy_vedio_url();
				}
				List<ChannelLineVedioVO> channelList  = system.getCompanyLinesVOs();
				for(ChannelLineVedioVO channel : channelList) {
					this.easy_vedio_url = this.easy_vedio_url + "," + channel.getEasy_vedio_url();
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
	    
}
