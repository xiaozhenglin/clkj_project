package com.changlan.point.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.point.pojo.CompanyDetail;
import com.changlan.point.service.ICompanyGropService;
import com.changlan.point.service.ICompanyInfoService;
import com.changlan.user.pojo.LoginUser;

@Service
public class ComPanyServiceImpl implements ICompanyInfoService{
	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private ICompanyGropService companyGroupService;

	@Override
	public List<CompanyDetail> companyList(TblCompanyEntity company) {
		List<CompanyDetail> list = new ArrayList<CompanyDetail>();
		List<Object> all = null;
		Map map = new HashMap();
		if(company.getGroupId() != null) {
			map.put("groupId", new ParamMatcher(company.getGroupId()));
		}
		if(company.getCompanyId()!=null) {
			map.put("companyId", new ParamMatcher(company.getCompanyId()));
		}
		all = crudService.findByMoreFiled(TblCompanyEntity.class, map, true);
		//封装公司信息和公司组信息
		for(Object o : all) {
			TblCompanyEntity entity = (TblCompanyEntity)o;
			TblCompanyGroupEntity groupInfo = (TblCompanyGroupEntity) crudService.get(entity.getGroupId(), TblCompanyGroupEntity.class, true);
			CompanyDetail detail = new CompanyDetail(entity,groupInfo);
			list.add(detail);
		}
		
		//根据用户权限二次筛选
//		TblAdminUserEntity user = LoginUser.getCurrentUser();
//		String companyIdS = user.getCompanyId(); 
//		if(StringUtil.isNotEmpty(companyIdS)) {
//			List<String> stringToList = StringUtil.stringToList(companyIdS); 
//			for(CompanyDetail detail : list) {
//				Integer companyId = detail.getCompany().getCompanyId(); 
//				if(!stringToList.contains(companyId)) {
//					list.remove(list.indexOf(detail)); 
//				}
//			}
//		}
		return list;
	}

	@Override
	public Boolean existName(TblCompanyEntity entity) {
		Map map = new HashMap();
		map.put("name", new ParamMatcher(entity.getName()));
		List<TblCompanyEntity> list = crudService.findByMoreFiled(TblCompanyEntity.class, map, true); 
		
		Integer companyId = entity.getCompanyId(); 
		if(companyId == null) {
			//添加
			if(!ListUtil.isEmpty(list)) {
				return true;
			}
		}else {
			//修改
			for(TblCompanyEntity company : list) {
				if(company != null &&  company.getCompanyId() != companyId) {
					return true;
				}
			}
		}
		return false;
	}
	
}
