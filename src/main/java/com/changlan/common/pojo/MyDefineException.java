package com.changlan.common.pojo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblFunInfoEntity;
import com.changlan.common.entity.TblUserOperationEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.user.config.UserAuthorityUrlConfig;
import com.changlan.user.constrant.UserModuleConst;
import com.changlan.user.pojo.LoginUser;
import com.changlan.user.pojo.UserErrorType;
import com.changlan.user.service.IUserOpertaionService;

public class MyDefineException extends Exception{
	
	private Boolean success;
	private String code;
	private String msg;
	private Object data;
	
	public MyDefineException(String code, String msg, Boolean success, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.success = success;
		this.data = data;
		saveToUserOperation(code);
	}
	
	
	public MyDefineException(PoinErrorType type) {
		super();
		this.success = false;
		this.code = type.getCode();
		this.msg = type.getName();
		this.data = null;
		saveToUserOperation(type.getCode());
	}

	public MyDefineException(UserErrorType result) {
		this.success= false;
		this.code = result.getCode();
		this.msg = result.getMsg();
		this.data = null;
		saveToUserOperation(result.getCode());
	}

	public MyDefineException() {
		super();
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

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
