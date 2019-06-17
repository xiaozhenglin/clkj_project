package com.changlan.user.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.changlan.common.entity.TBLRoleDefineEntity;
import com.changlan.common.entity.TBLUserRoleEntity;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblFunInfoEntity;
import com.changlan.common.entity.TblUserOperationEntity;
import com.changlan.common.pojo.BaseResult;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
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
public class OriginFilter   implements Filter {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

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
        logger.info("主机["+host+"进入会话访问]"+session.getId()); 
        if(host.equalsIgnoreCase("192.168.1.251") ) {
        	response.setHeader("Access-Control-Allow-Origin", "http://"+host +":3000"); //
        }else {
        	response.setHeader("Access-Control-Allow-Origin", "http://"+host +":8082"); //
        }
        response.addHeader("Access-Control-Allow-Credentials", "true");//加上这句代码
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
        response.setHeader("Access-Control-Max-Age", "5000");
        response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Origin,Origin,accept,No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,Authorization,Token");
        response.setCharacterEncoding("UTF-8"); 
        response.setContentType("application/json");
     
        logger.info("过滤器 >>>>>>>开始校验参数是否合法");
        checkParamLegal(req.getParameterMap());
        
    	String requestURI = request.getRequestURI();
    	logger.info("过滤器 >>>>>>>开始校验权限 requestURI"+requestURI); 
    	if(needVerifyUserPermission(requestURI)){ 
    		//需要验证权限，
    		TblAdminUserEntity user = (TblAdminUserEntity)session.getAttribute(UserModuleConst.USER_SESSION_ATTRIBUTENAME);
    		if(user == null) {
    			throw new ServletException("用户没有登录");
    		}
    		   
    		if( HaveAuthorityToCome(user,requestURI)) {
    			//用户登录了而且用户有权限
    			//记录用户操作
    			 saveToUserOperation(user,requestURI,request.getRemoteHost());
        		 chain.doFilter(req,res);
             }else {
            	 throw new ServletException("请检查地址是否正确或用户没有访问权限");
             }
        }else {
        	//不需要验证权限
        	chain.doFilter(req,res);
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
		IUserRoleService roleService = SpringUtil.getBean(IUserRoleService.class);
		TBLUserRoleEntity query = new TBLUserRoleEntity();
		query.setUserId(user.getAdminUserId()); 
		List<UserRoleDetail> list = roleService.getAll(query); 
		for(int i = 0;i<list.size();i++){
			TBLRoleDefineEntity roleDefine = list.get(i).getRoleDefine(); 
			if(roleDefine!=null && roleDefine.getRoleName().equalsIgnoreCase("系统管理员")) {
				return true;
			}
		}
		return false;
//		IRoleFunctionService rolefunc = SpringUtil.getBean(IRoleFunctionService.class);
//		List<RoleFunctionDetail> all = rolefunc.getByRole(userRole.getRoleID()); 
//		for(RoleFunctionDetail detail : all) {
//			TblFunInfoEntity functionInfo = detail.getFuntion(); 
//			if(functionInfo.getAddress().indexOf(requestUrl)>-1) {
//    			return true;
//    		}
//		}
//		return false;
	}

	private boolean needVerifyUserPermission(String requestURI) { 
    	String notRequireAuthorityUrl = UserAuthorityUrlConfig.getNotRequireAuthorityUrl(); 
    	if(notRequireAuthorityUrl.indexOf(requestURI) >-1 ) { 
    		return false;
    	}
		return true;
	}
	
	private void checkParamLegal(Map<String, String[]> parameterMap) throws ServletException {
		Iterator<Entry<String, String[]>> iterator = parameterMap.entrySet().iterator(); 
		while(iterator.hasNext()) {
			Entry<String, String[]> next = iterator.next();
			String[] value = next.getValue(); 
			for(int i = 0 ;i <value.length;i++) {
				if(StringUtil.isIllegalStr(value[i])) {
					throw new ServletException("请求参数中包含非法字符" + value[i]);
				}
				if(StringUtil.isIllegalStrContainSql(value[i])) {
					throw new ServletException("请求参数中包含sql注入语句" + value[i]);
				}
			}
		}
	}

	@Override
    public void destroy() {

    }

}