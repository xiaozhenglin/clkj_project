package com.changlan.command.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.changlan.command.pojo.CommandRecordDetail;
import com.changlan.common.entity.TblCommandRecordEntity;
import com.changlan.common.entity.TblPoinDataEntity;

public interface ICommandRecordService {
	List<CommandRecordDetail> getList(Integer recordId,String registPackage,String backContent);

	CommandRecordDetail getOneResult(String registPackage, String receiveMessage);

	List<TblPoinDataEntity> anylysisData(CommandRecordDetail commandRecordDetail);

	Page<CommandRecordDetail> getPage(Integer recordId, String registPackage, String backContent, Pageable page); 

}
