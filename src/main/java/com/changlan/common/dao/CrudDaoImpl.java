package com.changlan.common.dao;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.changlan.common.pojo.MatcheType;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.util.DaoUtil;
import com.changlan.common.util.DateUtil;
import com.changlan.common.util.SpringUtil;




@Repository
public class CrudDaoImpl implements ICrudDao{
	
	public Logger log = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private DaoUtil util;
	
	//根据类名获取注解表名称
	private String getTableNameByClass(Class clazz) {
		Table annotation = (Table) clazz.getAnnotation(Table.class); 
		return annotation.name().toUpperCase();
	}
	
	//根据属性名获取注解列名称
	private String getColumnNameByField(Class clazz,String fieldName) {
		Field declaredField;
		try {
			declaredField = clazz.getDeclaredField(fieldName);
			Column column=declaredField.getAnnotation(Column.class);
			return column.name().toUpperCase();
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private EntityManager getEntityManage() {
		return this.em;
	}
	


	@Override
	public List<Object> getAll(Class clazz) {
		EntityManager em = getEntityManage();
		em.clear();
		String sqlString = "select * from "+getTableNameByClass(clazz);
		List resultList = em.createNativeQuery(sqlString, clazz).getResultList();
		return resultList;
	}
	
	@Override
	public PageImpl getAllByPage(Class clazz, Pageable page) {
		EntityManager em = getEntityManage();
		em.clear();
		String sqlString = "select * from "+getTableNameByClass(clazz);
		PageImpl queryPage = (PageImpl) util.queryPage(sqlString, null, clazz, page);
		return queryPage;
	}
	
	@Override
	public Object get(Object id, Class clazz) {
		EntityManager em = getEntityManage();
		em.clear();
		Object find = em.find(clazz, id);
		return find;
	}
	
	@Override
	public List<Object> findByMoreFiled(Class clazz, Map params) {
		EntityManager em = getEntityManage();
		em.clear();
	    String sql=" SELECT * FROM "+getTableNameByClass(clazz)+"  WHERE  1=1 ";
        List<String> list=new ArrayList<>(params.keySet());
        for (int i=0;i<list.size();i++){
        	ParamMatcher matcher = (ParamMatcher)params.get(list.get(i));
        	MatcheType type = matcher.getType(); 
        	switch (type) {
			case EQUALS:
	        	sql+=" and "+getColumnNameByField(clazz,list.get(i))+" = :" +list.get(i); 
				break;
			case EXIST:
	        	sql+=" and "+getColumnNameByField(clazz,list.get(i))+" EXIST :" +list.get(i); 
				break;
			case LIKE:
	        	sql+=" and "+getColumnNameByField(clazz,list.get(i))+" like '%' :" +list.get(i) + " '%'";
				break;
			case BETWEEN:
				String begin = DateUtil.formatDate(matcher.getBegin(),"yyyy-MM-dd HH:mm:ss");
				String end = DateUtil.formatDate(matcher.getEnd(),"yyyy-MM-dd HH:mm:ss");
				sql+=" AND "+getColumnNameByField(clazz,list.get(i))+" BETWEEN '" +begin + "' AND '"+end + "' ";
				break;
			case NOT_EQUALS:
				sql+=" and "+getColumnNameByField(clazz,list.get(i))+" != :"+list.get(i); 
				break;
			default:
				break;
			}
        }
        
        Query query=em.createNativeQuery(sql,clazz);
        for (int i=0;i<list.size();i++){
        	ParamMatcher matcher = (ParamMatcher)params.get(list.get(i));
//        	System.out.println(list.get(i));
        	Object value = matcher.getValue(); 
//        	System.out.println(value);
        	
        	MatcheType type = matcher.getType(); 
        	if(type!=MatcheType.BETWEEN ) {
        		query.setParameter(list.get(i), value);
        	}
        }
//        System.out.println(sql);
        List<Object> listResult= query.getResultList();
        return listResult;
	}
	

    @Override
    public Object save(Object entity){
		EntityManager em = getEntityManage(); 
		em.flush();
    	em.clear();
        boolean flag=false;
        try {
        	em.persist(entity);
//            flag=true;
        	return entity;
        }catch (Exception e){
        	System.out.println(e);
        	log.info("---------------保存出错---------------");
        }
        return null;
    }

    @Override
    public Object update(Object entity) {
		EntityManager em = getEntityManage();
		em.flush();
    	em.clear();
        try {
            Object merge = em.merge(entity); 
            em.flush();
            return merge;
        } catch (Exception e) {
        	System.out.println(e);
        	log.info("---------------更新出错---------------");
        }
        return null;
    }
	
    @Override
    public Boolean delete(Object entity) {
		EntityManager em = getEntityManage();
		em.flush();
    	em.clear();
        boolean flag=false;
        try {
        	em.remove(em.merge(entity));
    		em.flush();
            flag=true;
        }catch (Exception e){
        	System.out.println(e); 
        	log.info("---------------删除出错---------------");
        }
        return flag;
    }

	@Override
	public Page findByMoreFiledAndPage(Class clazz, Map params, Pageable page) {
		EntityManager em = getEntityManage();
		em.clear();
	    String sql=" SELECT * FROM "+getTableNameByClass(clazz)+"  WHERE  1=1 ";
        List<String> list=new ArrayList<>(params.keySet());
        for (int i=0;i<list.size();i++){
        	ParamMatcher matcher = (ParamMatcher)params.get(list.get(i));
        	MatcheType type = matcher.getType(); 
        	switch (type) {
			case EQUALS:
	        	sql+=" and "+getColumnNameByField(clazz,list.get(i))+" = :"+list.get(i); 
				break;
			case EXIST:
	        	sql+=" and "+getColumnNameByField(clazz,list.get(i))+" EXIST :"+list.get(i); 
				break;
			case LIKE:
	        	sql+=" and "+getColumnNameByField(clazz,list.get(i))+" like '%' :"+list.get(i) + " '%'  ";
				break;
			case BETWEEN:
				String begin = DateUtil.formatDate(matcher.getBegin(),"yyyy-MM-dd HH:mm:ss");
				String end = DateUtil.formatDate(matcher.getEnd(),"yyyy-MM-dd HH:mm:ss");
				sql+=" AND "+getColumnNameByField(clazz,list.get(i))+" BETWEEN '" +begin + "' AND '"+end + "'";
				break;
			case NOT_EQUALS:
				sql+=" and "+getColumnNameByField(clazz,list.get(i))+" != :"+list.get(i); 
				break;
			default:
				break;
			}
        }
		PageImpl queryPage = (PageImpl) util.queryPage(sql, params, clazz, page);
        return queryPage;
	}

	@Override
	public Object findOneByMoreFiled(Class clazz, Map params) {
		EntityManager em = getEntityManage();
		em.clear();
	    String sql=" SELECT * FROM "+getTableNameByClass(clazz)+"  WHERE  1=1 ";
        List<String> list=new ArrayList<>(params.keySet());
        for (int i=0;i<list.size();i++){
        	ParamMatcher matcher = (ParamMatcher)params.get(list.get(i));
        	MatcheType type = matcher.getType(); 
        	switch (type) {
			case EQUALS:
	        	sql+=" and "+getColumnNameByField(clazz,list.get(i))+" = :" +list.get(i); 
				break;
			case EXIST:
	        	sql+=" and "+getColumnNameByField(clazz,list.get(i))+" EXIST :" +list.get(i); 
				break;
			case LIKE:
	        	sql+=" and "+getColumnNameByField(clazz,list.get(i))+" like '%' :" +list.get(i) + "'%'";
				break;
			case BETWEEN:
				String begin = DateUtil.formatDate(matcher.getBegin(),"yyyy-MM-dd HH:mm:ss");
				String end = DateUtil.formatDate(matcher.getEnd(),"yyyy-MM-dd HH:mm:ss");
				sql+=" AND "+getColumnNameByField(clazz,list.get(i))+" BETWEEN '" +begin + "' AND '"+end + "'";
				break;
			default:
				break;
			}
        }
        
        Query query=em.createNativeQuery(sql,clazz);
        for (int i=0;i<list.size();i++){
        	ParamMatcher matcher = (ParamMatcher)params.get(list.get(i));
        	Object value = matcher.getValue(); 
        	MatcheType type = matcher.getType(); 
        	if(type!=MatcheType.BETWEEN ) {
        		query.setParameter(list.get(i), value);
        	}
        }
        Object result= query.getSingleResult();
        return result;
	}

	@Override
	public Boolean deleteBysql(String sql) {
		EntityManager em = getEntityManage();
		em.clear(); 
		int executeUpdate = em.createNativeQuery(sql).executeUpdate();
		return executeUpdate>0?true:false;
	}

    
}
