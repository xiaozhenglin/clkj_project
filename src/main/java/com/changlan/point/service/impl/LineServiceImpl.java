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
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.point.pojo.CompanyDetail;
import com.changlan.point.pojo.LineDetail;
import com.changlan.point.service.ILineService;
import com.changlan.user.pojo.LoginUser;

@Service
public class LineServiceImpl implements ILineService{
	@Autowired
	private ICrudService crudService;
	
	
	@Override
	public Boolean existName(TblLinesEntity entity) {
		Map map = new HashMap();
		map.put("lineName", new ParamMatcher(entity.getLineName()));
		List<TblLinesEntity> list = crudService.findByMoreFiled(TblLinesEntity.class, map, true); 
		
		Integer lineId = entity.getLineId(); 
		if(lineId == null) {
			//添加
			if(!ListUtil.isEmpty(list)) {
				return true;
			}
		}else {
			//修改
			for(TblLinesEntity line : list) {
				if(line != null &&  line.getLineId() != lineId) {
					return true;
				}
			}
		}
		return false;
	}


	@Override
	public List<LineDetail> getAll(TblLinesEntity entity) {
		List<LineDetail> list = new ArrayList<LineDetail>();
		Map map = new HashMap();
		if(entity.getCompanyId()!= null) {
			map.put("companyId", new ParamMatcher(entity.getCompanyId()));
		}
		if(entity.getLineId()!=null) {
			map.put("lineId", new ParamMatcher(entity.getLineId()));
		}
		List<Object> all =  crudService.findByMoreFiled(TblLinesEntity.class, map, true);
		
		//根据用户权限二次筛选  去除掉用户不能查看的线路
		TblAdminUserEntity user = LoginUser.getCurrentUser(); 
		String lineIdS = user.getLineId(); 
		if(StringUtil.isNotEmpty(lineIdS)) {
			List<String> stringToList = StringUtil.stringToList(lineIdS); 
			for(Object o : all) {
				TblLinesEntity line = (TblLinesEntity)o;
				Integer lineId = line.getLineId();
				//包含可查看范围
				if(!stringToList.contains(lineId)) {
					all.remove(all.indexOf(line)); 
				}
			}
		}
		
		//封装公司线路信息
		for(Object o : all) {
			TblLinesEntity line = (TblLinesEntity)o;
			LineDetail detail = new LineDetail(line);
			list.add(detail);
		}
		return list;
	}

}
