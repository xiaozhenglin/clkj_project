package com.changlan.common.action;
 
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.changlan.common.pojo.BaseResult;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.user.filter.OriginFilter;
import com.changlan.user.pojo.UserErrorType;
 
 
/**
 * @author xiaozl
 * 统一异常类处理 
 */
@ControllerAdvice
public class MyExceptionHandler {
	
	private Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);
 
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<Object> hander(Exception e){ 
    	BaseResult baseReult  = null;
    	if(e instanceof MyDefineException) {
    		//使用自定的返回值进行返回
    		baseReult = new BaseResult((MyDefineException)e);
    	}else {
    		baseReult = new BaseResult("0000",e.getMessage(),false,e); 
    	}
    	return new ResponseEntity<Object>(baseReult,HttpStatus.OK);
    }
    
//    @ExceptionHandler(value = {Throwable.class})
//    public ResponseEntity<Object> hander2(Throwable e){
//    	if(e instanceof ServletException) {
//    		ServletException ex = 	(ServletException)e;
//    		if(ex.getMessage().equalsIgnoreCase(UserErrorType.NO_AUTHORITY.getMsg())) {
//    			BaseResult baseReult  = new BaseResult(UserErrorType.NO_AUTHORITY.getCode(),UserErrorType.NO_AUTHORITY.getMsg(),false,null);
//    			return new ResponseEntity<Object>(baseReult,HttpStatus.OK);
//    		}
//    	}
//		return new ResponseEntity<Object>(e,HttpStatus.OK);
//    }
}
