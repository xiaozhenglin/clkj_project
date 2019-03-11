package com.changlan.user.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.pojo.BaseResult;
import com.changlan.common.pojo.MatcheType;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.user.constrant.UserModuleConst;
import com.changlan.user.pojo.UserErrorType;
import com.changlan.user.pojo.LoginUser;

@RestController
@RequestMapping("/admin/user")
public class LoginController extends BaseController{
	
	@Autowired
	ICrudService crudService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/login")
	public ResponseEntity<Object>  login(String name,String pass){
		Map map = new HashMap();
		map.put("name", new ParamMatcher(name));
		map.put("pass", new ParamMatcher(pass));
		List<TblAdminUserEntity> list = crudService.findByMoreFiled(TblAdminUserEntity.class, map, true); 
		if(ListUtil.isEmpty(list)) {  
			//没找到抛出异常
			return success(UserErrorType.LOGIN_ERROR.getCode(),UserErrorType.LOGIN_ERROR.getMsg(),false,null); 
		}
		TblAdminUserEntity user = (TblAdminUserEntity)list.get(0);
		addUserInfoToSession(user);
		logger.info("用户登入"+ user.getName());
		return success(true); 
	}
	
	private void addUserInfoToSession(TblAdminUserEntity user) {
		HttpSession session = getSession(); 
		session.setAttribute(UserModuleConst.userSessionAttributeName,user);
	}

	@RequestMapping("/logout")
	public ResponseEntity<Object>  logout(){
		HttpSession session = getSession(); 
		TblAdminUserEntity user = (TblAdminUserEntity)session.getAttribute(UserModuleConst.userSessionAttributeName); 
		if(user!=null) {
			logger.info("用户登出"+ user.getName());
			session.removeAttribute(UserModuleConst.userSessionAttributeName); 
		}
		return success(true);
	}
	
}
