package com.changlan.user.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.changlan.user.pojo.RoleFunctionDetail;

public interface IRoleFunctionService {
	List<RoleFunctionDetail> getByRole(Integer roleId);

	Page<RoleFunctionDetail> roleFunction(Pageable page);

	Boolean merge(List<String> functionIds, Integer role);   

}
