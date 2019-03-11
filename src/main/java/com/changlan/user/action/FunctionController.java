package com.changlan.user.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblFunInfoEntity;
import com.changlan.common.pojo.BaseResult;
import com.changlan.common.service.ICrudService;
import com.changlan.user.pojo.UserErrorType;

@RestController
@RequestMapping("/admin/func")
public class FunctionController extends BaseController{
	
	@Autowired
	ICrudService crudService;
	
	@RequestMapping("/list")
	public ResponseEntity<Object>  functionList() {
		List<Object> all = crudService.getAll(TblFunInfoEntity.class, true);
		return success(all);
	}
	
	//权限表为后台人员根据接口手动添加数据，不提供接口 
}
