package com.changlan.user.action;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TBLRoleDefineEntity;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblUserGroupEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.user.service.IRoleDefineService;
import com.changlan.user.service.IUserGoupService;

@RestController
@RequestMapping("/admin/user/group")
public class UserGroupController extends BaseController{
	
	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private IUserGoupService userGoupService;
	
	
	@RequestMapping("/list")
	public ResponseEntity<Object>  list(TblUserGroupEntity userGroup) throws Exception {  
		List<TblUserGroupEntity> all = userGoupService.getAll(userGroup); 
		return success(all);
	} 
	
	@RequestMapping("/save")
	public ResponseEntity<Object>  save(TblUserGroupEntity userGroup) throws Exception {  
		Boolean exist = userGoupService.existName(userGroup); 
		if(!exist) {
			Object update = crudService.update(userGroup, true); 
			return success(update);
		}
		return success("添加失败");
	}
	
	
	@RequestMapping("/delete")
	@Transactional
	public ResponseEntity<Object>  delete(TblUserGroupEntity entity) throws Exception { 
		TblAdminUserEntity adminUser = super.userIsLogin();
		if(isSuperAdminUser(adminUser.getAdminUserId())) { 
			TblUserGroupEntity companyEntity = (TblUserGroupEntity)crudService.get(entity.getUserGroupId(),TblUserGroupEntity.class,true);
			if(companyEntity != null) {
				Boolean delete = crudService.deleteBySql("DELETE FROM TBL_USER_GROUP WHERE USER_GROUP_ID = " +entity.getUserGroupId() , true); 
				return success(delete);
			}
		}
		return success("删除失败");
	}
	
}
