package com.changlan.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblFunInfoEntity;
import com.changlan.common.entity.TblUserFunctionEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.user.pojo.LoginUser;
import com.changlan.user.pojo.UserFunctionInfo;
import com.changlan.user.service.IUserFunctionService;
import com.changlan.user.vo.UserFunctionInfoVO;

@Service
public class UserFunctionServiceImpl implements IUserFunctionService{
	@Autowired
	ICrudService crudService;

	@Override
	public UserFunctionInfo findOne(TblAdminUserEntity user) {
		Map map = new HashMap();
		map.put("adminUserId", new ParamMatcher(user.getAdminUserId())); 
		List<TblUserFunctionEntity> list = crudService.findByMoreFiled(TblUserFunctionEntity.class, map, true);
		
		List<TblFunInfoEntity> functions = new ArrayList<TblFunInfoEntity>();
		for(TblUserFunctionEntity userFunction : list) {
        	//获取权限详情
    		map.clear();
    		map.put("funcId", new ParamMatcher(userFunction.getFuncId()));
    		TblFunInfoEntity findOneByMoreFiled = (TblFunInfoEntity)crudService.findOneByMoreFiled(TblFunInfoEntity.class,map,true);
    		functions.add(findOneByMoreFiled);
		}
		return new UserFunctionInfo(user, functions); 
	}

	@Override
	public List<UserFunctionInfo> findALLPersions() {
		//查出所有的用户
		List<Object> all = crudService.getAll(TblAdminUserEntity.class, true); 
		List<UserFunctionInfo> useFunctionList = new ArrayList<UserFunctionInfo>();
		for(Object o : all) {
			TblAdminUserEntity user = (TblAdminUserEntity)o;
			//查询出用户的权限
			UserFunctionInfo info = findOne(user);
			useFunctionList.add(info);
		}
		return useFunctionList;
	}

	
	@Override
	@Transactional
	public Boolean merge(String adminUserId, List<String> functionIds) {
		//删除旧数据
		removeOld(adminUserId);
		for(String funcId : functionIds) {
			Integer id = Integer.parseInt(funcId);
			TblUserFunctionEntity entity = new TblUserFunctionEntity();
			entity.setAdminUserId(adminUserId);
			entity.setFuncId(id); 
			crudService.update(entity, true);
		}
		return true;
	}
	
	private void removeOld(String adminUserId) {
		crudService.deleteBySql("DELETE FROM TBL_USER_FUNCTION WHERE ADMIN_USER_ID = " +adminUserId , true);
	}


}
