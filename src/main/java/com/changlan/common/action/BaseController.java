package com.changlan.common.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.changlan.common.entity.TBLRoleDefineEntity;
import com.changlan.common.entity.TBLUserRoleEntity;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblFunInfoEntity;
import com.changlan.common.entity.TblUserOperationEntity;
import com.changlan.common.pojo.BaseResult;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.user.config.UserAuthorityUrlConfig;
import com.changlan.user.constrant.UserModuleConst;
import com.changlan.user.pojo.LoginUser;
import com.changlan.user.pojo.UserErrorType;
import com.changlan.user.pojo.UserRoleDetail;
import com.changlan.user.service.IUserOpertaionService;
import com.changlan.user.service.IUserRoleService;

import io.netty.handler.codec.http2.Http2FrameLogger.Direction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class BaseController {
    private Logger log = LoggerFactory.getLogger(BaseController.class);

    private final String SORT_NAME = "sort";
    private final String PAGE_NAME = "p";
    private final String SIZE_NAME = "len";
    private final int DEFAULT_PAGE = 0;
    private final char DESC = '-';
    private final int DEFAULT_SIZE = 10;
    
    protected Pageable getPageAndOrderBy(String orderDescProperty) {
    	 HttpServletRequest reqeust = getReqeust();
         int page = DEFAULT_PAGE;
         if (reqeust.getParameter(PAGE_NAME) != null) {
             page = Integer.parseInt(reqeust.getParameter(PAGE_NAME));
         }

         int size = DEFAULT_SIZE;
         if (reqeust.getParameter(SIZE_NAME) != null) {
             size = Integer.parseInt(reqeust.getParameter(SIZE_NAME));
         }

         Sort sort = null;
         List<Sort.Order> orders = new ArrayList<>();
         orders.add(new Sort.Order(Sort.Direction.DESC, orderDescProperty));
         sort = new Sort(orders);
         return new PageRequest(page, size, sort);
    }

    protected Pageable getPage() {
        HttpServletRequest reqeust = getReqeust();
        int page = DEFAULT_PAGE;
        if (reqeust.getParameter(PAGE_NAME) != null) {
            page = Integer.parseInt(reqeust.getParameter(PAGE_NAME));
        }

        int size = DEFAULT_SIZE;
        if (reqeust.getParameter(SIZE_NAME) != null) {
            size = Integer.parseInt(reqeust.getParameter(SIZE_NAME));
        }

        Sort sort = null;
        if (reqeust.getParameter(SORT_NAME) != null) {
        	// sort=-name|-age    name|age
            List<String> sortValue = Arrays.asList(reqeust.getParameter(SORT_NAME).split("\\|"));
            List<Sort.Order> orders = new ArrayList<>();
            sortValue.forEach(new Consumer<String>() {
                @Override
                public void accept(String s) {
                    Sort.Direction direction = Sort.Direction.ASC;
                    if (DESC == s.charAt(0)) {
                        direction = Sort.Direction.DESC;
                        s = s.substring(1);
                    }
                    orders.add(new Sort.Order(direction, s));
                }
            });
            sort = new Sort(orders);
        }
        return new PageRequest(page, size, sort);
    }

    protected HttpServletRequest getReqeust() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    protected HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    }
    
    public HttpSession getSession() {
    	HttpServletRequest request = getReqeust(); 
		HttpSession session = request.getSession(); 
		return session;
    }
    
    
	public ResponseEntity<Object> success(String code, String msg, Boolean success, Object data){
		BaseResult result = new BaseResult(code, msg, success, data);
		saveToUserOperation("code");
		return new ResponseEntity(result,HttpStatus.OK); 
	}
	
	public ResponseEntity<Object> success(Object data){
		BaseResult result = new BaseResult("200", "success", true, data);
		saveToUserOperation("200");
		return new ResponseEntity(result,HttpStatus.OK); 
	}
	
	private boolean needVerifyUserPermission(String requestURI) { 
    	String notRequireAuthorityUrl = UserAuthorityUrlConfig.getNotRequireAuthorityUrl(); 
    	if(notRequireAuthorityUrl.indexOf(requestURI) >-1 ) { 
    		return false;
    	}
		return true;
	}
	
	private void saveToUserOperation(String code) {
	   	 IUserOpertaionService service = SpringUtil.getBean(IUserOpertaionService.class);
	   	 HttpSession session = getSession();
	   	 HttpServletRequest request = getReqeust();
	   	 String requestURI = request.getRequestURI();
	   	 String fromIp = request.getRemoteHost();
	   	 TblAdminUserEntity user = (TblAdminUserEntity)session.getAttribute(UserModuleConst.USER_SESSION_ATTRIBUTENAME);
	   	 ICrudService crudService = SpringUtil.getBean(ICrudService.class);
	   	 Map map = new HashMap();
	   	 map.clear();
		 map.put("address", new ParamMatcher(requestURI));
	   	// TblFunInfoEntity tblFunInfo = (TblFunInfoEntity)crudService.get(requestURI, TblFunInfoEntity.class, true);
		 
	   	 if(needVerifyUserPermission(requestURI)){	   		 
	   		TblFunInfoEntity tblFunInfo = (TblFunInfoEntity)crudService.findOneByMoreFiled(TblFunInfoEntity.class,map,true);
		   	 String funcName = tblFunInfo.getFuncName();
		   	 String isSuccess ="";
		   	 String operationType = "";
		   	 if(code.equals("200")) {
			   	 isSuccess = "成功";
			   	 operationType = "正常";
		   	 }else {
		   		 isSuccess = "失败";
			   	 operationType = "异常";
		   	 }
		   	 String curdName = tblFunInfo.getFun_category();
		   	 TblUserOperationEntity userOperation = new TblUserOperationEntity();
	   		 userOperation = new TblUserOperationEntity(null, new Date(), fromIp, LoginUser.map.get(UserModuleConst.USER_SESSION_ATTRIBUTENAME).getAdminUserId(), requestURI,funcName,isSuccess,operationType,curdName);
	   		 service.save(userOperation);
	   	 }else {
	   		 String funcName ="数据库菜单表未知";
			 String curdName ="查询";
			 String isSuccess ="";
		   	 String operationType = "";					   	 
		   	 if(code.equals("200")) {
			   	 isSuccess = "成功";
			   	 operationType = "正常";
		   	 }else {
		   		 isSuccess = "失败";
			   	 operationType = "异常";
		   	 }
		   	 
		   	 TblUserOperationEntity userOperation = new TblUserOperationEntity();
	   		 userOperation = new TblUserOperationEntity(null, new Date(), fromIp, "system", requestURI,funcName,isSuccess,operationType,curdName);
	   		 service.save(userOperation);
	   	 }
		
	}
	
//	token 方式
//	public TblAdminUserEntity getLoginUser() throws MyDefineException { 
//		String userId = getReqeust().getHeader("token");
//		if(StringUtil.isEmpty(userId)) { 
//			throw new MyDefineException(UserErrorType.USER_NOT_LOGIN.getCode(), UserErrorType.USER_NOT_LOGIN.getMsg(), false, null);  
//		}
//		ICrudService crudService = SpringUtil.getICrudService();
//		TblAdminUserEntity user = (TblAdminUserEntity)crudService.get(userId, TblAdminUserEntity.class, true); 
//		return user;
//	}
	
	//通过会话方式获取
	public TblAdminUserEntity userIsLogin() throws Exception  {   
		TblAdminUserEntity user = LoginUser.getCurrentUser(); 
		if(user == null) {
			throw new MyDefineException(UserErrorType.USER_NOT_LOGIN.getCode(), UserErrorType.USER_NOT_LOGIN.getMsg(), false, null);  
		}
		return user;
	}
	
	public void saveSessionAttribute(String attributeName,Integer id) {
		HttpSession	session = getSession(); 
		session.setAttribute(attributeName, id);
	}
	
	public Boolean isSuperAdminUser(String adminUserId) {
		IUserRoleService roleService = SpringUtil.getBean(IUserRoleService.class);
		TBLUserRoleEntity query = new TBLUserRoleEntity();
		query.setUserId(adminUserId); 
		List<UserRoleDetail> list = roleService.getAll(query); 
		for(int i = 0;i<list.size();i++){
			TBLRoleDefineEntity roleDefine = list.get(i).getRoleDefine(); 
			if(roleDefine!=null && roleDefine.getRoleName().indexOf("管理员")>-1) {
				return true;
			}
		}
		return false;
	}
	
}
