package com.changlan.common.util;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.changlan.common.service.ICrudService;

@Component
public class SpringUtil implements ApplicationContextAware {

	public  static ApplicationContext applicationContext ;

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		if (SpringUtil.applicationContext == null) {
			SpringUtil.applicationContext = arg0;
		}
	}

	public static void publishEvent(ApplicationEvent event) {
		applicationContext.publishEvent(event);
	}
	
	// 获取applicationContext
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	// 通过name获取 Bean.
	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}

	// 通过class获取Bean.
	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}

	// 通过name,以及Clazz返回指定的Bean
	public static <T> T getBean(String name, Class<T> clazz) {
		return getApplicationContext().getBean(name, clazz);
	}
	
	public static ICrudService getICrudService() {
		ICrudService bean = getBean(ICrudService.class);
		return bean;
	}
}
