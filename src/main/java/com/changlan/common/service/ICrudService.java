package com.changlan.common.service;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.changlan.common.pojo.ParamMatcher;


public interface ICrudService {
	 
	Object  get(Object id,Class clazz,boolean isPramDataSource);
	
	Object update(Object entity,boolean isPramDataSource);

	Boolean delete(Object entity,boolean isPramDataSource);
	
	Boolean deleteBySql(String sql,boolean isPramDataSource);

	Boolean save(Object entity,boolean isPramDataSource);
	
    Object findOneByMoreFiled(Class clazz,Map<String,ParamMatcher> map,boolean isPramDataSource);  
	
    List<Object> getAll(Class clazz,boolean isPramDataSource); 
	
    List<Object> findByMoreFiled(Class clazz,Map<String,ParamMatcher> map,boolean isPramDataSource);  
    
	Page<Object> getAllByPage(Class clazz,Pageable page,boolean isPramDataSource);
	
    Page<Object> findByMoreFiledAndPage(Class clazz,Map<String,ParamMatcher> map,boolean isPramDataSource,Pageable page);  


}
