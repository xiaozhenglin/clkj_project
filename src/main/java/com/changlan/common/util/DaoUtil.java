package com.changlan.common.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.changlan.common.pojo.ParamMatcher;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

/**
 * Created by DK on 2018/1/17.
 */
@Repository
public class DaoUtil {
    @PersistenceContext
    EntityManager em;

    public int getQueryCount(String sql, Map<String, Object> param) {
        sql = "select count(*) as size from (" + sql + ") as countSize";
        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery = setParam(nativeQuery, param);
        return Integer.parseInt(nativeQuery.getSingleResult().toString());
    }

    public <T> List<T> queryList(String sql, Map<String, Object> param, Class<T> c) {
        Query nativeQuery = em.createNativeQuery(sql.toString(), c);
        nativeQuery = setParam(nativeQuery, param);
        return nativeQuery.getResultList();
    }

    private Query setParam(Query nativeQuery, Map<String, Object> param) {
        if(param == null){
            return nativeQuery;
        }
        List<String> list=new ArrayList<>(param.keySet());
        for (int i=0;i<list.size();i++){
        	ParamMatcher matcher = (ParamMatcher)param.get(list.get(i));
        	Object value = matcher.getValue(); 
        	nativeQuery.setParameter(i+1, value);
        }
        
        return nativeQuery;
    }

    public <T> Page<T> queryPage(String sql, Map<String, Object> param, Class<T> c, Pageable page) {
    	//按条件查出列表
    	sql += getOrderBySql(page);
        Query nativeQuery = em.createNativeQuery(sql, c);
        nativeQuery = setParam(nativeQuery, param);
        int startNum = page.getPageNumber() * page.getPageSize(); //分页从零开始
        nativeQuery.setFirstResult(startNum);
        nativeQuery.setMaxResults(page.getPageSize());
        List<T> resultList = nativeQuery.getResultList();
        
        //查出总条数
        int count = getQueryCount(sql.toString(), param);
        return new PageImpl<T>(resultList, page, count);
    }

    private String getOrderBySql(Pageable page) {
        Sort sort = page.getSort();
        if (sort == null) {
            return "";
        }
        Iterator<Sort.Order> iterator = sort.iterator();
        if (iterator == null || !iterator.hasNext()) {
            return "";
        }
        String sql = "  order by  ";
        while (iterator.hasNext()) {
            Sort.Order next = iterator.next();
            sql += next.getProperty() + " " + next.getDirection().toString();
            if (iterator.hasNext()) {
                sql += ", ";
            }
        }
        return sql;
    }

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put(1, 1);
        map.put(2, 2);
        Set set = map.keySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            if (1 == Integer.parseInt(next.toString())) {
                iterator.remove();
            }
        }
        System.out.println(map.toString());

    }
}
