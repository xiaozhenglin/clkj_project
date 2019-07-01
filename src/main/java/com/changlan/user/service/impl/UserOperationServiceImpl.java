package com.changlan.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblUserOperationEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.StringUtil;
import com.changlan.user.pojo.UserDetail;
import com.changlan.user.pojo.UserOperationDetail;
import com.changlan.user.pojo.UserOperationQuery;
import com.changlan.user.service.IUserInfoService;
import com.changlan.user.service.IUserOpertaionService;

@Service
public class UserOperationServiceImpl implements IUserOpertaionService{

	@Autowired
	ICrudService crudService;
	
	@Autowired
	IUserInfoService userInfoService;

	@Override
	@Transactional
	public Object save(TblUserOperationEntity entity) {
		Object update = crudService.update(entity, true);
		return update==null? false : update; 
	}

	@Override
	public Page<UserOperationDetail> findByPage(UserOperationQuery query,Pageable page) {
		Map map = new HashMap();
		if(query!=null) {
			if(StringUtil.isNotEmpty(query.getUserId())) {
				map.put("userId", new ParamMatcher(query.getUserId()));
			}
			if(query.getUserOperationId()!=null) {
				map.put("userOperationId", new ParamMatcher(query.getUserOperationId()));
			}
			if(query.getBegin()!=null && query.getEnd()!=null) {
				map.put("recordTime", new ParamMatcher(query.getBegin(),query.getEnd()));
			}
		}
		Page<Object> findByMoreFiledAndPage = crudService.findByMoreFiledAndPage(TblUserOperationEntity.class, map, true, page);
		//封装数据
		List<UserOperationDetail> result = new ArrayList<UserOperationDetail>();
		
		for(Object object : findByMoreFiledAndPage) {
			TblUserOperationEntity entity = (TblUserOperationEntity)object;
			UserOperationDetail detail = new UserOperationDetail(entity);
			result.add(detail);
		}
		return new PageImpl(result, page, findByMoreFiledAndPage.getTotalElements());  
	}
	
}
