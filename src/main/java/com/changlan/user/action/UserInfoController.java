package com.changlan.user.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TBLRoleDefineEntity;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblUserGroupEntity;
import com.changlan.common.pojo.BaseResult;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.common.util.UUIDUtil;
import com.changlan.user.constrant.UserModuleConst;
import com.changlan.user.pojo.LoginUser;
import com.changlan.user.pojo.UserDetail;
import com.changlan.user.pojo.UserErrorType;
import com.changlan.user.pojo.UserRoleDetail;
import com.changlan.user.service.IUserInfoService;
import com.changlan.user.service.IUserRoleService;

@RestController
@RequestMapping("/admin/user")
public class UserInfoController extends BaseController{
	
	@Autowired
	ICrudService crudService;
	
	@Autowired
	IUserInfoService userInfoService;
	
	@Autowired
	IUserRoleService userRoleService;
	
	@RequestMapping("/add")
	@Transactional
	public ResponseEntity<Object>  save(TblAdminUserEntity user) throws Exception{ 
		TblAdminUserEntity adminUser = super.userIsLogin();
		Boolean exist = userInfoService.existName(user);
		if(exist){
			throw new MyDefineException(UserErrorType.USER_NAME_EXIST.getCode(), UserErrorType.USER_NAME_EXIST.getMsg(), false, null);
		}
		//只有系统管理员可以添加用户
		if(isSuperAdminUser(adminUser.getAdminUserId())) { 
			user.setAdminUserId(UUIDUtil.getUUID());
			user.setAddTime(new Date());
			user.setRemoveFlage(0); //设置删除标记为未删除
			TblAdminUserEntity save = (TblAdminUserEntity)crudService.update(user, true); 
			if(save == null) {
				throw new MyDefineException(UserErrorType.SAVE_ERROR.getCode(), UserErrorType.SAVE_ERROR.getMsg(), false, null);
			}
			return success(true);
		}
		throw new MyDefineException(UserErrorType.ONLY_SUPER_SAVE_OTHER.getCode(), UserErrorType.ONLY_SUPER_SAVE_OTHER.getMsg(), false, null);
	} 
	
	@RequestMapping("/edit")
	@Transactional
	public ResponseEntity<Object>  edit(TblAdminUserEntity updateUser) throws Exception{
		//只允许管理员和用户修改自己的信息
		TblAdminUserEntity user = super.userIsLogin();
		if(isSuperAdminUser(user.getAdminUserId()) ||  user.getAdminUserId().equalsIgnoreCase(updateUser.getAdminUserId()) ) { 
			Boolean exist = userInfoService.existName(updateUser);
			if(exist){
				throw new MyDefineException(UserErrorType.USER_NAME_EXIST);
			}
			if(updateUser.getUserGroupId()!=null) {
				TblUserGroupEntity userGroup = (TblUserGroupEntity)crudService.get(updateUser.getUserGroupId(), TblUserGroupEntity.class, true);
				updateUser.setUserGroupName(userGroup.getName());
			}
		    if(updateUser.getCompanyId()!=null) {
				TblCompanyEntity company = (TblCompanyEntity)crudService.get(updateUser.getCompanyId(), TblCompanyEntity.class, true);
				updateUser.setCompanyName(company.getName());
		    }
			TblAdminUserEntity save = (TblAdminUserEntity)crudService.update(updateUser, true); 
			if(save == null) {
				throw new MyDefineException(UserErrorType.EDIT_ERROR);
			}
			return success(true);
		}
		throw new MyDefineException(UserErrorType.CANNOT_EDIT_OTHER);
	}
	
	@RequestMapping("/list")
	public ResponseEntity<Object>  list() throws Exception { 
		TblAdminUserEntity user = super.userIsLogin(); //登录用户
		List<UserDetail> userList = userInfoService.userList(user);
		return success(userList);
	}
	
	
	@RequestMapping("/delete")
	@Transactional
	public ResponseEntity<Object>  delete(TblAdminUserEntity entity) throws Exception { 
		TblAdminUserEntity adminUser = super.userIsLogin();
		if(isSuperAdminUser(adminUser.getAdminUserId())) { 
			TblAdminUserEntity find = (TblAdminUserEntity)crudService.get(entity.getAdminUserId(),TblAdminUserEntity.class,true);
			if(find != null) {
				find.setRemoveFlage(1);
				Object update = crudService.update(find, true);
				return success("移除成功");
			}
		}
		return success("移除失败");
	}
	
}
