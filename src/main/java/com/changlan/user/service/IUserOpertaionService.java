package com.changlan.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.changlan.common.entity.TblUserOperationEntity;
import com.changlan.user.pojo.UserOperationDetail;
import com.changlan.user.pojo.UserOperationQuery;

public interface IUserOpertaionService {
	
	Object save(TblUserOperationEntity entity);

	Page<UserOperationDetail> findByPage(UserOperationQuery query, Pageable page); 
	 
}
