package com.changlan.command.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.changlan.command.pojo.CommandRecordDetail;
import com.changlan.common.entity.TblCommandRecordEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblPointSendCommandEntity;

public interface ICommandRecordService {
	List<CommandRecordDetail> getList(Integer recordId,String registPackage,String backContent);

	CommandRecordDetail getLastOneResult(String registPackage, String receiveMessage);

	//解析数据
	List<TblPoinDataEntity> anylysisData(CommandRecordDetail commandRecordDetail);

	Page<CommandRecordDetail> getPage(Integer recordId, String registPackage, String backContent, Pageable page);

	void update(TblPointSendCommandEntity commandDefault);  
	
}
