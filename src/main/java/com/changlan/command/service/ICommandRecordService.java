package com.changlan.command.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.changlan.command.pojo.CommandRecordDetail;
import com.changlan.common.entity.TblCommandRecordEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblPointSendCommandEntity;

public interface ICommandRecordService {
	//获取发送记录列表
	List<CommandRecordDetail> getList(Integer recordId,String registPackage,String backContent);

	//分页查询
	Page<CommandRecordDetail> getPage(TblCommandRecordEntity record,Pageable page);   
	
	//获取上一次的记录
	CommandRecordDetail getLastOneResult(String registPackage, String receiveMessage);

	//解析电流、电压数据
	//解析温度数据
	List<Object> anylysisData(CommandRecordDetail recordDetail); 

	//保存服务器往客户端发送记录
	TblCommandRecordEntity updateServerRecord(TblPointSendCommandEntity commandDefault,String registPackage);

	//保存客户端往服务器发送记录
	TblCommandRecordEntity updateClientRecord(TblPointSendCommandEntity commandDefault, String ip);




	
}
