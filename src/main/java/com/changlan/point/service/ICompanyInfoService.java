package com.changlan.point.service;

import java.util.List;

import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.point.pojo.CompanyDetail;

public interface ICompanyInfoService {

	//查询公司列表
	List<CompanyDetail> companyList(TblCompanyEntity company); 
	
	//查询公司列表
	List<TblCompanyEntity> getAll(Integer companyId); 

	//添加或者修改线路
	Boolean existName(TblCompanyEntity entity);  

}
