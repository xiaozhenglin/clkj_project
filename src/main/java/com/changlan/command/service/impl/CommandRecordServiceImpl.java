package com.changlan.command.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.ListUI;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changlan.command.dao.ICommandRecordDao;
import com.changlan.command.pojo.CommandProtolDetail;
import com.changlan.command.pojo.CommandRecordDetail;
import com.changlan.command.service.ICommandDefaultService;
import com.changlan.command.service.ICommandRecordService;
import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblCommandCategoryEntity;
import com.changlan.common.entity.TblCommandProtocolEntity;
import com.changlan.common.entity.TblCommandRecordEntity;
import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblPointSendCommandEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.MatcheType;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.AnalysisDataUtil;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;

@Service
public class CommandRecordServiceImpl implements ICommandRecordService{
	
	private static Logger log = LoggerFactory.getLogger(CommandRecordServiceImpl.class);
	@Autowired
	ICrudService crudService;
	
	@Autowired
	ICommandRecordDao commandRecordDao;

	@Override
	public List<CommandRecordDetail> getList(Integer recordId, String registPackage, String backContent) {
		List<CommandRecordDetail> result = new ArrayList<CommandRecordDetail>(); 
		List<Object> all = new ArrayList<Object>();
		Map map = new HashMap();
		if(!StringUtil.isEmpty(registPackage)) {
			map.put("pointRegistPackage", new ParamMatcher(registPackage)); 
		}
		if(!StringUtil.isEmpty(backContent)) {
			map.put("backContent", new ParamMatcher(MatcheType.LIKE,backContent));
		}
		if(recordId != null) {
			map.put("commandRecordId", new ParamMatcher(recordId));
		} 
		all = crudService.findByMoreFiled(TblCommandRecordEntity.class, map, true);
		for(Object o : all) {
			TblCommandRecordEntity record = (TblCommandRecordEntity)o;
			TblPointSendCommandEntity commandDefault = (TblPointSendCommandEntity)crudService.get(record.getSendCommandId(), TblPointSendCommandEntity.class, true);
			CommandRecordDetail detail = new CommandRecordDetail(record,commandDefault);
			result.add(detail);
		}
		return result;
	}

	@Override
	public CommandRecordDetail getOneResult(String registPackage, String receiveMessage) {
		TblCommandRecordEntity record = commandRecordDao.getOneRecordOrderByTime(registPackage,receiveMessage);
		TblPointSendCommandEntity commandDefault = (TblPointSendCommandEntity)crudService.get(record.getSendCommandId(), TblPointSendCommandEntity.class, true);
		CommandRecordDetail detail = new CommandRecordDetail(record, commandDefault);
		return detail;
	}

	@Override
	@Transactional
	public List<TblPoinDataEntity> anylysisData(CommandRecordDetail recordDetail) {
		List<TblPoinDataEntity> result = new ArrayList<>(); 
		//监控点信息
	  	TblPointsEntity point = recordDetail.getPoint();
	  	//记录信息
	  	TblCommandRecordEntity record = recordDetail.getRecord();
	  	List<TblCommandProtocolEntity> currentDataProtocol = recordDetail.getCurrentDataProtocol();  
		//逻辑：获取返回内容-》 获取其中匹配的解析协议 -》 获取解析规则 -》解析结果 -》保存入库 -》返回保存的数据
    	for(TblCommandProtocolEntity protocol : currentDataProtocol) {
    		if(protocol == null || !protocol.getPointRegistPackage().equalsIgnoreCase(record.getPointRegistPackage())) {
        		return null;
        	}
    		//解析数据
    		List<BigDecimal> data = AnalysisDataUtil.getData(record.getBackContent(),protocol); 
    		if(!ListUtil.isEmpty(data)) {
    			//一个监控点的具体指标的值
    			TblPoinDataEntity saveData = saveData(data.get(0).toString(),point,protocol); 
    			result.add(saveData);
    		}
    	}
		return result;
	}

	private TblPoinDataEntity saveData(String value, TblPointsEntity point, TblCommandProtocolEntity protocol) {
		TblPoinDataEntity entity = new TblPoinDataEntity(); 
		entity.setPointId(point.getPointId());
		entity.setPointName(point.getPointName());
		entity.setIndicatorId(protocol.getIndicatorId());  
		entity.setValue(value); 
		entity.setRecordTime(new Date()); 
		entity.setProtocolId(protocol.getProtocolId());  
		TblIndicatorValueEntity indicator = (TblIndicatorValueEntity)crudService.get(protocol.getIndicatorId(), TblIndicatorValueEntity.class, true);
		entity.setCategroryId(indicator.getCategoryId());
		crudService.save(entity, true);
		return entity;
		
	}

	
	

}
