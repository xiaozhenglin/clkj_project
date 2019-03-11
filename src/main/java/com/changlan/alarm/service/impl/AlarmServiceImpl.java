package com.changlan.alarm.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changlan.alarm.service.IAlarmRuleService;
import com.changlan.alarm.service.IAlarmService;
import com.changlan.common.entity.TBLAlarmCategoryEntity;
import com.changlan.common.entity.TblAlarmRuleEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblPointAlamDataEntity;
import com.changlan.common.entity.TblPointSendCommandEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.BigDecimalUtil;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.indicator.pojo.IndiCatorValueDetail;
import com.changlan.indicator.pojo.SimpleIndicator;
import com.changlan.indicator.service.IIndicatoryValueService;
import com.changlan.point.dao.IPointDataDao;
import com.changlan.point.service.IPointDataService;

@Service
public class AlarmServiceImpl implements IAlarmService{
	
	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private IAlarmRuleService alarmRuleService;
	
	@Autowired
	private IPointDataDao pointDataDao;
	
	@Autowired
	private IPointDataService pointDataService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	@Transactional
	public void anylysisPointData(List<TblPoinDataEntity> pointDatas) {
		logger.info("第五步-----》报警规则计算开始");
		//指标类别,  指标id|指标值    指标值的区间比较用
		Map<Integer,List<SimpleIndicator>> map = new HashMap();
		for(TblPoinDataEntity data : pointDatas ) {
			SimpleIndicator simpleIndicator = new SimpleIndicator(data.getIndicatorId(), data.getValue(),data.getPointDataId());
			List<SimpleIndicator> list = map.get(data.getCategroryId()); 
			if(ListUtil.isEmpty(list)) {
				list = new ArrayList<SimpleIndicator>();
			}
			list.add(simpleIndicator);
			map.put(data.getCategroryId(), list);
		}
		
		
		for(TblPoinDataEntity data : pointDatas ) {
			BigDecimal value = new BigDecimal(data.getValue());
			//规则
			List<TblAlarmRuleEntity> rules = alarmRuleService.getAll(null,data.getIndicatorId(),data.getPointId());
			//没有找到,就不进行报警计算
			for(int j = 0 ;j<rules.size() ;j++) {
				TblAlarmRuleEntity rule = rules.get(j); 
				//规则类
				Integer alarmCategoryId = rule.getAlarmCategoryId(); 
				switch (alarmCategoryId) {
				case 1:
					// a
					int intValue = value.intValue();
					canculateAlarm(intValue,data,rule,data.getPointDataId());
					break;
				case 2:
					//找到相同类别的其它指标的值，分别进行比较
					List<SimpleIndicator> simpleIndicator = map.get(data.getCategroryId()); 
					for(SimpleIndicator simple : simpleIndicator) {
						if(simple.getIndicatorId() != data.getIndicatorId()) {
							//a-b
							BigDecimal indicatorValue = new BigDecimal(simple.getIndicatorValue());
							BigDecimal subtract = value.subtract(indicatorValue); 
							//|a-b|
							int abs = subtract.abs().intValue(); 
							canculateAlarm(abs,data,rule,simple.getPointDataId());
						}
					}
					break;
				case 3:
					//获取相同指标的上次历史的值进行一次比较
					TblPoinDataEntity thePenultimateData = pointDataDao.getThePenultimateData(rule.getPointId(), rule.getIndicatorValueId());
					if(thePenultimateData!=null) {
						BigDecimal penultimateValue = new BigDecimal(thePenultimateData.getValue());
						//a-b
						BigDecimal subtract = value.subtract(penultimateValue); 
						//|a-b|
						int abs = subtract.abs().intValue(); 
						canculateAlarm(abs,data,rule,thePenultimateData.getPointDataId());
					}
					break;
				case 4:
					//线圈状态类别
					Integer abnomal = rule.getAbnomal();
					Integer normal = rule.getNormal(); 
					if(value.intValue() == abnomal) {
						Integer alarmDataId = saveToAlarmDataBase(value.intValue(), data, rule, data.getPointDataId());
						data.setIsEarlyWarning(1); 
						update(data,alarmDataId);
					}
					break;
				default:
					break;
				}
			}
		}
		logger.info("-----》报警规则计算结束");
	}



	private void canculateAlarm(int intValue, TblPoinDataEntity data, TblAlarmRuleEntity rule,Integer constractDataId) {
		Integer topAlarm = rule.getTopAlarm();
		Integer lowerAlarm = rule.getLowerAlarm();
		Integer topLimit = rule.getTopLimit(); 
		Integer lowerLimit = rule.getLowerLimit(); 
		if(intValue< lowerAlarm || intValue>topAlarm) {
			//报警
			sendSMSMessage(data.getPointId(),data.getIndicatorId());
			Integer alarmDataId = saveToAlarmDataBase(intValue, data, rule, constractDataId);
			data.setIsAlarm(1); 
			update(data,alarmDataId);
		}
		if( (intValue> topLimit && intValue<=topAlarm) || (intValue<lowerLimit && intValue>= lowerAlarm) ) {
			//预警
			Integer alarmDataId =  saveToAlarmDataBase(intValue, data, rule, constractDataId);
			data.setIsEarlyWarning(1); 
			update(data,alarmDataId);
		}
	}


	private Integer saveToAlarmDataBase(int intValue, TblPoinDataEntity data, TblAlarmRuleEntity rule, Integer constractDataId) {
		TblPointAlamDataEntity entity = new TblPointAlamDataEntity(); 
		entity.setAlarmDate(new Date());
		entity.setAlarmRuleId(rule.getAlarmRuleId());
		entity.setIndicatorId(data.getIndicatorId());
		entity.setValue(data.getValue());
		entity.setIsNotice(1);
		entity.setPointId(data.getPointId()); 
		entity.setCurrentDataId(data.getPointDataId()); 
		entity.setContrastDataId(constractDataId); 
		TblPointAlamDataEntity update = (TblPointAlamDataEntity)crudService.update(entity, true); 
		return update.getAlarmId();
	}

	private void update(TblPoinDataEntity data, Integer alarmDataId) { 
		String alarmDataIds = data.getAlarmDataId();
		if(StringUtil.isEmpty(alarmDataIds)) {
			data.setAlarmDataId(alarmDataId.toString());
		}else {
			data.setAlarmDataId(alarmDataIds+","+alarmDataId.toString());
		}
		TblPoinDataEntity update = pointDataService.update(data);
	}


	
	private void sendSMSMessage(Integer pointId, Integer indicatorId) {
		// TODO Auto-generated method stub
		
	}







	
	
}
