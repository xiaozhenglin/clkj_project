package com.changlan.user.service;

import java.util.List;

import com.changlan.common.entity.TblUserGroupEntity;

public interface IUserGoupService {

	Boolean existName(TblUserGroupEntity userGroup);

	List<TblUserGroupEntity> getAll(TblUserGroupEntity userGroup); 

}
