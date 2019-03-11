package com.changlan.indicator.service;

import java.util.List;

import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.indicator.pojo.IndiCatorValueDetail;

public interface IIndicatoryValueService {

	//是否名称重复
	Boolean existName(TblIndicatorValueEntity entity);

	//获取值定义详情
	List<IndiCatorValueDetail> getAll(Integer id,Integer categoryId);   
	
}
