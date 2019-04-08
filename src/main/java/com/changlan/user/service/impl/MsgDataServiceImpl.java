package com.changlan.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.changlan.common.entity.TblMsgDataEntity;
import com.changlan.common.pojo.MatcheType;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.StringUtil;
import com.changlan.user.pojo.MsgDataDetail;
import com.changlan.user.service.IMsgDataService;

@Service
public class MsgDataServiceImpl implements IMsgDataService{
	
	@Autowired
	private ICrudService crudService;
	
	@Override
	public Page<MsgDataDetail> getAllByPage(TblMsgDataEntity data, Pageable page) {
		List<MsgDataDetail> content = new ArrayList<MsgDataDetail>();
		Map map = new HashMap();
		if(data.getMsgId()!=null) {
			map.put("msgId", new ParamMatcher(data.getMsgId())); 
		}
		if(data.getAdminUserId()!=null) {
			map.put("adminUserId", new ParamMatcher(data.getAdminUserId())); 
		}
		if(data.getDirection()!=null) {
			map.put("direction", new ParamMatcher(data.getDirection())); 
		}
		if(StringUtil.isNotEmpty(data.getMsgType())) { 
			map.put("msgType", new ParamMatcher(data.getMsgType())); 
		}
		if(StringUtil.isNotEmpty(data.getContent())) { 
			map.put("content", new ParamMatcher(MatcheType.LIKE,data.getContent())); 
		}
		
		Page findByMoreFiledAndPage = crudService.findByMoreFiledAndPage(TblMsgDataEntity.class, map, true, page);
		for(Object o : findByMoreFiledAndPage) {
			TblMsgDataEntity entity = (TblMsgDataEntity)o;
			MsgDataDetail detail = new MsgDataDetail(entity);
			content.add(detail);
		}
		return new PageImpl<MsgDataDetail>(content,page,findByMoreFiledAndPage.getTotalElements());
	}

}
