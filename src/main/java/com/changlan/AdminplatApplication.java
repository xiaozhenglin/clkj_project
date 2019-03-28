package com.changlan;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.changlan.command.service.impl.CommandRecordServiceImpl;
import com.changlan.common.util.SpringUtil;
import com.changlan.netty.pojo.MyTask;
import com.changlan.netty.pojo.NettyConfiguration;
import com.changlan.netty.server.NettyServer;
import com.changlan.netty.service.INettyService;


@SpringBootApplication
@EnableScheduling
@EnableCaching
public class AdminplatApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(AdminplatApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		new NettyServer().start(); //启动netty服务器
// 		INettyService nettyService = SpringUtil.getBean(INettyService.class);  // 启动循环发送指令任务
//		nettyService.task();
	}

}
