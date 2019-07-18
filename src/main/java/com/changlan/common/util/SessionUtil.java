package com.changlan.common.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.user.constrant.UserModuleConst;

import io.netty.channel.Channel;

public class SessionUtil {
	public static Map<Object,Object> storage = new ConcurrentHashMap<Object, Object>();
	
	public static HttpSession getSession() {
    	ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		HttpSession session = request.getSession(); 
		return session;
	}
	
}
