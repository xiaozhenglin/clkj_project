package com.changlan.user.action;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.pojo.BaseResult;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.StringUtil;
import com.changlan.user.constrant.UserModuleConst;
import com.changlan.user.pojo.LoginUser;
import com.changlan.user.pojo.UserErrorType;
import com.changlan.user.pojo.UserFunctionInfo;
import com.changlan.user.service.IUserFunctionService;
import com.changlan.user.service.IUserRoleService;
import com.changlan.user.vo.UserFunctionInfoVO;

@RestController
@RequestMapping("/admin/user")
public class UserFunctionController extends BaseController{
	
	@Autowired
	IUserFunctionService userFunctionService;
	
	@Autowired
	ICrudService crudService;
	
	@Autowired
	IUserRoleService roleService;
	
	
	@RequestMapping("/func/list")
	public ResponseEntity<Object>  userFunctionlist() throws Exception {  
		TblAdminUserEntity user = super.userIsLogin();
		List<UserFunctionInfoVO> useFunctionVOList = new ArrayList<UserFunctionInfoVO>();
		if(isSuperAdminUser(user.getAdminUserId())) { 
			//如果是管理员，就查出所有的
			List<UserFunctionInfo> findALLPersions = userFunctionService.findALLPersions(); 
			for(UserFunctionInfo userFunctionInfo : findALLPersions) {
				UserFunctionInfoVO vo = new UserFunctionInfoVO(userFunctionInfo);	
				useFunctionVOList.add(vo);
			}
		}else {
			//如果不是超级管理员，那么只能查出自己拥有的权限
			UserFunctionInfo info = userFunctionService.findOne(LoginUser.getCurrentUser());
			UserFunctionInfoVO vo = new UserFunctionInfoVO(info);	
			useFunctionVOList.add(vo);
		}
		return success(useFunctionVOList);
	} 
	
	
	@RequestMapping("/func/save")
	public ResponseEntity<Object>  userAddFunction(String funcIds,String adminUserId) throws Exception {  
		TblAdminUserEntity user = super.userIsLogin();
		if(isSuperAdminUser(user.getAdminUserId())) { 
			List<String> functionIds = StringUtil.stringToList(funcIds, ","); 
			Boolean save = userFunctionService.merge(adminUserId,functionIds);
			if(!save) {
				throw new MyDefineException(UserErrorType.SAVE_ERROR.getCode(), UserErrorType.SAVE_ERROR.getMsg(), false, null);
			}
			return success(UserModuleConst.EDIT_SUCCESS);
		}
		throw new MyDefineException(UserErrorType.ONLY_SUPER_SAVE_OTHER.getCode(), UserErrorType.ONLY_SUPER_SAVE_OTHER.getMsg(), false, null);
	} 
	
	

}
