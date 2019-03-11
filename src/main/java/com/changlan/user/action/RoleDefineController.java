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
import com.changlan.common.entity.TblUserFunctionEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.user.constrant.UserModuleConst;
import com.changlan.user.pojo.UserErrorType;

@RestController
@RequestMapping("/admin/role/define")
public class RoleDefineController extends BaseController{
	
	@Autowired
	private ICrudService crudService;
	
	@RequestMapping("/list")
	public ResponseEntity<Object>  list(TBLRoleDefineEntity role) throws Exception {  
		List<Object> all = crudService.getAll(TBLRoleDefineEntity.class, true); 
		return success(all);
	} 
	
	@RequestMapping("/save")
	public ResponseEntity<Object>  save(TBLRoleDefineEntity role) throws Exception {  
		Map map = new HashMap();
		map.put("roleName", new ParamMatcher(role.getRoleName())); 
		List<TBLRoleDefineEntity> list = crudService.findByMoreFiled(TBLRoleDefineEntity.class, map, true);
		if(!ListUtil.isEmpty(list)) {
			throw new MyDefineException(UserErrorType.NAME_EXIST);
		}
		Object update = crudService.update(role, true); 
		if(update==null){
			throw new MyDefineException(UserErrorType.SAVE_ERROR);
		}
		return success(update);
	} 
	
}
