package com.changlan.user.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.StringUtil;
import com.changlan.user.constrant.UserModuleConst;
import com.changlan.user.pojo.LoginUser;
import com.changlan.user.pojo.RoleFunctionDetail;
import com.changlan.user.pojo.UserErrorType;
import com.changlan.user.pojo.UserFunctionInfo;
import com.changlan.user.service.IRoleFunctionService;
import com.changlan.user.service.IUserFunctionService;
import com.changlan.user.service.IUserRoleService;
import com.changlan.user.vo.UserFunctionInfoVO;

@RestController
@RequestMapping("/admin/role/func")
public class RoleFunctionController extends BaseController{
	
	@Autowired
	IRoleFunctionService roleFunctionService;

	@RequestMapping("/list")
	public ResponseEntity<Object>  list() throws Exception {  
		TblAdminUserEntity user = super.userIsLogin();
		List<UserFunctionInfoVO> useFunctionVOList = new ArrayList<UserFunctionInfoVO>();
		if(isSuperAdminUser(user.getAdminUserId())) { 
			Page<RoleFunctionDetail> result = roleFunctionService.roleFunction(getPage());
			return success(result);
		}
		return success(null);
	} 
	
	
	@RequestMapping("/save")
	public ResponseEntity<Object>  save(String funcIds,Integer role) throws Exception {  
		TblAdminUserEntity user = super.userIsLogin();
		if(isSuperAdminUser(user.getAdminUserId())) { 
			List<String> functionIds = StringUtil.stringToList(funcIds, ","); 
			Boolean save = roleFunctionService.merge(functionIds,role);
			if(!save) {
				throw new MyDefineException(UserErrorType.SAVE_ERROR.getCode(), UserErrorType.SAVE_ERROR.getMsg(), false, null);
			}
			return success(UserModuleConst.EDIT_SUCCESS);
		}
		throw new MyDefineException(UserErrorType.ONLY_SUPER_SAVE_OTHER.getCode(), UserErrorType.ONLY_SUPER_SAVE_OTHER.getMsg(), false, null);
	} 
	
}
