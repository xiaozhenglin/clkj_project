package com.changlan.command.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpSession;
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
import com.changlan.command.pojo.ContainSendCommands;
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
import com.changlan.common.entity.TblSystemVarEntity;
import com.changlan.common.entity.TblTemperatureDTSDataEntity;
import com.changlan.common.entity.TblTemperatureDataEntity;
import com.changlan.common.pojo.MatcheType;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.AnalysisDataUtil;
import com.changlan.common.util.CRC16M;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.SessionUtil;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.netty.controller.NettyController;
import com.changlan.netty.service.INettyService;
import com.changlan.netty.service.NettyServiceImpl;
import com.changlan.other.entity.DeviceData;
import com.changlan.other.entity.DeviceDataColl;
import com.changlan.other.entity.DeviceDataSpecial;
import com.changlan.point.pojo.PointStatus;
import com.changlan.point.service.IPointDefineService;
import com.changlan.user.constrant.UserModuleConst;
import com.changlan.user.pojo.LoginUser;
import com.changlan.user.pojo.UserErrorType;

@Service
public class CommandRecordServiceImpl implements ICommandRecordService{
	
	private static Logger logger = LoggerFactory.getLogger(CommandRecordServiceImpl.class);
	public static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
	
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
	public List<Object> anylysisData(CommandRecordDetail recordDetail)  {
		List<Object> result = new ArrayList<Object>();
//		List<TblPoinDataEntity> currentAndVoltage = new ArrayList<>(); 
//		List<TblTemperatureDataEntity> temperature = new ArrayList<>(); 
		//指令类别
		TblCommandCategoryEntity category = recordDetail.getCategory();
		//监控点信息
	  	TblPointsEntity point = recordDetail.getPoint();
	  	TblPointsEntity pointUpdate = (TblPointsEntity) crudService.get(recordDetail.getPoint().getPointId(), TblPointsEntity.class, true);
	  	pointUpdate.setStatus(PointStatus.CONNECT.toString());
	  	crudService.update(pointUpdate,true);
	  	//记录信息
	  	TblCommandRecordEntity record = recordDetail.getRecord();
	  	List<ProtocolInfo> currentDataProtocol = recordDetail.getCurrentDataProtocol(); 
	  	DeviceData  deviceDataValue = new DeviceData();
		//逻辑：获取返回内容 -》 获取解析规则 -》解析结果 -》保存入库 -》返回保存的数据
	  	if(!ListUtil.isEmpty(currentDataProtocol)) {
	  		if(category.getCategoryNmae().indexOf("相位")>-1){
		  		return savePartialDischarge(point,record,deviceDataValue,currentDataProtocol);
		  	}
	  		if(category.getCategoryNmae().indexOf("局放全采集")>-1) {
		  		try {
					return  saveDeviceDataTotal(point,record,deviceDataValue,currentDataProtocol);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
	  		
	  		for(ProtocolInfo protocolInfo : currentDataProtocol) {
	    		TblCommandProtocolEntity protocol = protocolInfo.getProtocol(); 
	    		if(protocol == null || protocol.getPointId()!=record.getPointId() ) {
	        		return null;
	        	}
	    		String backContent = record.getBackContent();
	    		String reciveAddressCode = backContent.substring(0,0+2);
	    		String addressCode = protocol.getAddressCode(); 
	    		TblPointSendCommandEntity commandDefault = (TblPointSendCommandEntity)crudService.get(record.getSendCommandId(), TblPointSendCommandEntity.class, true);
	    		String isControl = commandDefault.getIs_controller();
	    		logger.info("reciveAddressCode" + reciveAddressCode);
	    		logger.info("addressCode" + addressCode);
	    		logger.info("isControl" + isControl);
	    		//地址码匹配
	    		if(StringUtil.isEmpty(addressCode)||(StringUtil.isNotEmpty(addressCode) && protocol.getAddressCode().equals(reciveAddressCode) && isControl.contentEquals("0")) ) {
	    			//解析数据
	    			List<BigDecimal> data = new ArrayList<BigDecimal>();
	    			if(!(protocol.getDataType().indexOf("相DTS温度")>-1)) {
	    				data = AnalysisDataUtil.getData(record.getBackContent(),protocol); 
	    			}else{
        				return saveDtss(point, record, protocol);
        			}
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
	        			if(categoryNmae.indexOf("频次")>-1) {   //可以得到 频次, 求得多个幅值相位的频次
	        				deviceDataValue = saveDeviceData(data.get(0).toString(),point,protocol,record);
	        				result.add(deviceDataValue);
	        			}
	        			
	        			if(protocol.getDataType().indexOf("断纤")>-1) {
	      	  			  try {
	      					return  saveDtsTotal(point,record,currentDataProtocol);
	      				} catch (InterruptedException e) {
	      					// TODO Auto-generated catch block
	      					e.printStackTrace();
	      				}
	      	  		   }
	        			
	        			
	        			
	        			if(categoryNmae.indexOf("温度")>-1) {
	        				//if(data.size()>1) {
	        					//BigDecimal temList =
	        					//String temList = StringUtils.join(data.toArray(),",");
							/*
							 * BigDecimal temValue = new BigDecimal(0); for(int i = 0; i < data.size() ;
							 * i++) { temValue = temValue.add(data.get(i)); } BigDecimal avgValue =
							 * temValue.divide(new BigDecimal(data.size()),2, RoundingMode.HALF_UP);
							 * TblTemperatureDataEntity aveValue =
							 * saveTemperatureData(avgValue.toString(),point,protocol,Integer.parseInt("0"))
							 * ;
							 * 
							 * for(int j = 0 ;j < data.size() ; j++) { TblTemperatureDTSDataEntity value =
							 * saveTemperatureDtsData(data.get(j).toString(),point,protocol,new
							 * Integer(j),aveValue.getPointDataId()); result.add(value); }
							 */
	        					
	        					
	        				//}else {	        					
		        				TblTemperatureDataEntity value = saveTemperatureData(bigDecimal.toString(),point,protocol,Integer.parseInt("0")); 
		        				result.add(value);
	        				//}
	        			}else if((categoryNmae.indexOf("模拟量")>-1)||((categoryNmae.indexOf("水泵")>-1))){
	        				TblPoinDataEntity saveData = saveData(bigDecimal.toString(),point,protocol); 
	        				result.add(saveData);
	        			}
	        			
	        		}
	    		}
	    	}
	  	}
	  	
	  	TblPointSendCommandEntity sendCommandDetail = recordDetail.getCommandDefault();
	  	if(sendCommandDetail.getIs_controller().contentEquals("1")) {   //若为控制类指令 
	  		 if(record.getCommandContent().substring(0, 2).contentEquals(record.getBackContent().substring(0, 2))) {   //控制指令内容和返回指令内容一致， 说明控制类指令起效果。
	  			sendCommandDetail.setSystem_start("no");  		  //成功控制	
	  		 }else {
	  			sendCommandDetail.setSystem_start("no"); 
	  			String error = sendCommandDetail.getCommandName() + "失败";
	  			result.add(error);
	  			return result; 
	  		 }
	  		TblPointSendCommandEntity update = (TblPointSendCommandEntity)crudService.update(sendCommandDetail, true);
	  	}
	  	
	  	List<ContainSendCommands> containSendCommands = recordDetail.getContainSendCommands();
	  	if(!ListUtil.isEmpty(containSendCommands)) {
	  		for(ContainSendCommands sendCommand : containSendCommands) {
	  			//String protocolId = sendCommand.getProtocolId();
	  			//String sendContent = sendCommand.getCommandContent();
	  			TblPointsEntity tblpoint = (TblPointsEntity)crudService.get(sendCommand.getPointId(), TblPointsEntity.class, true);
	  			TblPointSendCommandEntity  tblPointSendCommand = (TblPointSendCommandEntity)crudService.get(sendCommand.getSendCommandId(), TblPointSendCommandEntity.class, true);
	  			if(!tblPointSendCommand.getSystem_start().contentEquals("yes")) {
	  				saveAndSubSend(tblpoint,tblPointSendCommand,sendCommand);
	  				try {
						Thread.sleep(sendCommand.getIntervalTime());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	  			}else {
	  				//sendCommand.setMsg(sendCommand.getCommandName() + "失败");
	  				try {
						Thread.sleep(sendCommand.getIntervalTime());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	  			}
	  		}
	  	}
	  		  	
		return result;
	}
	
	private void saveAndSubSend(TblPointsEntity point, TblPointSendCommandEntity  data, ContainSendCommands sendCommand) { 
		
		data.setSystem_start("no");
		TblPointSendCommandEntity update = (TblPointSendCommandEntity)crudService.update(data, true);
		//System.out.println(update.getSendCommandId());
		
		//保存记录 并加锁
		TblCommandRecordEntity record = updateServerRecord(update,point.getPointRegistPackage()); 
		//执行发送
		try {
			nettyService.serverSendMessage(point.getPointRegistPackage(), update.getCommandContent());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
    
	private DeviceData saveDeviceData(String value, TblPointsEntity point,TblCommandProtocolEntity protocol,TblCommandRecordEntity record) {
		DeviceData entity = new DeviceData(); 
		entity.setPointId(point.getPointId()); 
		entity.setPointName(point.getPointName());
		//entity.setIndicatorId(protocol.getIndicatorId());  
        String commandContent = record.getCommandContent();
		
        String key = commandContent.substring(6, 8);
        
        if(key.contentEquals("00")||key.contentEquals("01")||key.contentEquals("06")||key.contentEquals("07")||key.contentEquals("0c")||key.contentEquals("0d")) //幅值
        {
        	float fz = Float.parseFloat(value);
        	entity.setAmplitude(fz);
        }else if(key.contentEquals("02")||key.contentEquals("03")||key.contentEquals("08")||key.contentEquals("09")||key.contentEquals("0e")||key.contentEquals("0f"))//频次
        {
        	int pc = Integer.parseInt(value);
        	entity.setFrequency(pc);
        }else {
        	int nl = Integer.parseInt(value);
        	entity.setEnergy(nl);
        }
        
              
        if(key.contentEquals("00")||key.contentEquals("01")||key.contentEquals("02")||key.contentEquals("03")||key.contentEquals("04")||key.contentEquals("05")) //幅值
        {
        	//float fz = Float.parseFloat(value);
        	entity.setPhase_no("A"); //A相
        	SessionUtil.storage.put(point.getPointId() + "phase_no", "A");
        }else if(key.contentEquals("06")||key.contentEquals("07")||key.contentEquals("08")||key.contentEquals("09")||key.contentEquals("0a")||key.contentEquals("0b"))//频次
        {
        	//float pc = Float.parseFloat(value);
        	entity.setPhase_no("B"); //B相
        	SessionUtil.storage.put(point.getPointId() + "phase_no", "B");
        }else {
        	//float nl = Float.parseFloat(value);
        	entity.setPhase_no("C"); //C相
        	SessionUtil.storage.put(point.getPointId() + "phase_no", "C");
        }
        
       // SessionUtil.storage.put(point.getPointId() + "jfTwo", "no");
        
        //System.out.println("saveDeviceData " + SessionUtil.storage.get(point.getPointId() + "phase_no"));
        				
		//crudService.save(entity, true);
		return entity;
	}
	
	//设置相位 
	private void savePhaseNo(String value,TblCommandProtocolEntity protocol) {
				              
        int beginByte = protocol.getBeginByte();
         
	        if(beginByte>6 && beginByte<31) {                           	
	        	SessionUtil.storage.put(protocol.getPointId() + "phase_no", "A");
	        }else if (beginByte>30 && beginByte<55) {
	        	SessionUtil.storage.put(protocol.getPointId() + "phase_no", "B");
	        }else{
	        	SessionUtil.storage.put(protocol.getPointId() +  "phase_no", "C");
	        }        
                       					
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
	
	private TblTemperatureDTSDataEntity saveTemperatureDtsData(String value, TblPointsEntity point,TblCommandProtocolEntity protocol,Integer distinct,Integer refPointDataId) {
		TblTemperatureDTSDataEntity entity = new TblTemperatureDTSDataEntity(); 
		entity.setPointId(point.getPointId()); 
		entity.setRefPointDataId(refPointDataId);
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
	private List<Object> savePartialDischarge(TblPointsEntity point, TblCommandRecordEntity record,DeviceData  deviceData,List<ProtocolInfo> currentDataProtocol) {
		List<Object> result = new ArrayList<Object>();
		
		String substring = record.getBackContent().substring(10);
		 
		String commandContent = record.getCommandContent();
		
		
		//System.out.println(substring); 
		logger.info(substring);
		String substringcopy = substring;
		long curTime = new Date().getTime();
		float maxPhase = 0;
		float maxAmplitude = 0;
		int energy = 0;
		int j = 0;
		int frequencyMax = 0;
		while(j<=substringcopy.length()-8) {
			
			String amplitude = substringcopy.substring(j, j+4);
			amplitude = StringUtil.decimalConvert(amplitude, 16, 10, null); 
			//System.out.println("幅值"+amplitude); 
			String phase = substringcopy.substring(j+4, j+8);
			phase = StringUtil.decimalConvert(phase, 16, 10, null); 
			//System.out.println("相位"+phase); 
			
			if(maxAmplitude < Float.parseFloat(amplitude) ) {  //幅值取最大值
				maxAmplitude = Float.parseFloat(amplitude);
			}
			if(maxPhase < Float.parseFloat(phase) ) {
				maxPhase = Float.parseFloat(phase);
			}
			energy  = energy + Integer.parseInt(amplitude);    //能量为总幅值之和
			frequencyMax++;				    //频次为循环次数
			j= j+8;
			
		}
		
		
		

		
		int i = 0 ;
		while(i<=substring.length()-8) {
			DeviceData data = new DeviceData();
			String amplitude = substring.substring(i, i+4);
			amplitude = StringUtil.decimalConvert(amplitude, 16, 10, null); 
			//System.out.println("幅值"+amplitude); 
			logger.info("幅值"+amplitude);
			String phase = substring.substring(i+4, i+8);
			phase = StringUtil.decimalConvert(phase, 16, 10, null); 
			//System.out.println("相位"+phase); 
			logger.info("相位"+phase);
			data.setChannelSettings_id(14);
			
			data.setAmplitude(Float.parseFloat(amplitude));//幅值
			data.setPhase(Float.parseFloat(phase));//相位
			data.setCreatetime(new Date()); 
			data.setPointId(point.getPointId()); 
			logger.info("接受record_id"+ SessionUtil.storage.get(point.getPointId() + "record_id" + (String)SessionUtil.storage.get(point.getPointId() + "phase_no1")));
			//System.out.println(SessionUtil.storage.get(point.getPointId() + "phase_no"));
			//logger.info("所属相位"+SessionUtil.storage.get(point.getPointId() + "phase_no"));
			//data.setPhase_no((String)SessionUtil.storage.get(point.getPointId() + "phase_no"));
			//data.setRecord_id((String)SessionUtil.storage.get("phase_no") + String.valueOf(curTime));
			if((Integer)SessionUtil.storage.get(point.getPointId() + "record_id" + (String)SessionUtil.storage.get(point.getPointId() + "phase_no1"))!=null){
				data.setRecord_id((Integer)SessionUtil.storage.get(point.getPointId() + "record_id" + (String)SessionUtil.storage.get(point.getPointId() + "phase_no1")));
			}
						
			
			BigDecimal fuzhi = new BigDecimal(amplitude); 
			BigDecimal diejiaXiShu = new BigDecimal(1000); 
			BigDecimal xiangwei = new BigDecimal(phase); 
			
			BigDecimal shang = xiangwei.divideToIntegralValue(diejiaXiShu);
			//BigDecimal shang = xiangwei.divide(diejiaXiShu,2, RoundingMode.HALF_UP);
			//this.quotient = shang.floatValue();
			data.setQuotient(shang.floatValue());
			
			
			BigDecimal yushu = xiangwei.divideAndRemainder(diejiaXiShu)[1];
			//this.Remainder = yushu.floatValue();
			data.setRemainder(yushu.floatValue());
			
			BigDecimal diejiaXiangWei = yushu.divide(diejiaXiShu).multiply(new BigDecimal(360));			
			//this.SuperimposedPhase = diejiaXiangWei.floatValue();
			data.setSuperimposedPhase(diejiaXiangWei.floatValue());
			crudService.update(data, true);
			result.add(data);
	
			i= i+8;
		}
		//SessionUtil.storage.remove("phase_no");
		return result;
	}
	
	private List<Object> saveDtss(TblPointsEntity point, TblCommandRecordEntity record,TblCommandProtocolEntity protocol){
		List<Object> result = new ArrayList<Object>();
		List<BigDecimal> data = new ArrayList<BigDecimal>();
		//if(!(protocol.getDataType().indexOf("线缆温度")>-1)) {
		data = AnalysisDataUtil.getData(record.getBackContent(),protocol);
		//data = getTemperature(record.getBackContent(),protocol); 
		String type = record.getBackContent().substring(12, 14);
		
		if(SessionUtil.storage.get(point.getPointId()  +"ref_record_id" + type)==null) {
			BigDecimal temValue = new BigDecimal(0);
			for(int i = 0; i < data.size() ; i++) {
				temValue = temValue.add(data.get(i));
			}
			BigDecimal avgValue =	temValue.divide(new BigDecimal(data.size()),2, RoundingMode.HALF_UP);
			TblTemperatureDataEntity aveKValue = saveTemperatureData(avgValue.toString(),point,protocol,Integer.parseInt("0")); 
			
			for(int j = 0 ;j < data.size() ; j++) {
				TblTemperatureDTSDataEntity value = saveTemperatureDtsData(data.get(j).toString(),point,protocol,new Integer(j+1),aveKValue.getPointDataId()); 
				result.add(value);
			}
			return result;
		}
		//BigDecimal avgValue =	temValue.divide(new BigDecimal(data.size()),2, RoundingMode.HALF_UP);
		//TblTemperatureDataEntity aveValue = saveTemperatureData(avgValue.toString(),point,protocol,Integer.parseInt("0")); 
		String ref_record_id = (String) SessionUtil.storage.get(point.getPointId()  + "ref_record_id" + type);
		String start_id = (String) SessionUtil.storage.get(point.getPointId() +  "start_id" + type);
		for(int j = 0 ;j < data.size() ; j++) {
			TblTemperatureDTSDataEntity value = saveTemperatureDtsData(data.get(j).toString(),point,protocol,new Integer(j) + Integer.parseInt(start_id),Integer.parseInt(ref_record_id)); 
			result.add(value);
		}
		String sum = (String) SessionUtil.storage.get(point.getPointId()  + "sum_dts" + type);
		int sumInt = Integer.parseInt(sum);
		int startInt = Integer.parseInt(start_id);
		int count = startInt/100;
		if(count == sumInt) {
			Map map = new HashMap();
	    	map.clear();
	 		map.put("pointId", new ParamMatcher(point.getPointId()));
	 		map.put("refPointDataId", new ParamMatcher(Integer.parseInt(ref_record_id)));
	 		TblTemperatureDataEntity temperature  =   (TblTemperatureDataEntity) crudService.get(Integer.parseInt(ref_record_id),TblTemperatureDataEntity.class,true);//得到未更新平均温度的 温度 数据表 
	 		List<TblTemperatureDTSDataEntity> dtsList  =  (List<TblTemperatureDTSDataEntity>) crudService.findByMoreFiled(TblTemperatureDTSDataEntity.class,map,true);
	 		BigDecimal dtsTotal = new BigDecimal(0);
	 		for(TblTemperatureDTSDataEntity dts :dtsList) {
	 			dtsTotal = dtsTotal.add(new BigDecimal(dts.getValue()));
	 		}
	 		BigDecimal avgValue =	dtsTotal.divide(new BigDecimal(dtsList.size()),2, RoundingMode.HALF_UP);
	 		temperature.setValue(avgValue.toString());
	 		crudService.update(temperature, true);
		}
		 
		
		return result;
	}
	
	private List<Object> saveDtsTotal(TblPointsEntity point, TblCommandRecordEntity record,List<ProtocolInfo> currentDataProtocol) throws InterruptedException {
		String commandContent = record.getCommandContent();
		String type = commandContent.substring(12, 14);
		
		List<Object> result = new ArrayList<Object>();
		List<BigDecimal> data = new ArrayList<BigDecimal>();
		for(ProtocolInfo protocolInfo : currentDataProtocol) {
    		TblCommandProtocolEntity protocol = protocolInfo.getProtocol(); 
		    data = AnalysisDataUtil.getData(record.getBackContent(),protocol); 
		    
		    TblTemperatureDataEntity aveValue = saveTemperatureData(data.get(0).toString(),point,protocol,Integer.parseInt("0")); 
		    TblTemperatureDataEntity temperatureData = saveEarlyTemperatureData(point,protocol,0,type);
		    result.add(temperatureData);
		    SessionUtil.storage.put(point.getPointId() + "ref_record_id"+ type , String.valueOf(temperatureData.getPointDataId()));
		    
		    
		    int count = 1;
		    int length = data.get(0).intValue();
		    int duan = 100;
		    int sum = length/duan;
		    SessionUtil.storage.put(point.getPointId()  + "sum_dts"+ type, String.valueOf(sum));
		    Thread[] thread = new Thread[sum+1];
		    for(int i = 0; i <= sum ;i++) {
		    	String starts=StringUtil.decimalConvert(String.valueOf(i*duan+1), 10, 16, 4);
		    	String pkgcount="";      //每个指令读取多少长度
		    	if(i==count){
		    	    pkgcount=length-duan*i+"";   //最后一次的读取长度
		    	   }else{
		    	    pkgcount=duan+"";
		    	   }
		    	pkgcount=StringUtil.decimalConvert(String.valueOf(pkgcount), 10, 16, 4);  //寄存器开发位置
		    	String cmdStr= "021800000006"+ type + "04"  + starts + pkgcount;
		    	
		    	byte[] sbuf = CRC16M.getSendBuf(cmdStr);
		    	String cmdStrTwo = CRC16M.getBufHexStr(sbuf).trim();
		    	logger.info("cmdStr : " +cmdStrTwo);
		    	SessionUtil.storage.put(point.getPointId()  + "start_id" + type, String.valueOf(i*duan+1));
		    	int dangqian = i;
		    	if(dangqian==0) {
			    	thread[i] = new Thread(new Runnable() {
			            @Override
			            public void run() {	
			            	    logger.info("Thread.currentThread().getName():" + Thread.currentThread().getName());
			            	   
			            	    saveDtsSend(point, cmdStrTwo); 
			            	    
			            }
			        },"ThreadDts" + type + i);	
			    	thread[i].start();
		    	}else {
		    		//Thread.sleep(3*1000);
		    		
		    		thread[i] = new Thread(new Runnable() {
			            @Override
			            public void run() {	
			            	    logger.info("Thread.currentThread().getName():" + Thread.currentThread().getName());
			            	    try {
			            	    	Thread.sleep(3*dangqian*1000);
									thread[dangqian-1].join();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
			            	   //SessionUtil.storage.put(point.getPointId()  + "start_id" + type, String.valueOf(dangqian*duan+1));
			            	    saveDtsSend(point, cmdStrTwo); 		            		
			            }
			        },"ThreadDts" + type + i);
		    		//thread[dangqian-1].join(3*1000);
		    		thread[i].start();
		    	}
		    	
		    	//saveDtsSend(point,cmdStrTwo);
		    }
		}
		return result;
	}
	
	private TblTemperatureDataEntity saveEarlyTemperatureData( TblPointsEntity point,TblCommandProtocolEntity protocol,Integer distinct,String type) {
		String code = "";
		if(type.equals("01")) {
			code = "A相DTS温度";
		}else if(type.equals("02")) {
			code = "B相DTS温度";
		}else {
			code = "C相DTS温度";
		}
		Map map = new HashMap();
    	map.clear();
 		map.put("pointId", new ParamMatcher(point.getPointId()));
 		map.put("dataType", new ParamMatcher(MatcheType.LIKE,code));
 		
 		List<TblCommandProtocolEntity> indicatorValue  =  (List<TblCommandProtocolEntity>) crudService.findByMoreFiled(TblCommandProtocolEntity.class,map,true);
		
		TblTemperatureDataEntity entity = new TblTemperatureDataEntity(); 
		entity.setPointId(point.getPointId()); 
		entity.setPointName(point.getPointName());
		//entity.setIndicatorId(protocol.getIndicatorId()); 
		entity.setIndicatorId(indicatorValue.get(0).getIndicatorId());  
		entity.setValue("0.1"); 
		entity.setRecordTime(new Date()); 
		entity.setProtocolId(indicatorValue.get(0).getProtocolId());  //数据来源协议id
		TblIndicatorValueEntity indicator = (TblIndicatorValueEntity)crudService.get(indicatorValue.get(0).getIndicatorId(), TblIndicatorValueEntity.class, true);
		entity.setCategroryId(indicator.getCategoryId()); //指标类别
		entity.setPointCatagoryId(point.getPointCatagoryId()); //监控系统类别
		entity.setIsAlarm(0); 
		entity.setIsEarlyWarning(0); 
		entity.setRangeSize(distinct);
		crudService.save(entity, true);
		return entity;
	}
	
	
	private void saveDtsSend(TblPointsEntity point, String sendContent) {
		String dtsCode = sendContent.substring(12, 14);
		String code = "";
		if(dtsCode.equals("01")) {
			code = "A相DTS温度";
		}else if(dtsCode.equals("02")) {
			code = "B相DTS温度";
		}else {
			code = "C相DTS温度";
		}
		TblPointSendCommandEntity data = new TblPointSendCommandEntity();		
		data.setSystem_start("no");
		data.setCommandCatagoryId(8);
		data.setCommandContent(sendContent);
		data.setCommandName("获取分布式光纤温度数据");
		data.setPointId(point.getPointId()); 
		data.setPointName(point.getPointName());
		Map map = new HashMap();
    	map.clear();
 		map.put("pointId", new ParamMatcher(point.getPointId()));
 		map.put("dataType", new ParamMatcher(MatcheType.LIKE,code));
 		
 		List<TblCommandProtocolEntity> indicatorValue  =  (List<TblCommandProtocolEntity>) crudService.findByMoreFiled(TblCommandProtocolEntity.class,map,true);
		
		data.setProtocolId(String.valueOf(indicatorValue.get(0).getProtocolId()));  //A相DTS温度
		data.setIs_controller("0");
		
		
		
		TblPointSendCommandEntity update = (TblPointSendCommandEntity)crudService.update(data, true);
		//System.out.println(update.getSendCommandId());
		logger.info("SendCommandId" + update.getSendCommandId());
		
		
		//保存记录 并加锁
		TblCommandRecordEntity record = updateServerRecord(update,point.getPointRegistPackage()); 
		//执行发送
		try {
				nettyService.serverSendMessage(point.getPointRegistPackage(), update.getCommandContent());
			} catch (Exception e) {
					e.printStackTrace();
			} 
	}
	
	
	/**
	 * @param point
	 * @param record
	 * @return 保存局放采集全量数据 DeviceDataColl,DeviceDataSpecial
	 * @throws InterruptedException 
	 *  
	 */
	private List<Object> saveDeviceDataTotal(TblPointsEntity point, TblCommandRecordEntity record,DeviceData  deviceData,List<ProtocolInfo> currentDataProtocol) throws InterruptedException {
		
		List<Object> result = new ArrayList<Object>();
		
		String substring = record.getBackContent().substring(10);
		//System.out.println(substring); 
		logger.info(substring);
		String substringcopy = substring;
		long curTime = new Date().getTime();
										  
		DeviceDataSpecial deviceDataSpecialA = createDeviceDataSpecial(record);
		SessionUtil.storage.put(point.getPointId() + "record_id" + "A" , deviceDataSpecialA.getId()); //设置好 record_id
		
		DeviceDataSpecial deviceDataSpecialB = createDeviceDataSpecial(record);
		SessionUtil.storage.put(point.getPointId() + "record_id" + "B" , deviceDataSpecialB.getId()); //设置好 record_id
		
		DeviceDataSpecial deviceDataSpecialC = createDeviceDataSpecial(record);
		SessionUtil.storage.put(point.getPointId() + "record_id" + "C", deviceDataSpecialC.getId()); //设置好 record_id
		
		logger.info(""+ SessionUtil.storage.get(point.getPointId() + "record_id" + "A"));
		logger.info(""+SessionUtil.storage.get(point.getPointId() + "record_id" + "B"));
		logger.info(""+SessionUtil.storage.get(point.getPointId() + "record_id" + "C"));
		for(ProtocolInfo protocolInfo : currentDataProtocol) {
    		TblCommandProtocolEntity protocol = protocolInfo.getProtocol(); 
		    //解析数据
		    List<BigDecimal> data = AnalysisDataUtil.getData(record.getBackContent(),protocol); 
		    
		    logger.info("局放采集到的数值: " +data.get(0).toString());
		    savePhaseNo(data.get(0).toString(),protocol);  //取得 相位 , 是否 为 A相, B相,C相 
		    
		    String protocolName  = protocol.getDataType();
		    
		    DeviceDataSpecial dataSpecial = deviceDataSpecialA;
		    
		    if(protocolName.indexOf("A")>-1||protocolName.indexOf("a")>-1) {
		    	 dataSpecial = (DeviceDataSpecial) crudService.get(deviceDataSpecialA.getId(), DeviceDataSpecial.class, true);		    	
		    }else if(protocolName.indexOf("B")>-1||protocolName.indexOf("b")>-1) {
		    	 dataSpecial = (DeviceDataSpecial) crudService.get(deviceDataSpecialB.getId(), DeviceDataSpecial.class, true);		    	
		    }else {
		    	 dataSpecial = (DeviceDataSpecial) crudService.get(deviceDataSpecialC.getId(), DeviceDataSpecial.class, true);		    	
		    }
		    

		    DeviceDataColl dataColl = new DeviceDataColl(); 
		    dataColl.setCommond_record_id(record.getSendCommandId());
			
			dataColl.setPointId(record.getPointId());
			dataColl.setPointName(point.getPointName());
			dataColl.setPhase_no((String)SessionUtil.storage.get(point.getPointId() + "phase_no"));
			dataColl.setRecordTime(new Date());
			dataColl.setRecord_id(dataSpecial.getId());
			
			
		    
		   // String protocolName  = protocol.getDataType();	
		   
			String phase_no = (String)SessionUtil.storage.get(point.getPointId() + "phase_no");
		    if(protocolName.indexOf("频次")>-1) {
		    	dataSpecial.setFrequency(Integer.parseInt(data.get(0).toString()));
		    	dataColl.setValue(data.get(0).toString());
		    	sendFrequent(point,data.get(0).toString(),phase_no);  ////得到频次
		    }else if (protocolName.indexOf("幅值")>-1) {
		    	dataSpecial.setAmplitude(Float.parseFloat(data.get(0).toString()));
		    	dataSpecial.setPhase(Float.parseFloat(data.get(0).toString()));
		    	dataColl.setValue(data.get(0).toString());
		    }else {   //能量值
		    	dataSpecial.setEnergy(Integer.parseInt(data.get(0).toString()));
		    	dataColl.setValue(data.get(0).toString());
		    }
		    dataSpecial.setPhase_no((String)SessionUtil.storage.get(point.getPointId() + "phase_no"));
		    dataColl.setIndicatorId(protocol.getIndicatorId());
			dataColl.setProtocolId(protocol.getProtocolId());
			TblIndicatorValueEntity indicator = (TblIndicatorValueEntity)crudService.get(protocol.getIndicatorId(), TblIndicatorValueEntity.class, true);
			dataColl.setCategroryId(indicator.getCategoryId());
			dataColl.setPointCatagoryId(point.getPointCatagoryId());
			
			crudService.save(dataColl, true);
			result.add(dataColl);
			crudService.update(dataSpecial, true);	
		}								
							
		//SessionUtil.storage.remove(point.getPointId() + "phase_no");
		return result;
	}
	
	
	public DeviceDataSpecial createDeviceDataSpecial(TblCommandRecordEntity record) {
		
		DeviceDataSpecial dataSpecial = new DeviceDataSpecial();  
		dataSpecial.setCommond_record_id(record.getCommandRecordId());
		dataSpecial.setPointId(record.getPointId());
		//dataSpecial.setPhase_no((String)SessionUtil.storage.get(point.getPointId() + "phase_no"));
		dataSpecial.setCreatetime(new Date());
		dataSpecial.setRecord_id(String.valueOf(dataSpecial.getId()));
		dataSpecial.setFrequency(0);
		dataSpecial.setAmplitude(0f);
		dataSpecial.setEnergy(0);		
		crudService.save(dataSpecial, true);
		return dataSpecial;
		
	}
	
	
	//计算频次后发送
	public void sendFrequent(TblPointsEntity point,String frequency ,String phase_no) throws InterruptedException {
		Integer pinci = Integer.parseInt(frequency)*2;
		if(pinci == 0) {
			return;
		}
		logger.info("幅值相位频次数 : " +pinci);
		String channelId = "";
		if(phase_no.contentEquals("B")) {
			 channelId = "0002";
			 SessionUtil.storage.put(point.getPointId() + "phase_no1","B");
		}else if(phase_no.contentEquals("C")) {
			 channelId = "0003";
			 SessionUtil.storage.put(point.getPointId() + "phase_no1","C");
		}else if(phase_no.contentEquals("A")){
			 channelId = "0001";
			 SessionUtil.storage.put(point.getPointId() + "phase_no1","A");
		}else {
			return;
		}
		
		int count=1;
	    int pkgsum=122;
	    int sum = pinci/pkgsum;
	   
	   
	    //ExecutorService executorService = Executors.newSingleThreadExecutor();
	    Thread[] thread = new Thread[sum+1];
	    for(int i = 0; i <= sum ;i++) {
	    	
	    	String starts=StringUtil.decimalConvert(String.valueOf(i*pkgsum), 10, 16, 4);  //寄存器开发位置
	    	String pkgcount="";      //每个指令读取多少长度
	    	if(i==count){
	    	    pkgcount=pinci-pkgsum*i+"";   //最后一次的读取长度
	    	   }else{
	    	    pkgcount=pkgsum+"";
	    	   }
	    	pkgcount=StringUtil.decimalConvert(String.valueOf(pkgcount), 10, 16, 4);  //寄存器开发位置
	    	
	    	String cmdStr= "01140706" +channelId + starts+pkgcount;
	    	//logger.info("cmdStr one : " +cmdStr);
	    	byte[] sbuf = CRC16M.getSendBuf(cmdStr);
	    	String cmdStrTwo = CRC16M.getBufHexStr(sbuf).trim();
	    	logger.info("cmdStr : " +cmdStrTwo);
	    	int dangqian = i;
	    	if(dangqian==0) {
		    	thread[i] = new Thread(new Runnable() {
		            @Override
		            public void run() {	
		            	    logger.info("Thread.currentThread().getName():" + Thread.currentThread().getName());
		            		saveAndSend(point, cmdStrTwo);
		            		logger.info("0次 cmdStr : " +cmdStrTwo);
		            }
		        },"Thread" + channelId + i);	
	    	}else {
	    		thread[i] = new Thread(new Runnable() {
		            @Override
		            public void run() {	
		            	    logger.info("Thread.currentThread().getName():" + Thread.currentThread().getName());
		            	    try {
		            	    	Thread.sleep(3*dangqian*1000);
								thread[dangqian-1].join();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		            	    saveAndSend(point, cmdStrTwo); 	
		            	    logger.info( dangqian + "次 cmdStr : " +cmdStrTwo);
		            }
		        },"Thread" + channelId + i);
	    	}
	    							
	    	thread[i].start();
	    		    		    		    		
			 
	    }
	    	    		
	
	}
	
	
	
	
	
	private void saveAndSend(TblPointsEntity point, String sendContent) { 
		Map map = new HashMap();
    	map.clear();
 		map.put("pointId", new ParamMatcher(point.getPointId()));
 		List<TblCommandProtocolEntity> protocols  =  (List<TblCommandProtocolEntity>) crudService.findByMoreFiled(TblCommandProtocolEntity.class,map,true);
 		String protocolA= "";
		String protocolB= "";
		String protocolC= "";
 		for(TblCommandProtocolEntity protocol : protocols) { 			
 			if(protocol.getDataType().indexOf("局放A")>-1||protocol.getDataType().indexOf("局放a")>-1) {
 				if(protocolA.contentEquals("")){
 					protocolA = protocolA + protocol.getProtocolId(); 
 				}else {
 					protocolA = protocolA + "," + protocol.getProtocolId(); 
 				}
 			}
 			if(protocol.getDataType().indexOf("局放B")>-1||protocol.getDataType().indexOf("局放b")>-1) {
 				if(protocolB.contentEquals("")){
 					protocolB = protocolB + protocol.getProtocolId(); 
 				}else {
 					protocolB = protocolB + "," + protocol.getProtocolId();
 				}
 			}
 			if(protocol.getDataType().indexOf("局放C")>-1||protocol.getDataType().indexOf("局放c")>-1) {
 				if(protocolC.contentEquals("")){
 					protocolC = protocolC + protocol.getProtocolId(); 
 				}else {
 					protocolC = protocolC + "," + protocol.getProtocolId(); 
 				}
 			}
 		}
 		
		TblPointSendCommandEntity data = new TblPointSendCommandEntity();		
		data.setSystem_start("no");
		data.setCommandCatagoryId(7);
		data.setCommandContent(sendContent);
		data.setCommandName("获取幅值相位");
		data.setPointId(point.getPointId()); 
		data.setPointName(point.getPointName());
		String phase_type = (String)SessionUtil.storage.get(point.getPointId() + "phase_no1");//获取是A还是B还是C相
		
		
		if("A".equals(phase_type)) {
			//data.setProtocolId("17,14,20");
			data.setProtocolId(protocolA);
		}else if("B".equals(phase_type)){
			//data.setProtocolId("18,15,21");
			data.setProtocolId(protocolB);
		}else {
			//data.setProtocolId("19,16,22");
			data.setProtocolId(protocolC);
		}
		
		
		TblPointSendCommandEntity update = (TblPointSendCommandEntity)crudService.update(data, true);
		//System.out.println(update.getSendCommandId());
		logger.info("SendCommandId" + update.getSendCommandId());
		
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
			TblPointsEntity point = (TblPointsEntity)crudService.get(record.getPointId(), TblPointsEntity.class, true);
			record.setPointName(point.getPointName());
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
		
		TblAdminUserEntity currentUser = LoginUser.map.get(UserModuleConst.USER_SESSION_ATTRIBUTENAME);  
		if(currentUser!=null) {
			  entity.setAdminUserId(LoginUser.map.get(UserModuleConst.USER_SESSION_ATTRIBUTENAME).getAdminUserId());//记录操作人 
		}
		 
		entity.setPointName(commandDefault.getPointName());
		entity.setCommandCatagoryId(commandDefault.getCommandCatagoryId());  //指令类别
		entity.setSendCommandId(commandDefault.getSendCommandId()); //发送的默认指令Id
		entity.setCommandContent(commandDefault.getCommandContent());//发送内容
		entity.setRecordTime(new Date()); //记录时间
		//将记录id保存到会话，当有返回消息时保存起来
		TblCommandRecordEntity update = (TblCommandRecordEntity)crudService.update(entity, true); 
//		String registPackage = getRegistPackage(entity.getPointId());
		logger.info("第二步预发送指令 注册包：registPackage：" +registPackage+ "指令内容："+commandDefault.getCommandContent() + "操作记录commandRecordId " + update.getCommandRecordId());
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
		TblAdminUserEntity currentUser = LoginUser.map.get(UserModuleConst.USER_SESSION_ATTRIBUTENAME);
		if(currentUser!=null) {
			  entity.setAdminUserId(LoginUser.map.get(UserModuleConst.USER_SESSION_ATTRIBUTENAME).getAdminUserId());//记录操作人 
		}		
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
