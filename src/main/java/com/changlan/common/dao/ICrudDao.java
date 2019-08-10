package com.changlan.common.dao;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.changlan.common.pojo.ParamMatcher;


public interface ICrudDao {
	
	List<Object> getAll(Class clazz);
	
	Page<Object> getAllByPage(Class clazz,Pageable page);
	
	Object get(Object id,Class<Object> clazz);
	
    List<Object> findByMoreFiled(Class clazz,Map<String,ParamMatcher> map);

    Object update(Object entity);

    Boolean delete(Object entity);

    Object save(Object entity);

	Page findByMoreFiledAndPage(Class clazz, Map map, Pageable page);

	Object findOneByMoreFiled(Class clazz, Map map);

	Boolean deleteBysql(String sql);   

	Boolean updateBysql(String sql);
//	void setDataSource(String beanName); 

    
    
}
