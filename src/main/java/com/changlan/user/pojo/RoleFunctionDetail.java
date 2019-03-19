package com.changlan.user.pojo;

import com.changlan.common.entity.TBLRoleDefineEntity;
import com.changlan.common.entity.TblFunInfoEntity;
import com.changlan.common.entity.TblRoleFunctionEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;

public class RoleFunctionDetail {
	private TblRoleFunctionEntity roleFunction;
	private TBLRoleDefineEntity roleDefine;
	private TblFunInfoEntity funtion;
	
	public RoleFunctionDetail(TblRoleFunctionEntity roleFunction) {
		super();
		this.roleFunction = roleFunction;
		this.roleDefine = getRoleDefine(roleFunction.getRoleId());
		this.funtion = getFunction(roleFunction.getFuncId()); 
	}
	
	private TblFunInfoEntity getFunction(Integer funcId) {
		ICrudService crudService = SpringUtil.getICrudService(); 
		return 	(TblFunInfoEntity)crudService.get(funcId, TblFunInfoEntity.class, true);
	}

	private TBLRoleDefineEntity getRoleDefine(Integer roleId) {
		ICrudService crudService = SpringUtil.getICrudService(); 
		return 	(TBLRoleDefineEntity)crudService.get(roleId, TBLRoleDefineEntity.class, true);
	}

	public RoleFunctionDetail() {
		super();
	}

	public TblRoleFunctionEntity getRoleFunction() {
		return roleFunction;
	}
	public void setRoleFunction(TblRoleFunctionEntity roleFunction) {
		this.roleFunction = roleFunction;
	}
	public TBLRoleDefineEntity getRoleDefine() {
		return roleDefine;
	}
	public void setRoleDefine(TBLRoleDefineEntity roleDefine) {
		this.roleDefine = roleDefine;
	}
	public TblFunInfoEntity getFuntion() {
		return funtion;
	}
	public void setFuntion(TblFunInfoEntity funtion) {
		this.funtion = funtion;
	}
	
}
