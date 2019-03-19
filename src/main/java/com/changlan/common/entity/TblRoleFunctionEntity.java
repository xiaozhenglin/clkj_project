package com.changlan.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_ROLE_FUNC")
public class TblRoleFunctionEntity implements Serializable{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ROLE_FUNC_ID", unique = true )
	private Integer roleFuncId;
	
    @Column(name = "FUNC_ID")
	private Integer funcId;
    
    @Column(name = "ROLE_ID")
	private Integer roleId;

	public Integer getRoleFuncId() {
		return roleFuncId;
	}

	public void setRoleFuncId(Integer roleFuncId) {
		this.roleFuncId = roleFuncId;
	}

	public Integer getFuncId() {
		return funcId;
	}

	public void setFuncId(Integer funcId) {
		this.funcId = funcId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
    
    
    

}
