package com.changlan.point.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.changlan.common.entity.TBLAlarmCategoryEntity;
import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblPointCategoryEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.point.pojo.LineDetail;
import com.changlan.point.service.ILineService;

public class ChannelLineVedioVO {
	
	private Integer lineId;
    private String  title; //名称 
    private String  easy_vedio_url; //视频地址,以逗号分隔
	private List<TblPointsEntity> points = new ArrayList<TblPointsEntity>(); //四级包含多个系统类别

	public ChannelLineVedioVO(LineDetail line) {
		TblLinesEntity entity = line.getLine(); 
		this.lineId = entity.getLineId();
		this.title = entity.getLineName(); 
		//List<TblPointCategoryEntity> pointCategorys = line.getPointCategorys(); 
		Map map = new HashMap();
		if(entity.getLineId()!=null) {
			map.put("lineId", new ParamMatcher(entity.getLineId()));
		}
		ICrudService crudService = SpringUtil.getBean(ICrudService.class);
		List<TblPointsEntity> list = crudService.findByMoreFiled(TblPointsEntity.class, map, true);		
		this.points = list;
		for(TblPointsEntity point : list) {
			/*
			 * if(StringUtil.isNotEmpty(this.easy_vedio_url)) { this.easy_vedio_url =
			 * point.getVideo_url(); break; }
			 */
			if(StringUtil.isNotEmpty(point.getVideo_url())) {
				this.easy_vedio_url = this.easy_vedio_url + "," + point.getVideo_url();
			}
		}
	}
	
	public ChannelLineVedioVO() {
		super();
	}

	public Integer getLineId() {
		return lineId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<TblPointsEntity> getPoints() {
		return points;
	}

	public void setPoints(List<TblPointsEntity> points) {
		this.points = points;
	}

	public String getEasy_vedio_url() {
		return easy_vedio_url;
	}

	public void setEasy_vedio_url(String easy_vedio_url) {
		this.easy_vedio_url = easy_vedio_url;
	}
	
	
}
