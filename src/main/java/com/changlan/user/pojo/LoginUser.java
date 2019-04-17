package com.changlan.user.pojo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.user.constrant.UserModuleConst;

public class LoginUser {
//    private static ThreadLocal<TblAdminUserEntity> localUser = new ThreadLocal<>();
// 
//    public static void add(TblAdminUserEntity user) {
//    	localUser.set(user);
//    }
// 
//    public static TblAdminUserEntity getUser() {
//        return localUser.get();
//    }
// 
//    public static void remove() {
//    	localUser.remove();
//    }
//    
    public static  TblAdminUserEntity getCurrentUser() {
    	ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    	if(requestAttributes==null) {
			return null;
		}
    	HttpServletRequest request = requestAttributes.getRequest();
		if(request==null) {
			return null;
		}
		HttpSession session = request.getSession(); 
		TblAdminUserEntity user = (TblAdminUserEntity) session.getAttribute(UserModuleConst.USER_SESSION_ATTRIBUTENAME); 
		return user;
    }
}
