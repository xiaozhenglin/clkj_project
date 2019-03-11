package com.changlan.user.pojo;

import java.util.List;

import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblFunInfoEntity;

public class UserFunctionInfo {
	private TblAdminUserEntity user; //用户信息
	private List<TblFunInfoEntity> functions; //用户的权限信息
	public UserFunctionInfo(TblAdminUserEntity user, List<TblFunInfoEntity> functions) {
		super();
		this.user = user;
		this.functions = functions;
	}
	public UserFunctionInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TblAdminUserEntity getUser() {
		return user;
	}
	public void setUser(TblAdminUserEntity user) {
		this.user = user;
	}
	public List<TblFunInfoEntity> getFunctions() {
		return functions;
	}
	public void setFunctions(List<TblFunInfoEntity> functions) {
		this.functions = functions;
	}
	
	
	
}
