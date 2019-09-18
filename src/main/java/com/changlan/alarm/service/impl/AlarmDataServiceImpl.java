package com.changlan.alarm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.soap.Detail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.changlan.alarm.pojo.AlarmDataQuery;
import com.changlan.alarm.pojo.AlarmDownRecordQuery;
import com.changlan.alarm.pojo.AlarmDownType;
import com.changlan.alarm.pojo.TblAlarmDataDetail;
import com.changlan.alarm.service.IAlarmDataService;
import com.changlan.common.entity.TBLAlarmCategoryEntity;
import com.changlan.common.entity.TblAlarmDownRecordEntity;
import com.changlan.common.entity.TblAlarmRuleEntity;
import com.changlan.common.entity.TblCompanyChannelEntity;
import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblPointAlamDataEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.MatcheType;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.point.pojo.PointInfoDetail;

@Service
public class AlarmDataServiceImpl implements IAlarmDataService {
	
	@Autowired
	ICrudService crudService;

	@Override
	public List<TblAlarmDataDetail> getList(TblPointAlamDataEntity entity) {
		Map map = new HashMap();
		if(entity.getAlarmId() != null) {
			map.put("alarmId", new ParamMatcher(entity.getAlarmId()));
		}
		if(entity.getIndicatorId()!=null) {
			map.put("indicatorValueId", new ParamMatcher(entity.getIndicatorId()));
		}
		if(entity.getAlarmRuleId()!=null) {
			map.put("alarmRuleId", new ParamMatcher(entity.getAlarmRuleId()));
		}
		if(StringUtil.isNotEmpty(entity.getDownStatus())) {
//			if(entity.getDownStatus().equals(AlarmDownType.DOWN.toString())) {
//				map.put("downStatus", new ParamMatcher(entity.getDownStatus()));
//			}else {
//				map.put("downStatus", new ParamMatcher(MatcheType.NOT_EQUALS,AlarmDownType.DOWN.toString()));
//			}
			map.put("downStatus", new ParamMatcher(entity.getDownStatus()));
		}
		List<TblPointAlamDataEntity> all = crudService.findByMoreFiled(TblPointAlamDataEntity.class, map, true);
		
		List<TblAlarmDataDetail> list = new ArrayList<TblAlarmDataDetail>();
		for(TblPointAlamDataEntity alarmData : all) {
			TblAlarmRuleEntity alarmRule = (TblAlarmRuleEntity)crudService.get(alarmData.getAlarmRuleId(), TblAlarmRuleEntity.class, true);
			TblAlarmDataDetail detail = new TblAlarmDataDetail(alarmData,alarmRule);
			if(alarmData.getAlarmDownRecordId()!=null) {
				TblAlarmDownRecordEntity downRecord = (TblAlarmDownRecordEntity)crudService.get(alarmData.getAlarmDownRecordId(), TblAlarmDownRecordEntity.class, true);
				if(downRecord!=null) {
					detail.setDownRecord(downRecord); 
				}
			}
			list.add(detail);
		}
		return list;
	}

	@Override
	public TblAlarmDataDetail getDetail(Integer id) {
		TblPointAlamDataEntity alarmData = (TblPointAlamDataEntity)crudService.get(id, TblPointAlamDataEntity.class, true);
		TblAlarmRuleEntity alarmRule = (TblAlarmRuleEntity)crudService.get(alarmData.getAlarmRuleId(), TblAlarmRuleEntity.class, true);
		TblAlarmDataDetail detail = new TblAlarmDataDetail(alarmData,alarmRule);
		return detail;
	}

	@Override
	public Page<TblAlarmDataDetail> getPage(AlarmDataQuery entity, Pageable pageable) {
		Page<TblAlarmDataDetail> result ;
		List<TblAlarmDataDetail> list = new ArrayList<TblAlarmDataDetail>();
		list.clear();
		Page<TblAlarmDataDetail> detailList  ;
		Map map = new HashMap();
		if(entity.getAlarmId() != null) {
			map.put("alarmId", new ParamMatcher(entity.getAlarmId()));
		}
		if(entity.getIndicatorId()!=null) {
			map.put("indicatorId", new ParamMatcher(entity.getIndicatorId()));
		}
		if(entity.getAlarmRuleId()!=null) {
			map.put("alarmRuleId", new ParamMatcher(entity.getAlarmRuleId()));
		}
		
		
		if(entity.getBegin()!=null&& entity.getEnd()!=null) {
			map.put("alarmDate", new ParamMatcher(entity.getBegin(),entity.getEnd()));
		}
		if(StringUtil.isNotEmpty(entity.getDownStatus())   ) {
//			if(entity.getDownStatus().equals(AlarmDownType.DOWN.toString())) {
//				map.put("downStatus", new ParamMatcher(entity.getDownStatus()));
//			}else {
//				map.put("downStatus", new ParamMatcher(MatcheType.NOT_EQUALS,AlarmDownType.DOWN.toString()));
//			}
			map.put("downStatus", new ParamMatcher(entity.getDownStatus()));
		}
		if(entity.getPointId()!=null) {
			map.put("pointId", new ParamMatcher(entity.getPointId()));
		}else {
				
			if(StringUtil.isNotEmpty(entity.getPointNameOrLineNameOrChannelName())){
				String common = entity.getPointNameOrLineNameOrChannelName();
				
				Map channelMap = new HashMap();			
				channelMap.put("name", new ParamMatcher(MatcheType.LIKE,entity.getPointNameOrLineNameOrChannelName()));
				List<TblCompanyChannelEntity> listChannel = crudService.findByMoreFiled(TblCompanyChannelEntity.class, channelMap, true);
				for(TblCompanyChannelEntity channel :listChannel) {
					Map lineMap = new HashMap();			
					lineMap.put("channelId", new ParamMatcher(channel.getChannelId()));
					List<TblLinesEntity> listLine = crudService.findByMoreFiled(TblLinesEntity.class, lineMap, true); 
					for(TblLinesEntity line :listLine) {
						Map pointMap = new HashMap();			
						pointMap.put("lineId", new ParamMatcher(line.getLineId()));
						List<TblPointsEntity> listPoints = crudService.findByMoreFiled(TblPointsEntity.class, pointMap, true); 
						for(TblPointsEntity point:listPoints) {
							if(point.getPointId() !=null) {
								map.put("pointId", new ParamMatcher(point.getPointId()));
							}
							 detailList = dealPage(entity, map,pageable);
							 for(TblAlarmDataDetail o:detailList) {
								 list.add(o);
							 }
						}
					}
				}
				if(ListUtil.isEmpty(listChannel)) {
					Map lineMap = new HashMap();			
					lineMap.put("lineName", new ParamMatcher(MatcheType.LIKE,entity.getPointNameOrLineNameOrChannelName()));
					List<TblLinesEntity> listLine = crudService.findByMoreFiled(TblLinesEntity.class, lineMap, true); 
					for(TblLinesEntity line :listLine) {
						Map pointMap = new HashMap();			
						pointMap.put("lineId", new ParamMatcher(line.getLineId()));
						List<TblPointsEntity> listPoints = crudService.findByMoreFiled(TblPointsEntity.class, pointMap, true); 
						for(TblPointsEntity point:listPoints) {
							if(point.getPointId() !=null) {
								map.put("pointId", new ParamMatcher(point.getPointId()));
							}
							 detailList = dealPage(entity, map,pageable);
							 for(TblAlarmDataDetail o:detailList) {
								 list.add(o);
							 }
						}
					}
					
					if(ListUtil.isEmpty(listLine)) {
						Map pointMap = new HashMap();
						if(entity.getPointNameOrLineNameOrChannelName() !=null) {
							pointMap.put("pointName", new ParamMatcher(MatcheType.LIKE,entity.getPointNameOrLineNameOrChannelName()));
						}
						List<TblPointsEntity> listPoints = crudService.findByMoreFiled(TblPointsEntity.class, pointMap, true); 
						for(TblPointsEntity point:listPoints) {
							if(point.getPointId() !=null) {
								map.put("pointId", new ParamMatcher(point.getPointId()));
							}
							 detailList = dealPage(entity, map,pageable);
							 for(TblAlarmDataDetail o:detailList) {
								 list.add(o);
							 }
						}
					}
					
				}				
				
			}
		}
		if(ListUtil.isEmpty(list)) {
			detailList = dealPage(entity, map,pageable);
			 for(TblAlarmDataDetail o:detailList) {
				 list.add(o);
			 }
		}
					
		result = new PageImpl<TblAlarmDataDetail>(list, pageable, list.size());
		return result;
	}
	
	public Page<TblAlarmDataDetail> dealPage(AlarmDataQuery entity, Map map ,Pageable pageable){
		
		List<TblAlarmDataDetail> list = new ArrayList<TblAlarmDataDetail>();
		list.clear();
		Page<TblPointAlamDataEntity> datas = crudService.findByMoreFiledAndPage(TblPointAlamDataEntity.class, map, true,pageable);
		
		for(Object o : datas) {
			TblPointAlamDataEntity alarmData =(TblPointAlamDataEntity)o;
			TblAlarmRuleEntity alarmRule = (TblAlarmRuleEntity)crudService.get(alarmData.getAlarmRuleId(), TblAlarmRuleEntity.class, true);
			TblAlarmDataDetail detail = new TblAlarmDataDetail(alarmData,alarmRule);
			
			if(alarmData.getAlarmDownRecordId()!=null) {
				TblAlarmDownRecordEntity downRecord = (TblAlarmDownRecordEntity)crudService.get(alarmData.getAlarmDownRecordId(), TblAlarmDownRecordEntity.class, true);
				if(downRecord!=null) {
					detail.setDownRecord(downRecord); 
				}
			}
			
			list.add(detail);
		}
		
		return new PageImpl<TblAlarmDataDetail>(list, pageable, datas.getTotalElements());
	}

	@Override
	public Page getPage(AlarmDownRecordQuery query, Pageable page) {
		Map map = new HashMap();
		if(query.getAlamDownRecordId() != null) {
			map.put("alamDownRecordId", new ParamMatcher(query.getAlamDownRecordId()));
		}
		if(query.getAlarmDataId()!=null) {
			map.put("alarmDataId", new ParamMatcher(query.getAlarmDataId()));
		}
		if(query.getBegin()!=null && query.getEnd()!=null) {
			map.put("recordTime", new ParamMatcher(query.getBegin(),query.getEnd()));
		}
		Page result = crudService.findByMoreFiledAndPage(TblAlarmDownRecordEntity.class, map, true, page);
		return result;
	}
	

}
