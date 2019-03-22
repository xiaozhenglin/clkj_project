package com.changlan.user.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.changlan.common.entity.TBLUserRoleEntity;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblFunInfoEntity;
import com.changlan.common.pojo.BaseResult;
import com.changlan.common.util.SpringUtil;
import com.changlan.user.config.UserAuthorityUrlConfig;
import com.changlan.user.constrant.UserModuleConst;
import com.changlan.user.pojo.LoginUser;
import com.changlan.user.pojo.RoleFunctionDetail;
import com.changlan.user.pojo.UserFunctionInfo;
import com.changlan.user.pojo.UserRoleDetail;
import com.changlan.user.service.IRoleFunctionService;
import com.changlan.user.service.IUserFunctionService;
import com.changlan.user.service.IUserRoleService;


@Component
public class OriginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest request=(HttpServletRequest)req; 
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
//        System.out.println("==========进入过滤器");
      
    	String requestURI = request.getRequestURI();
    	HttpSession session = request.getSession(); 
    	System.out.println(session.getId()); 
    	if(needVerifyUserPermission(requestURI)){ 
    		//需要验证权限，
    		TblAdminUserEntity user = (TblAdminUserEntity)session.getAttribute(UserModuleConst.userSessionAttributeName); 
    		if(user != null && HaveAuthorityToCome(user,requestURI)) {
            	 //用户登录了而且用户有权限
        		 chain.doFilter(request,response);
             }else {
            	 throw new ServletException("请检查地址是否正确,用户没有登录或者用户没有访问权限");
             }
        }else {
        	//不需要验证权限
        	chain.doFilter(request,response);
        }
    }

    private boolean HaveAuthorityToCome(TblAdminUserEntity user,String requestUrl) {
    	IUserFunctionService service = SpringUtil.getBean(IUserFunctionService.class); 
//    	byRole(user,requestUrl);
    	UserFunctionInfo findAll = service.findOne(user);  
    	List<TblFunInfoEntity> functions = findAll.getFunctions();
    	for(TblFunInfoEntity functionInfo : functions) {
    		if(functionInfo.getAddress().indexOf(requestUrl)>-1) {
    			return true;
    		}
    	}
		return false;
	}

	private boolean byRole(TblAdminUserEntity user, String requestUrl) {
		IUserRoleService userRoleService = SpringUtil.getBean(IUserRoleService.class);
		UserRoleDetail one = userRoleService.getOne(user.getAdminUserId()); 
		TBLUserRoleEntity userRole = one.getUserRole(); 
		IRoleFunctionService rolefunc = SpringUtil.getBean(IRoleFunctionService.class);
		List<RoleFunctionDetail> all = rolefunc.getByRole(userRole.getRoleID()); 
		for(RoleFunctionDetail detail : all) {
			TblFunInfoEntity functionInfo = detail.getFuntion(); 
			if(functionInfo.getAddress().indexOf(requestUrl)>-1) {
    			return true;
    		}
		}
		return false;
	}

	private boolean needVerifyUserPermission(String requestURI) { 
    	String notRequireAuthorityUrl = UserAuthorityUrlConfig.getNotRequireAuthorityUrl(); 
    	if(notRequireAuthorityUrl.indexOf(requestURI) >-1 ) { 
    		return false;
    	}
		return true;
	}

	@Override
    public void destroy() {

    }

}