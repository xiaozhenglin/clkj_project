package com.changlan.user.vo;

import java.util.List;

import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblFunInfoEntity;
import com.changlan.common.pojo.BasicInfo;
import com.changlan.user.pojo.UserFunctionInfo;

public class UserFunctionInfoVO {
	private BasicInfo adminUser;
	private List<TblFunInfoEntity> functions;

	public UserFunctionInfoVO(UserFunctionInfo info) {
		TblAdminUserEntity user = info.getUser(); 
		this.adminUser = new BasicInfo(user.getAdminUserId(),user.getName());
		this.functions = info.getFunctions();
	}
	
	public UserFunctionInfoVO() {
		super();
	}

	public BasicInfo getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(BasicInfo adminUser) {
		this.adminUser = adminUser;
	}

	public List<TblFunInfoEntity> getFunctions() {
		return functions;
	}


	public void setFunctions(List<TblFunInfoEntity> functions) {
		this.functions = functions;
	}
	
	
}
