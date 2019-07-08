package com.changlan.alarm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.changlan.alarm.pojo.AlarmDataQuery;
import com.changlan.alarm.pojo.AlarmDownRecordQuery;
import com.changlan.alarm.pojo.TblAlarmDataDetail;
import com.changlan.common.entity.TBLAlarmCategoryEntity;
import com.changlan.common.entity.TblAlarmDownRecordEntity;
import com.changlan.common.entity.TblPointAlamDataEntity;
import com.changlan.common.util.PageUtil;

public interface IAlarmDataService {

	List<TblPointAlamDataEntity> getList(TblPointAlamDataEntity entity);

	TblAlarmDataDetail getDetail(Integer id);

	Page<TblAlarmDataDetail> getPage(AlarmDataQuery query, Pageable page);    
	
	Page<TblAlarmDownRecordEntity> getPage(AlarmDownRecordQuery entity, Pageable page);   

}
