package com.changlan.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.changlan.common.entity.TblRoleFunctionEntity;
import com.changlan.common.entity.TblUserFunctionEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.user.pojo.RoleFunctionDetail;
import com.changlan.user.service.IRoleFunctionService;
import com.changlan.user.service.IUserRoleService;

@Service
public class RoleFunctionServiceImpl implements IRoleFunctionService {
	
	@Autowired
	ICrudService crudService;
	
	@Autowired
	IUserRoleService roleService;

	@Override
	public Page<RoleFunctionDetail> roleFunction(Pageable page) {
		Page<Object> allByPage = crudService.getAllByPage(TblRoleFunctionEntity.class, page, true); 
		List<RoleFunctionDetail> list = new ArrayList();
		for(Object o : allByPage) {
			TblRoleFunctionEntity entity = (TblRoleFunctionEntity)o;
			RoleFunctionDetail detail = new RoleFunctionDetail(entity);
			list.add(detail);
		}
		return new PageImpl<RoleFunctionDetail>(list, page, allByPage.getTotalElements());
	}

	@Override
	@Transactional
	public Boolean merge(List<String> functionIds, Integer role) {
		//删除旧数据
		removeOld(role);
		for(String funcId : functionIds) {
			Integer id = Integer.parseInt(funcId);
			TblRoleFunctionEntity entity = new TblRoleFunctionEntity();
			entity.setFuncId(id);
			entity.setRoleId(role); 
			crudService.update(entity, true);
		}
		return true;
	}
			
	private void removeOld(Integer role) {
		crudService.deleteBySql("DELETE FROM  TBL_ROLR_FUNC  WHERE ROLE_ID = " +role , true);
	}

	@Override
	public List<RoleFunctionDetail> getByRole(Integer roleId) {
		TblRoleFunctionEntity roleFunction = (TblRoleFunctionEntity)crudService.getAll(TblRoleFunctionEntity.class, true);
		List<RoleFunctionDetail> list = new ArrayList();
		for(Object o : list) {
			TblRoleFunctionEntity entity = (TblRoleFunctionEntity)o;
			RoleFunctionDetail detail = new RoleFunctionDetail(entity);
			list.add(detail);
		}
		return list;
	}

}
