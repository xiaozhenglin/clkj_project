package com.changlan.point.service;

import java.util.List;

import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.point.pojo.PointDataDetail;

public interface IPointDataService {

	List<PointDataDetail> getAll(TblPoinDataEntity entity);

	TblPoinDataEntity update(TblPoinDataEntity data);  

}
