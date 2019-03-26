package com.changlan.user.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.changlan.common.entity.TblUserOperationEntity;
import com.changlan.common.pojo.BaseResult;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;
import com.changlan.user.config.UserAuthorityUrlConfig;
import com.changlan.user.constrant.UserModuleConst;
import com.changlan.user.pojo.LoginUser;
import com.changlan.user.pojo.RoleFunctionDetail;
import com.changlan.user.pojo.UserFunctionInfo;
import com.changlan.user.pojo.UserRoleDetail;
import com.changlan.user.service.IRoleFunctionService;
import com.changlan.user.service.IUserFunctionService;
import com.changlan.user.service.IUserOpertaionService;
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
        String host = request.getRemoteHost();
        HttpSession session = request.getSession(); 
    	System.out.println("["+host+"]:"+session.getId()); 
        if(host.equalsIgnoreCase("192.168.1.251") ) {
        	response.setHeader("Access-Control-Allow-Origin", "http://"+host +":3000"); //必须要加上端口
        }else if( host.equalsIgnoreCase("192.168.1.199")) {
        	response.setHeader("Access-Control-Allow-Origin", "http://"+host +":8082"); //
        }else {
        	response.setHeader("Access-Control-Allow-Origin", "http://"+host +":8082"); //
        }
        response.setHeader("Access-Control-Allow-Credentials", "true");//加上这句代码
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
        response.setHeader("Access-Control-Max-Age", "5000");
        response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Origin,Origin,accept,No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,Authorization,Token");
//        System.out.println("==========进入过滤器");
    	String requestURI = request.getRequestURI();
    	if(needVerifyUserPermission(requestURI)){ 
    		//需要验证权限，
    		TblAdminUserEntity user = (TblAdminUserEntity)session.getAttribute(UserModuleConst.USER_SESSION_ATTRIBUTENAME); 
    		if(user != null && HaveAuthorityToCome(user,requestURI)) {
    			//用户登录了而且用户有权限
    			//记录用户操作
    			 saveToUserOperation(user,requestURI,request.getRemoteHost());
        		 chain.doFilter(request,response);
             }else {
            	 throw new ServletException("请检查地址是否正确,用户没有登录或者用户没有访问权限");
             }
        }else {
        	//不需要验证权限
        	chain.doFilter(request,response);
        }
    }

    private void saveToUserOperation(TblAdminUserEntity user, String requestURI, String fromIp) {
    	 IUserOpertaionService service = SpringUtil.getBean(IUserOpertaionService.class);
		 TblUserOperationEntity userOperation = new TblUserOperationEntity(null, new Date(), fromIp, user.getAdminUserId(), requestURI);
		 service.save(userOperation);
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