package com.changlan.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.changlan.common.entity.TblUserOperationEntity;

public interface IUserOpertaionService {
	
	Object save(TblUserOperationEntity entity);

	Page<Object> findByPage(Pageable page); 
	
}
