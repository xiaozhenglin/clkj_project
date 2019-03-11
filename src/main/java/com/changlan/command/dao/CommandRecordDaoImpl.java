package com.changlan.command.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.changlan.common.entity.TblCommandRecordEntity;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.StringUtil;

@Repository
public class CommandRecordDaoImpl implements ICommandRecordDao{
	@PersistenceContext
	EntityManager em;

	@Override
	public TblCommandRecordEntity getOneRecordOrderByTime(String registPackage, String receiveMessage) {
		StringBuffer sql = new StringBuffer("SELECT * FROM TBL_COMMAND_RECORD WHERE 1=1 ");
		Map map = new HashMap();
		if(!StringUtil.isEmpty(registPackage)) {
			sql.append("AND POINT_REGIST_PACKAGE = :registPackage ");
			map.put("registPackage", registPackage);
		}
		if(!StringUtil.isEmpty(receiveMessage)) {
			sql.append("AND BACK_CONTENT = :back ");
			map.put("back", receiveMessage);
		}
		em.clear();
		sql.append(" ORDER BY RECORD_TIME DESC ");
		Query createNativeQuery = em.createNativeQuery(sql.toString(),TblCommandRecordEntity.class);
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();  
		while(iterator.hasNext()) {
			Entry<String, String> next = iterator.next(); 
			createNativeQuery.setParameter(next.getKey(), next.getValue()); 
		}
		List<TblCommandRecordEntity> resultList = createNativeQuery.getResultList(); 
		if(!ListUtil.isEmpty(resultList)) {
			return resultList.get(0);
		}
		return null;
	}
}
