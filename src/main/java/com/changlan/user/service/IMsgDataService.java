package com.changlan.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.changlan.common.entity.TblMsgDataEntity;
import com.changlan.user.pojo.MsgDataDetail;

public interface IMsgDataService {

	Page<MsgDataDetail> getAllByPage(TblMsgDataEntity data, Pageable page);
	
	
}
