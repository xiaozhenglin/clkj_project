package com.changlan.point.vo;

import java.util.List;

import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.util.SpringUtil;
import com.changlan.point.pojo.LineDetail;
import com.changlan.point.service.ILineService;

public class CompanyLineVO {
	private TblCompanyEntity company;//一个公司
	private List<LineDetail> lines;//多条线路信息
	
	public CompanyLineVO(TblCompanyEntity company) {
		super();
		this.company = company;
		this.lines = getLines(company.getCompanyId());
	}
	
	public CompanyLineVO() {
	}

	private List<LineDetail> getLines(Integer companyId) {
		ILineService lineService = SpringUtil.getBean(ILineService.class);
		TblLinesEntity entity = new TblLinesEntity();
		entity.setCompanyId(companyId);
		List<LineDetail> all = lineService.getAll(entity); 
		return all;
	}

	public TblCompanyEntity getCompany() {
		return company;
	}
	public void setCompany(TblCompanyEntity company) {
		this.company = company;
	}
	public List<LineDetail> getLines() {
		return lines;
	}
	public void setLines(List<LineDetail> lines) {
		this.lines = lines;
	}
	
	
	
}
