package com.changlan.user.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.changlan.common.entity.TblUserOperationEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.user.service.IUserOpertaionService;

@Service
public class UserOperationServiceImpl implements IUserOpertaionService{

	@Autowired
	ICrudService crudService;

	@Override
	@Transactional
	public Object save(TblUserOperationEntity entity) {
		Object update = crudService.update(entity, true);
		return update==null? false : update; 
	}

	@Override
	public Page<TblUserOperationEntity> findByPage(Pageable page) {
		return null;
	}
	
}
