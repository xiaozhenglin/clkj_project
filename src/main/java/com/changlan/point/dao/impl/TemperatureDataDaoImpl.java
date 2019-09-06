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
import com.changlan.common.entity.TblTemperatureDTSDataEntity;
import com.changlan.common.entity.TblTemperatureDataEntity;
import com.changlan.common.util.DateUtil;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.StringUtil;

import com.changlan.point.dao.IPointDataDao;
import com.changlan.point.dao.ITemperatureDataDao;
import com.changlan.point.pojo.TemperatureDtsQuery;

@Repository
public class TemperatureDataDaoImpl implements ITemperatureDataDao{
	
	@PersistenceContext
	EntityManager em;

	@Override
	public TblTemperatureDataEntity getThePenultimateData(Integer pointId, Integer indicatorId) {
		em.clear();
		StringBuffer sql = new StringBuffer("SELECT * FROM TBL_TEMPERATURE_DATA A WHERE A.VALUE IS NOT NULL ");
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
		Query createNativeQuery = em.createNativeQuery(sql.toString(),TblTemperatureDataEntity.class);
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();  
		while(iterator.hasNext()) {
			Entry<String, String> next = iterator.next(); 
			createNativeQuery.setParameter(next.getKey(), next.getValue()); 
		}
		List<TblTemperatureDataEntity> resultList = createNativeQuery.getResultList(); 
		if(!ListUtil.isEmpty(resultList)) {
			return resultList.get(0);
		}
		return null;
	}

	@Override
	public List<TblTemperatureDataEntity> getTableData(Date begin, Date end,Integer indicatorId,Integer pointId) {
		em.clear();
		StringBuffer sql = new StringBuffer("SELECT * FROM TBL_TEMPERATURE_DATA A WHERE A.VALUE IS NOT NULL  ");
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
		Query createNativeQuery = em.createNativeQuery(sql.toString(),TblTemperatureDataEntity.class);
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();  
		while(iterator.hasNext()) {
			Entry<String, String> next = iterator.next(); 
			createNativeQuery.setParameter(next.getKey(), next.getValue()); 
		}
		List<TblTemperatureDataEntity> resultList = createNativeQuery.getResultList(); 
		if(!ListUtil.isEmpty(resultList)) {
			return resultList;
		}
		return null;
	}
	
	@Override
	public List<TblTemperatureDataEntity> getTableDataOne(Date begin, Date end,Integer indicatorId,Integer pointId) {
		em.clear();
		StringBuffer sql = new StringBuffer("SELECT * FROM TBL_TEMPERATURE_DATA A WHERE A.VALUE IS NOT NULL  ");
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
		sql.append(" ORDER BY RECORD_TIME DESC LIMIT 0 , 1");
		Query createNativeQuery = em.createNativeQuery(sql.toString(),TblTemperatureDataEntity.class);
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();  
		while(iterator.hasNext()) {
			Entry<String, String> next = iterator.next(); 
			createNativeQuery.setParameter(next.getKey(), next.getValue()); 
		}
		List<TblTemperatureDataEntity> resultList = createNativeQuery.getResultList(); 
		if(!ListUtil.isEmpty(resultList)) {
			return resultList;
		}
		return null;
	}
	
	@Override
	public List<TblTemperatureDTSDataEntity> getDtsTableData(Date begin, Date end,Integer indicatorId,Integer pointId,Integer refPointDataId) {
		em.clear();
		StringBuffer sql = new StringBuffer("SELECT * FROM TBL_TEMPERATURE_DTS_DATA A WHERE A.VALUE IS NOT NULL  ");
		Map map = new HashMap();
		if(indicatorId!=null) {
			sql.append(" AND INDICATOR_ID = :indicatorId ");
			map.put("indicatorId", indicatorId);
		}
		if(pointId!=null) {
			sql.append(" AND POINT_ID = :pointId ");
			map.put("pointId", pointId);
		}
		if(refPointDataId!=null) {
			sql.append(" AND REF_POINT_DATA_ID = :refPointDataId ");
			map.put("refPointDataId", refPointDataId);
		}
		if(begin!=null && end !=null ) {
			String beginDate = DateUtil.formatDate(begin, "yyyy-MM-dd HH:mm:ss"); 
			String endDate = DateUtil.formatDate(end, "yyyy-MM-dd HH:mm:ss"); 
			sql.append(" AND RECORD_TIME BETWEEN '"+beginDate+"'" + " AND '"+ endDate + "'" );
		}
		sql.append(" ORDER BY RECORD_TIME DESC ");
		Query createNativeQuery = em.createNativeQuery(sql.toString(),TblTemperatureDTSDataEntity.class);
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();  
		while(iterator.hasNext()) {
			Entry<String, String> next = iterator.next(); 
			createNativeQuery.setParameter(next.getKey(), next.getValue()); 
		}
		List<TblTemperatureDTSDataEntity> resultList = createNativeQuery.getResultList(); 
		if(!ListUtil.isEmpty(resultList)) {
			return resultList;
		}
		return null;
	}
	
	@Override
	public List<TblTemperatureDTSDataEntity> Table(TemperatureDtsQuery query) {
		em.clear();
		StringBuffer sql = new StringBuffer("SELECT * FROM TBL_TEMPERATURE_DTS_DATA A WHERE A.VALUE IS NOT NULL  ");
		Map map = new HashMap();
		if(query.getIndicatorId()!=null) {
			sql.append(" AND INDICATOR_ID = :indicatorId ");
			map.put("indicatorId", query.getIndicatorId());
		}
		if(query.getPointId()!=null) {
			sql.append(" AND POINT_ID = :pointId ");
			map.put("pointId", query.getPointId());
		}
		if(query.getRefPointDataId()!=null) {
			sql.append(" AND REF_POINT_DATA_ID = :refPointDataId ");
			map.put("refPointDataId", query.getRefPointDataId());
		}
		if(query.getBegin()!=null && query.getEnd() !=null ) {
			String beginDate = DateUtil.formatDate(query.getBegin(), "yyyy-MM-dd HH:mm:ss"); 
			String endDate = DateUtil.formatDate(query.getEnd(), "yyyy-MM-dd HH:mm:ss"); 
			sql.append(" AND RECORD_TIME BETWEEN '"+beginDate+"'" + " AND '"+ endDate + "'" );
		}
		sql.append(" ORDER BY RECORD_TIME DESC ");
		Query createNativeQuery = em.createNativeQuery(sql.toString(),TblTemperatureDTSDataEntity.class);
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();  
		while(iterator.hasNext()) {
			Entry<String, String> next = iterator.next(); 
			createNativeQuery.setParameter(next.getKey(), next.getValue()); 
		}
		List<TblTemperatureDTSDataEntity> resultList = createNativeQuery.getResultList(); 
		if(!ListUtil.isEmpty(resultList)) {
			return resultList;
		}
		return resultList;
	}
}
