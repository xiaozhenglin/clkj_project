package com.changlan.command.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.ListUI;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
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
import com.changlan.common.entity.TblTemperatureDataEntity;
import com.changlan.common.pojo.MatcheType;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.AnalysisDataUtil;
import com.changlan.common.util.CRC16M;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.netty.controller.NettyController;
import com.changlan.netty.service.INettyService;
import com.changlan.netty.service.NettyServiceImpl;
import com.changlan.other.entity.DeviceData;
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
	
	@Autowired
	private INettyService nettyService;

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
	public List<Object> anylysisData(CommandRecordDetail recordDetail) {
		List<Object> result = new ArrayList<Object>();
//		List<TblPoinDataEntity> currentAndVoltage = new ArrayList<>(); 
//		List<TblTemperatureDataEntity> temperature = new ArrayList<>(); 
		//指令类别
		TblCommandCategoryEntity category = recordDetail.getCategory();
		//监控点信息
	  	TblPointsEntity point = recordDetail.getPoint();
	  	//记录信息
	  	TblCommandRecordEntity record = recordDetail.getRecord();
	  	List<ProtocolInfo> currentDataProtocol = recordDetail.getCurrentDataProtocol();  
		//逻辑：获取返回内容 -》 获取解析规则 -》解析结果 -》保存入库 -》返回保存的数据
	  	if(!ListUtil.isEmpty(currentDataProtocol)) {
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
	        			String categoryNmae = category.getCategoryNmae();
	        			if(categoryNmae.indexOf("温度")>-1) {
	        				if(data.size()>1) {
	        					//BigDecimal temList =
	        					//String temList = StringUtils.join(data.toArray(),",");
	        					for(int j = 0 ;j < data.size() ; j++) {
		        					TblTemperatureDataEntity value = saveTemperatureData(data.get(j).toString(),point,protocol,new Integer(j)); 
		        					result.add(value);
	        					}
	        				}else {
		        				TblTemperatureDataEntity value = saveTemperatureData(bigDecimal.toString(),point,protocol,Integer.parseInt("0")); 
		        				result.add(value);
	        				}
	        			}else {
	        				TblPoinDataEntity saveData = saveData(bigDecimal.toString(),point,protocol); 
	        				result.add(saveData);
	        			}
	        		}
	    		}
	    	}
	  	}
	  	if(category.getCategoryNmae().indexOf("相位")>-1){
	  		return savePartialDischarge(point,record);
	  	}
	  	if(category.getCategoryNmae().indexOf("局放频次采集")>-1){
	  		return savePartialDischargeCommand(point,record,recordDetail.getCommandDefault());
	  	}
		return result;
	}


	//表格参数一样的，是为了分表存储温度和电流的数据   6.27 增加 记录 所在距离的温度
	private TblTemperatureDataEntity saveTemperatureData(String value, TblPointsEntity point,TblCommandProtocolEntity protocol,Integer distinct) {
		TblTemperatureDataEntity entity = new TblTemperatureDataEntity(); 
		entity.setPointId(point.getPointId()); 
		entity.setPointName(point.getPointName());
		entity.setIndicatorId(protocol.getIndicatorId());  
		entity.setValue(value); 
		entity.setRecordTime(new Date()); 
		entity.setProtocolId(protocol.getProtocolId());  //数据来源协议id
		TblIndicatorValueEntity indicator = (TblIndicatorValueEntity)crudService.get(protocol.getIndicatorId(), TblIndicatorValueEntity.class, true);
		entity.setCategroryId(indicator.getCategoryId()); //指标类别
		entity.setPointCatagoryId(point.getPointCatagoryId()); //监控系统类别
		entity.setIsAlarm(0); 
		entity.setIsEarlyWarning(0); 
		entity.setRangeSize(distinct);
		crudService.save(entity, true);
		return entity;
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
	
	/**
	 * @param point
	 * @param record
	 * @return 保存局放数据 devicedata
	 *  
	 */
	private List<Object> savePartialDischarge(TblPointsEntity point, TblCommandRecordEntity record) {
		List<Object> result = new ArrayList<Object>();
		
		String substring = record.getBackContent().substring(10);
		System.out.println(substring); 
		int i = 0 ;
		while(i<=substring.length()-8) {
			DeviceData data = new DeviceData();
			String amplitude = substring.substring(i, i+4);
			amplitude = StringUtil.decimalConvert(amplitude, 16, 10, null); 
			System.out.println("幅值"+amplitude); 
			String phase = substring.substring(i+4, i+8);
			phase = StringUtil.decimalConvert(phase, 16, 10, null); 
			System.out.println("相位"+phase); 
			data.setChannelSettings_id(14);
			
			data.setAmplitude(Float.parseFloat(amplitude));//幅值
			data.setPhase(Float.parseFloat(phase));//相位
			data.setCreatetime(new Date()); 
			data.setPointId(point.getPointId()); 
			crudService.update(data, true);
			result.add(data);
	
			i= i+8;
		}
		return result;
	}
	

	/**
	 * 根据频次计算 需要发送的采集指令 , 保存指令并发送
	 * @param point
	 * @param record
	 * @param sendCommand 
	 * @return
	 */
	private List<Object> savePartialDischargeCommand(TblPointsEntity point, TblCommandRecordEntity record, TblPointSendCommandEntity sendCommand) {
		List<Object> result = new ArrayList<Object>();
		String backContent = record.getBackContent();
		backContent = backContent.substring(6,backContent.length()-4);
		System.out.println(backContent); 
		int i = 0 ;
		while(i<=backContent.length()-4) {
			String frequency = backContent.substring(i, i+4);
			frequency = StringUtil.decimalConvert(frequency, 16, 10, null); 
			System.out.println("频次"+frequency); 
			Integer pinci = Integer.parseInt(frequency)*2;
			
			//一次最多采集122个，所以要分批次采集
			if(pinci >0 && pinci <= 122) {
				String command = "0114070600010000" +  StringUtil.decimalConvert(pinci.toString(), 10, 16, 4) + "4507" ; 
				//计算crc校验 的结果
				byte[] sbuf = CRC16M.getSendBuf(command.substring(0,command.length()-4));
				String trim = CRC16M.getBufHexStr(sbuf).trim();
				saveAndSend(point, trim); 
				try {
					Thread.sleep(3000); //第二条发的时候间隔是3秒钟 ，防止收不到消息
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else if(pinci >122 && pinci <= 244) {
				String trim  ="0114070600010000007A4507";
				saveAndSend(point,trim); //0-121的数据 也就是122个
				try {
					Thread.sleep(3000); //第二条发的时候间隔是3秒钟 ，防止收不到消息
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Integer more = 244-pinci;
				String command2 = "011407060001007B" +  StringUtil.decimalConvert(more.toString(), 10, 16, 4) + "4507" ; 
				//计算crc校验 的结果
				byte[] sbuf2 = CRC16M.getSendBuf(command2.substring(0,command2.length()-4));
				String trim2= CRC16M.getBufHexStr(sbuf2).trim();
				saveAndSend(point,trim2);
			}
			i = i+4;
		}
		return null;
	}
	
	private void saveAndSend(TblPointsEntity point, String sendContent) { 
		TblPointSendCommandEntity data = new TblPointSendCommandEntity();
		data.setCommandCatagoryId(7);
		data.setCommandContent(sendContent);
		data.setCommandName("获取幅值相位");
		data.setPointId(point.getPointId()); 
		TblPointSendCommandEntity update = (TblPointSendCommandEntity)crudService.update(data, true);
		System.out.println(update.getSendCommandId());
		
		//保存记录 并加锁
		TblCommandRecordEntity record = updateServerRecord(update,point.getPointRegistPackage()); 
		//执行发送
		try {
			nettyService.serverSendMessage(point.getPointRegistPackage(), update.getCommandContent());
		} catch (Exception e) {
			e.printStackTrace();
		} 
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
	public TblCommandRecordEntity updateServerRecord(TblPointSendCommandEntity commandDefault,String registPackage) {
		//保存用户操作指令
		TblCommandRecordEntity entity = new TblCommandRecordEntity();
		entity.setPointId(commandDefault.getPointId()); //设置监控点id
//		TblAdminUserEntity currentUser = LoginUser.getCurrentUser(); 
//		if(currentUser!=null) {
//			entity.setAdminUserId(LoginUser.getCurrentUser().getAdminUserId());//记录操作人
//		}
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

//	private String getRegistPackage(Integer pointId) {
//		TblPointsEntity pointDefine = pointDefineService.getByRegistPackageOrId(pointId,null);
//		String pointRegistPackage = pointDefine.getPointRegistPackage(); 
//		return pointRegistPackage;
//	}

	@Override
	@Transactional
	public TblCommandRecordEntity updateClientRecord(TblPointSendCommandEntity commandDefault, String ip) {
		//保存用户操作指令
		TblCommandRecordEntity entity = new TblCommandRecordEntity();
		entity.setPointId(commandDefault.getPointId()); //设置监控点id
		entity.setCommandCatagoryId(commandDefault.getCommandCatagoryId());  //指令类别
		entity.setSendCommandId(commandDefault.getSendCommandId()); //发送的默认指令Id
		entity.setCommandContent(commandDefault.getCommandContent());//发送内容
		entity.setRecordTime(new Date()); //记录时间
		//将记录id保存到会话，当有返回消息时保存起来
		TblCommandRecordEntity update = (TblCommandRecordEntity)crudService.update(entity, true); 
		logger.info("第二步发送指令 ip：" +ip+ "指令内容："+commandDefault.getCommandContent() + "操作记录commandRecordId " + update.getCommandRecordId());
		if(update != null) {
			//加锁,一个监控点同时只能发送一个指令，接受指令就会解锁
			NettyController.map.put(ip, update.getCommandRecordId());
		}
		return update;
	}

//	@Override
//	public List<TblTemplateDataEntity> anylysisTemplateData(CommandRecordDetail commandRecordDetail) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	

}
