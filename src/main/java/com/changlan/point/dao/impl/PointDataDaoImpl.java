package com.changlan.point.dao.impl;

import java.util.Date;
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
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.util.DateUtil;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.point.dao.IPointDataDao;

@Repository
public class PointDataDaoImpl implements IPointDataDao{
	
	@PersistenceContext
	EntityManager em;

	@Override
	public TblPoinDataEntity getThePenultimateData(Integer pointId, Integer indicatorId) {
		em.clear();
		StringBuffer sql = new StringBuffer("SELECT * FROM TBL_POIN_DATA A WHERE A.VALUE IS NOT NULL ");
		Map map = new HashMap();
		if(pointId!=null) {
			sql.append("AND POINT_ID = :pointId ");
			map.put("pointId", pointId);
		}
		if(indicatorId!=null) {
			sql.append("AND INDICATOR_ID = :indicatorId ");
			map.put("indicatorId", indicatorId);
		}
		sql.append(" ORDER BY RECORD_TIME DESC LIMIT 1,1 ");
		Query createNativeQuery = em.createNativeQuery(sql.toString(),TblPoinDataEntity.class);
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();  
		while(iterator.hasNext()) {
			Entry<String, String> next = iterator.next(); 
			createNativeQuery.setParameter(next.getKey(), next.getValue()); 
		}
		List<TblPoinDataEntity> resultList = createNativeQuery.getResultList(); 
		if(!ListUtil.isEmpty(resultList)) {
			return resultList.get(0);
		}
		return null;
	}

	@Override
	public List<TblPoinDataEntity> getTableData(Date begin, Date end,Integer indicatorId,Integer pointId) {
		em.clear();
		StringBuffer sql = new StringBuffer("SELECT * FROM TBL_POIN_DATA A WHERE A.VALUE IS NOT NULL  ");
		Map map = new HashMap();
		if(indicatorId!=null) {
			sql.append(" AND INDICATOR_ID = :indicatorId ");
			map.put("indicatorId", indicatorId);
		}
		if(pointId!=null) {
			sql.append(" AND POINT_ID = :pointId ");
			map.put("pointId", pointId);
		}
		if(begin!=null && end !=null ) {
			String beginDate = DateUtil.formatDate(begin, "yyyy-MM-dd HH:mm:ss"); 
			String endDate = DateUtil.formatDate(end, "yyyy-MM-dd HH:mm:ss"); 
			sql.append(" AND RECORD_TIME BETWEEN '"+beginDate+"'" + " AND '"+ endDate + "'" );
		}
		sql.append(" ORDER BY RECORD_TIME DESC ");
		Query createNativeQuery = em.createNativeQuery(sql.toString(),TblPoinDataEntity.class);
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();  
		while(iterator.hasNext()) {
			Entry<String, String> next = iterator.next(); 
			createNativeQuery.setParameter(next.getKey(), next.getValue()); 
		}
		List<TblPoinDataEntity> resultList = createNativeQuery.getResultList(); 
		if(!ListUtil.isEmpty(resultList)) {
			return resultList;
		}
		return null;
	}
	
	@Override
	public List<TblPoinDataEntity> getTableDataOne(Date begin, Date end,Integer indicatorId,Integer pointId) {
		em.clear();
		StringBuffer sql = new StringBuffer("SELECT * FROM TBL_POIN_DATA A WHERE A.VALUE IS NOT NULL  ");
		Map map = new HashMap();
		if(indicatorId!=null) {
			sql.append(" AND INDICATOR_ID = :indicatorId ");
			map.put("indicatorId", indicatorId);
		}
		if(pointId!=null) {
			sql.append(" AND POINT_ID = :pointId ");
			map.put("pointId", pointId);
		}
		if(begin!=null && end !=null ) {
			String beginDate = DateUtil.formatDate(begin, "yyyy-MM-dd HH:mm:ss"); 
			String endDate = DateUtil.formatDate(end, "yyyy-MM-dd HH:mm:ss"); 
			sql.append(" AND RECORD_TIME BETWEEN '"+beginDate+"'" + " AND '"+ endDate + "'" );
		}
		sql.append(" ORDER BY RECORD_TIME DESC LIMIT 0 , 1 ");
		Query createNativeQuery = em.createNativeQuery(sql.toString(),TblPoinDataEntity.class);
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();  
		while(iterator.hasNext()) {
			Entry<String, String> next = iterator.next(); 
			createNativeQuery.setParameter(next.getKey(), next.getValue()); 
		}
		List<TblPoinDataEntity> resultList = createNativeQuery.getResultList(); 
		if(!ListUtil.isEmpty(resultList)) {
			return resultList;
		}
		return null;
	}
}
