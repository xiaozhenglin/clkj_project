package com.changlan.point.pojo;

import java.util.List;

import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblCompanyGroupEntity;

public class CompanyDetail {
	//一个公司对应一个组
//	private TblCompanyGroupEntity groupInfo;
	private TblCompanyEntity company;
	
	public CompanyDetail() {
		super();
	}

	public CompanyDetail(TblCompanyEntity company, TblCompanyGroupEntity groupInfo) {
		this.company = company;
//		this.groupInfo = groupInfo;
	}

	public TblCompanyEntity getCompany() {
		return company;
	}

	public void setCompany(TblCompanyEntity company) {
		this.company = company;
	}

//	public TblCompanyGroupEntity getGroupInfo() {
//		return groupInfo;
//	}
//
//	public void setGroupInfo(TblCompanyGroupEntity groupInfo) {
//		this.groupInfo = groupInfo;
//	}
	
	
	
}
