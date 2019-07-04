package com.changlan.user.action;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.encoders.Hex;
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
import com.changlan.common.pojo.SM2KeyPair;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.FastjsonUtil;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.SM2Util;
import com.changlan.common.util.StringUtil;
import com.changlan.common.util.VerifyCodeUtil;
import com.changlan.user.constrant.UserModuleConst;
import com.changlan.user.pojo.UserErrorType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.changlan.user.pojo.LoginUser;

@RestController
@RequestMapping("/admin/user")
public class LoginController extends BaseController{
	
	@Autowired
	ICrudService crudService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//未加入权限表
	@RequestMapping("/get/public/key")
	public ResponseEntity<Object>  getPublicKey(){
		HttpSession session = getSession(); 
	    SM2Util sm2 = new SM2Util();
        //公钥
        SM2KeyPair keyPair = sm2.generateKeyPair();
        ECPoint publicKey = keyPair.getPublicKey();
        
		session.setAttribute(UserModuleConst.USER_GENERATE_KEY,keyPair);
		Object attribute = session.getAttribute(UserModuleConst.USER_GENERATE_KEY); 
		logger.info("用户获取公钥"+ Hex.toHexString(publicKey.getEncoded()));
		
		return success(Hex.toHexString(publicKey.getEncoded()));
	}
	
	@RequestMapping("/login")
	public ResponseEntity<Object>  login(String name,String pass,String verifyCode){
		Map map = new HashMap();
		map.put("name", new ParamMatcher(name));
		map.put("pass", new ParamMatcher(pass));
		map.put("removeFlage", new ParamMatcher(0)); //被删除的将无法登陆
		HttpSession session = getSession(); 
		String generalCode = ((String) session.getAttribute("verifyCode")); 
		//验证码为空
		if(verifyCode == null || "".equals(verifyCode.trim())){
			return success(UserErrorType.VERIFIED_NULL.getCode(),UserErrorType.VERIFIED_NULL.getMsg(),false,null); 
		}
		//不是生成的验证码报 验证失败错误
		if(!(verifyCode.trim()).equalsIgnoreCase((generalCode.trim()))){
			return success(UserErrorType.VERIFIED_ERROR.getCode(),UserErrorType.VERIFIED_ERROR.getMsg(),false,null); 
		}
		LocalDateTime time = LocalDateTime.now();
		LocalDateTime codeTime = ((LocalDateTime) session.getAttribute("codeTime")); 
		if(time.compareTo(codeTime) > 0) { //验证码失效
			return success(UserErrorType.VERIFIED_TIMEOUT_ERROR.getCode(),UserErrorType.VERIFIED_TIMEOUT_ERROR.getMsg(),false,null);
		}
		
		List<TblAdminUserEntity> list = crudService.findByMoreFiled(TblAdminUserEntity.class, map, true); 
		if(ListUtil.isEmpty(list)) {  
			//没找到抛出异常
			return success(UserErrorType.LOGIN_ERROR.getCode(),UserErrorType.LOGIN_ERROR.getMsg(),false,null); 
		}
		TblAdminUserEntity user = (TblAdminUserEntity)list.get(0);
		addUserInfoToSession(user);
		logger.info("用户登入"+ user.getName());
		return success(user.getAdminUserId()); 
	}
	
	private void addUserInfoToSession(TblAdminUserEntity user) {
		HttpSession session = getSession(); 
//		session.setMaxInactiveInterval(1*60);//设置无操作60秒后失效
		session.setAttribute(UserModuleConst.USER_SESSION_ATTRIBUTENAME,user);
		LoginUser.map.put(user.getAdminUserId(), user);
	}

	@RequestMapping("/logout")
	public ResponseEntity<Object>  logout(){
		HttpSession session = getSession(); 
		TblAdminUserEntity user = (TblAdminUserEntity)session.getAttribute(UserModuleConst.USER_SESSION_ATTRIBUTENAME); 
		if(user!=null) {
			session.removeAttribute(UserModuleConst.USER_SESSION_ATTRIBUTENAME); 
			logger.info("用户登出"+ user.getName());
			LoginUser.map.remove(user.getAdminUserId(),user);
		}
		return success(true);
	}
	
	@RequestMapping("/getImage")
	public ResponseEntity<Object>  getImage() throws IOException{
		HttpSession session = getSession(); 
		HttpServletResponse response = getResponse();
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		String verifyCode = VerifyCodeUtil.generateVerifyCode(4,"");
        
		logger.info("生成的验证码为："+ verifyCode.toUpperCase());
		session.removeAttribute("verifyCode");
		session.removeAttribute("codeTime");

		session.setAttribute("verifyCode", verifyCode.toUpperCase());
		LocalDateTime time = LocalDateTime.now().plusSeconds(60);//增加60秒时间超时
		session.setAttribute("codeTime", time);
		// 生成图片
		int w = 100, h = 30;
	    OutputStream out = response.getOutputStream();
		VerifyCodeUtil.outputImage(w, h, out, verifyCode);		
		return success(true);
	}
	
}
