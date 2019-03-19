package com.changlan.user.action;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TBLRoleDefineEntity;
import com.changlan.common.entity.TBLUserRoleEntity;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.user.pojo.UserDetail;
import com.changlan.user.pojo.UserErrorType;
import com.changlan.user.pojo.UserRoleDetail;
import com.changlan.user.service.IUserRoleService;

@RestController
@RequestMapping("/admin/user/role")
public class UserRoleController extends BaseController{

	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private IUserRoleService userRoleService;
	
	@RequestMapping("/list")
	public ResponseEntity<Object>  list(TBLUserRoleEntity role) throws Exception {  
		List<UserRoleDetail> result =  new ArrayList<UserRoleDetail>();
		
		//只有系统管理员能够修改或添加用户的角色
		TblAdminUserEntity adminUser = super.userIsLogin();
		UserDetail detail = new UserDetail(adminUser);
		if(detail!=null && detail.getRoleDefine().getRoleId() == 1) {
			result = userRoleService.getAll(role); 
		}else {
			//只能查询自己的用户角色信息
			result.add(detail);
		}
		return success(result);
	} 
	
	@RequestMapping("/save")
	@Transactional
	public ResponseEntity<Object>  save(TBLUserRoleEntity role) throws Exception {  
		//只有系统管理员能够修改或添加用户的角色
		TblAdminUserEntity adminUser = super.userIsLogin();
		if(isSuperAdminUser(adminUser.getAdminUserId())) {
			//检查是否已经存在
			Boolean existRole = userRoleService.existRole(role); 
			if(existRole) {
				throw new MyDefineException(UserErrorType.USER_ROLE_EXIST);
			}
			Object update = crudService.update(role, true); 
			if(update==null){
				throw new MyDefineException(UserErrorType.SAVE_ERROR);
			}
			return success(update);
		}
		return success(null);
	} 
	
	@RequestMapping("/delete")
	@Transactional
	public ResponseEntity<Object>  delete(TBLUserRoleEntity role) throws Exception {  
		//只有系统管理员能够修改或添加用户的角色
		TblAdminUserEntity adminUser = super.userIsLogin();
		if(isSuperAdminUser(adminUser.getAdminUserId())) {
			Object update = crudService.delete(role, true); 
			if(update==null){
				throw new MyDefineException(UserErrorType.SAVE_ERROR);
			}
			return success(update);
		}
		return success(null);
	} 
	
}
