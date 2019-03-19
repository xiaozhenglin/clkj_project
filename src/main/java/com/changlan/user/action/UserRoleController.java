package com.changlan.user.action;

import java.util.ArrayList;
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
import com.changlan.common.entity.TBLUserRoleEntity;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.pojo.ParamMatcher;
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
			Map map = new HashMap();
			map.put("userId", new ParamMatcher(role.getUserId()));
			List<TBLUserRoleEntity> list = crudService.findByMoreFiled(TBLUserRoleEntity.class, map, true); 
			TBLUserRoleEntity tblUserRoleEntity = list.get(0); 
			tblUserRoleEntity.setRoleID(role.getRoleID()); 
			Object update = crudService.update(tblUserRoleEntity, true); 
			if(update==null){
				throw new MyDefineException(UserErrorType.SAVE_ERROR);
			}
			return success(update);
		}
		return success(null);
	} 
	
}
