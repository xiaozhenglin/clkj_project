package com.changlan.user.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

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
	@Transactional
	public ResponseEntity<Object>  save(TBLRoleDefineEntity role) throws Exception {  
		Boolean exist = roleDefineService.existRoleName(role);
		if(exist) {
			throw new MyDefineException(UserErrorType.NAME_EXIST);
		}
		TBLRoleDefineEntity update = (TBLRoleDefineEntity)crudService.update(role, true); 
		if(update == null) {
			throw new MyDefineException(UserErrorType.SAVE_ERROR);
		}
		return success(update);
	} 
	
	@RequestMapping("/delete")
	@Transactional
	public ResponseEntity<Object>  delete(TBLRoleDefineEntity entity) throws Exception { 
		TblAdminUserEntity adminUser = super.userIsLogin();
		if(isSuperAdminUser(adminUser.getAdminUserId())) { 
			TBLRoleDefineEntity companyEntity = (TBLRoleDefineEntity)crudService.get(entity.getRoleId(),TBLRoleDefineEntity.class,true);
			if(companyEntity != null) {
				Boolean delete = crudService.delete(entity, true);
				return success(delete);
			}
		}
		return success("删除失败");
	}
	
}
