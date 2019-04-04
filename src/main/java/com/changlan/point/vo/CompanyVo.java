package com.changlan.point.vo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.util.SpringUtil;
import com.changlan.point.pojo.LineDetail;
import com.changlan.point.service.ILineService;

public class CompanyVo {
//	private TblCompanyEntity company;//第一级一个公司
	
    private Integer companyId;
    private String  title;
	private List<CompanyLineVO> companyLinesVOs = new ArrayList<CompanyLineVO>(); //二级包含多条线路信息

	public CompanyVo(TblCompanyEntity company) {
		super();
//		this.company = company;
		this.companyId = company.getCompanyId();
		this.title = company.getName();
		List<LineDetail> lines = getLines(company.getCompanyId()); 
		for(LineDetail line : lines) {
			CompanyLineVO lineVO = new CompanyLineVO(line);
			companyLinesVOs.add(lineVO);
		}
	}

	private List<LineDetail> getLines(Integer companyId) {
		ILineService lineService = SpringUtil.getBean(ILineService.class);
		TblLinesEntity entity = new TblLinesEntity();
		entity.setCompanyId(companyId);
		List<LineDetail> all = lineService.getAll(entity); 
		return all;
	}

	
//	public TblCompanyEntity getCompany() {
//		return company;
//	}
//
//	public void setCompany(TblCompanyEntity company) {
//		this.company = company;
//	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<CompanyLineVO> getCompanyLinesVOs() {
		return companyLinesVOs;
	}

	public void setCompanyLinesVOs(List<CompanyLineVO> companyLinesVOs) {
		this.companyLinesVOs = companyLinesVOs;
	}
	
	

	

}
