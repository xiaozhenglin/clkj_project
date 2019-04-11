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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.changlan.command.dao.ICommandRecordDao;
import com.changlan.command.pojo.CommandProtolDetail;
import com.changlan.command.pojo.CommandRecordDetail;
import com.changlan.command.pojo.ProtocolInfo;
import com.changlan.command.service.ICommandDefaultService;
import com.changlan.command.service.ICommandRecordService;
import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblAdminUserEntity;
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
import com.changlan.netty.controller.NettyController;
import com.changlan.point.service.IPointDefineService;
import com.changlan.user.pojo.LoginUser;

@Service
public class CommandRecordServiceImpl implements ICommandRecordService{
	
	private static Logger logger = LoggerFactory.getLogger(CommandRecordServiceImpl.class);
	
	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private ICommandRecordDao commandRecordDao;
	
	@Autowired
	private IPointDefineService pointDefineService;

	@Override
	public List<CommandRecordDetail> getList(Integer recordId, String registPackage, String backContent) {
		List<CommandRecordDetail> result = new ArrayList<CommandRecordDetail>(); 
		List<Object> all = new ArrayList<Object>();
		Map map = new HashMap();
		if(!StringUtil.isEmpty(backContent)) {
			map.put("backContent", new ParamMatcher(backContent));
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
	public CommandRecordDetail getLastOneResult(String registPackage, String receiveMessage) {
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
	  	List<ProtocolInfo> currentDataProtocol = recordDetail.getCurrentDataProtocol();  
		//逻辑：获取返回内容 -》 获取解析规则 -》解析结果 -》保存入库 -》返回保存的数据
    	for(ProtocolInfo protocolInfo : currentDataProtocol) {
    		TblCommandProtocolEntity protocol = protocolInfo.getProtocol(); 
    		if(protocol == null || protocol.getPointId()!=record.getPointId() ) {
        		return null;
        	}
    		String backContent = record.getBackContent();
    		String reciveAddressCode = backContent.substring(0,0+2);
    		String addressCode = protocol.getAddressCode(); 
    		//地址码匹配
    		if(StringUtil.isEmpty(addressCode)||(StringUtil.isNotEmpty(addressCode) && protocol.getAddressCode().equals(reciveAddressCode)) ) {
    			//解析数据
        		List<BigDecimal> data = AnalysisDataUtil.getData(record.getBackContent(),protocol); 
        		if(!ListUtil.isEmpty(data) ) {
        			//一个监控点的具体指标的值
        			BigDecimal bigDecimal = data.get(0);
        			int compareTo = bigDecimal.compareTo(BigDecimal.ZERO); 
        			//是否不是负值
        			Integer notNegative = protocol.getNotNegative(); 
        			if(notNegative!=null && notNegative>=1&& compareTo == -1) {
        				bigDecimal = BigDecimal.ZERO;
        			}
        			TblPoinDataEntity saveData = saveData(bigDecimal.toString(),point,protocol); 
        			result.add(saveData);
        		}
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
		entity.setProtocolId(protocol.getProtocolId());  //数据来源协议id
		TblIndicatorValueEntity indicator = (TblIndicatorValueEntity)crudService.get(protocol.getIndicatorId(), TblIndicatorValueEntity.class, true);
		entity.setCategroryId(indicator.getCategoryId()); //指标类别
		entity.setPointCatagoryId(point.getPointCatagoryId()); //监控系统类别
//		entity.setAlarmDataId(("0"));
//		entity.setElarlyAlamDataId("0");
		entity.setIsAlarm(0); 
		entity.setIsEarlyWarning(0);  
		crudService.save(entity, true);
		return entity;
	}

	@Override
	public Page<CommandRecordDetail> getPage(TblCommandRecordEntity commandRecord,Pageable page) {
		Map map = new HashMap();
		if(commandRecord!=null) {
			if(!StringUtil.isEmpty(commandRecord.getBackContent())) {
				map.put("backContent", new ParamMatcher(MatcheType.LIKE,commandRecord.getBackContent()));
			}
			if(commandRecord.getCommandRecordId() != null) {
				map.put("commandRecordId", new ParamMatcher(commandRecord.getCommandRecordId()));
			} 
			if(commandRecord.getPointId() != null) {
				map.put("pointId", new ParamMatcher(commandRecord.getPointId()));
			}
			if(commandRecord.getAdminUserId() != null) {
				map.put("adminUserId", new ParamMatcher(commandRecord.getAdminUserId()));
			}
			if(commandRecord.getCommandCatagoryId() != null) {
				map.put("commandCatagoryId", new ParamMatcher(commandRecord.getCommandCatagoryId()));
			}
		}
		Page all = crudService.findByMoreFiledAndPage(TblCommandRecordEntity.class, map, true,page);
		
		List<CommandRecordDetail> result = new ArrayList<CommandRecordDetail>(); 
		for(Object o : all) {
			TblCommandRecordEntity record = (TblCommandRecordEntity)o;
			TblPointSendCommandEntity commandDefault = (TblPointSendCommandEntity)crudService.get(record.getSendCommandId(), TblPointSendCommandEntity.class, true);
			CommandRecordDetail detail = new CommandRecordDetail(record,commandDefault);
			result.add(detail);
		}
		return new PageImpl<CommandRecordDetail>(result, page, all.getTotalElements());
	}

	@Override
	@Transactional
	public TblCommandRecordEntity update(TblPointSendCommandEntity commandDefault,String registPackage) {
		//保存用户操作指令
		TblCommandRecordEntity entity = new TblCommandRecordEntity();
		entity.setPointId(commandDefault.getPointId()); //设置监控点id
		TblAdminUserEntity currentUser = LoginUser.getCurrentUser(); 
		if(currentUser!=null) {
			entity.setAdminUserId(LoginUser.getCurrentUser().getAdminUserId());//记录操作人
		}
		entity.setCommandCatagoryId(commandDefault.getCommandCatagoryId());  //指令类别
		entity.setSendCommandId(commandDefault.getSendCommandId()); //发送的默认指令Id
		entity.setCommandContent(commandDefault.getCommandContent());//发送内容
		entity.setRecordTime(new Date()); //记录时间
		//将记录id保存到会话，当有返回消息时保存起来
		TblCommandRecordEntity update = (TblCommandRecordEntity)crudService.update(entity, true); 
//		String registPackage = getRegistPackage(entity.getPointId());
		logger.info("第二步发送指令 注册包：registPackage：" +registPackage+ "指令内容："+commandDefault.getCommandContent() + "操作记录commandRecordId " + update.getCommandRecordId());
		if(update != null) {
			//加锁,一个监控点同时只能发送一个指令，接受指令就会解锁
			NettyController.map.put(registPackage, update.getCommandRecordId());
		}
		return update;
	}

	private String getRegistPackage(Integer pointId) {
		TblPointsEntity pointDefine = pointDefineService.getByRegistPackageOrId(pointId,null);
		String pointRegistPackage = pointDefine.getPointRegistPackage(); 
		return pointRegistPackage;
	}
	
	

}
