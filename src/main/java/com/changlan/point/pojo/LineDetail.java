package com.changlan.point.pojo;

import java.util.ArrayList;
import java.util.List;

import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblPointCategoryEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.point.service.ICompanyInfoService;

public class LineDetail {
	
	private TblLinesEntity line;  // 一条线路信息
	private  List<TblPointCategoryEntity> pointCategorys = new ArrayList<TblPointCategoryEntity>(); //一条线路多个监控类别
	private TblCompanyEntity company; //所属一个公司信息 get方法去掉，不显示
	
	public LineDetail() { 
		super();
	}

	
	public LineDetail(TblLinesEntity line) { 
		ICrudService crudService = SpringUtil.getBean(ICrudService.class);
		this.line = line;
		List<String> stringToList = StringUtil.stringToList(line.getPointCatagoryIds()); 
		for(String str : stringToList) {
			TblPointCategoryEntity category = (TblPointCategoryEntity)crudService.get(Integer.parseInt(str), TblPointCategoryEntity.class, true);
			this.pointCategorys.add(category);
		}
		
		
		//获取所属公司信息
//		ICompanyInfoService companyInfoService = SpringUtil.getBean(ICompanyInfoService.class);
//		TblCompanyEntity tblCompanyEntity = new TblCompanyEntity(); 
//		tblCompanyEntity.setCompanyId(line.getCompanyId());
//		List<CompanyDetail> companyList = companyInfoService.companyList(tblCompanyEntity);
//		if(!ListUtil.isEmpty(companyList)) { 
//			CompanyDetail companyDetail = companyList.get(0); 
//			this.company = companyDetail.getCompany(); 
//			super.setGroupInfo(companyDetail.getGroupInfo()); 
//		}
	}


	public TblLinesEntity getLine() {
		return line;
	}

	public void setLine(TblLinesEntity line) {
		this.line = line;
	}

	public List<TblPointCategoryEntity> getPointCategorys() {
		return pointCategorys;
	}

	public void setPointCategorys(List<TblPointCategoryEntity> pointCategorys) {
		this.pointCategorys = pointCategorys;
	}


//	public TblCompanyEntity getCompany() {
//		return company;
//	}


	public void setCompany(TblCompanyEntity company) {
		this.company = company;
	}
	
	
}
