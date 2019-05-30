package com.changlan.common.service.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.changlan.common.dao.ICrudDao;
import com.changlan.common.service.ICrudService;


@Service
public class CrudServiceImpl implements ICrudService{
	
	@Autowired
	private ICrudDao dao;

	@Override
	public List getAll(Class clazz,boolean isPramDataSource) {
		return dao.getAll(clazz);
	}

	@Override
	public Page getAllByPage(Class clazz, Pageable page,boolean isPramDataSource) {
		return dao.getAllByPage(clazz, page);
	}

	@Override
	public Object get(Object id, Class clazz,boolean isPramDataSource) {
		return dao.get(id, clazz);
	}

	@Override
	public List<Object> findByMoreFiled(Class clazz, Map map,boolean isPramDataSource) {
		return dao.findByMoreFiled(clazz, map); 
	}

	@Override
	@Transactional
	public Object update(Object entity,boolean isPramDataSource) {
		return dao.update(entity);
	}

	@Override
	public Boolean delete(Object entity,boolean isPramDataSource) {
		return dao.delete(entity);
	}

	@Override
	@Transactional
	public Boolean save(Object entity,boolean isPramDataSource) {
		return dao.save(entity); 
	}

	@Override
	public Page findByMoreFiledAndPage(Class clazz, Map map, boolean isPramDataSource, Pageable page) {
		return dao.findByMoreFiledAndPage(clazz, map,page);
	}

	@Override
	public Object findOneByMoreFiled(Class clazz, Map map, boolean isPramDataSource) {
		return dao.findOneByMoreFiled(clazz, map) ;
	}

	@Override
	public Boolean deleteBySql(String sql, boolean isPramDataSource) {
		return dao.deleteBysql(sql);
	}

}
