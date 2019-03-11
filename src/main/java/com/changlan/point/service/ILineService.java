package com.changlan.point.service;

import java.util.List;

import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.point.pojo.LineDetail;

public interface ILineService {

	Boolean existGroupName(TblLinesEntity entity);

	List<LineDetail> getAll(TblLinesEntity entity);   

}
