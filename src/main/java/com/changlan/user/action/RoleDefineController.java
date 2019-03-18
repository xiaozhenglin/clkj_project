package com.changlan.user.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TBLRoleDefineEntity;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.entity.TblUserFunctionEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.user.constrant.UserModuleConst;
import com.changlan.user.pojo.UserErrorType;
import com.changlan.user.service.IRoleDefineService;

@RestController
@RequestMapping("/admin/role/define")
public class RoleDefineController extends BaseController{
	
	@Autowired
	private ICrudService crudService;
	
	@Autowired
	IRoleDefineService roleDefineService;
	
	@RequestMapping("/list")
	public ResponseEntity<Object>  list(TBLRoleDefineEntity role) throws Exception {  
		List<Object> all = crudService.getAll(TBLRoleDefineEntity.class, true); 
		return success(all);
	} 
	
	@RequestMapping("/save")
	public ResponseEntity<Object>  save(TBLRoleDefineEntity role) throws Exception {  
		Boolean exist = roleDefineService.existGroupName(role);
		if(exist) {
			throw new MyDefineException(PoinErrorType.COMPANY_GROUP_NAME_EXIST.getCode(), PoinErrorType.COMPANY_GROUP_NAME_EXIST.getName(), false, null);
		}
		TBLRoleDefineEntity update = (TBLRoleDefineEntity)crudService.update(role, true); 
		if(update == null) {
			throw new MyDefineException(PoinErrorType.SAVE_EROOR.getCode(), PoinErrorType.SAVE_EROOR.getName(), false, null);
		}
		return success(update);
	} 
	
}
