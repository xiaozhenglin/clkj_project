package com.changlan;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.changlan.command.service.impl.CommandRecordServiceImpl;
import com.changlan.netty.NettyConfiguration;
import com.changlan.netty.server.NettyServer;


@SpringBootApplication
@EnableScheduling
public class AdminplatApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(AdminplatApplication.class, args);
		Logger log = LoggerFactory.getLogger(Object.class);
		log.debug("==============================================debug");
		log.info("==============================================info");
		log.error("==============================================error");
		log.warn("==============================================warn");
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		new NettyServer().start();
	}

}
