package com.changlan.alarm.service;

import java.util.List;

import com.changlan.alarm.pojo.TblAlarmDataDetail;
import com.changlan.common.entity.TBLAlarmCategoryEntity;
import com.changlan.common.entity.TblPointAlamDataEntity;

public interface IAlarmDataService {

	List<TblPointAlamDataEntity> getList(TblPointAlamDataEntity entity);

	TblAlarmDataDetail getDetail(Integer id);  

}
