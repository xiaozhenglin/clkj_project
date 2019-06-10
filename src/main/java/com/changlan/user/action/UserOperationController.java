package com.changlan.user.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblUserOperationEntity;
import com.changlan.user.service.IUserOpertaionService;

@RestController
public class UserOperationController extends BaseController{
	
	@Autowired
	private IUserOpertaionService userOperationService;
	
	@RequestMapping("/admimn/user/operation")
	public ResponseEntity<Object>  loginError(){
		Page<Object> result =  userOperationService.findByPage(getPage()); 
		return success(result);
	}
	
	
}
