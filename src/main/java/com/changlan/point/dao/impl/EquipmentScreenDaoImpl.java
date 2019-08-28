package com.changlan.point.dao.impl;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.util.DateUtil;
import com.changlan.common.util.SqlUtil;
import com.changlan.point.dao.IEquipmentScreenDao;
import com.changlan.point.entity.ScreenPointEntity;
import com.changlan.point.pojo.CommonDataQuery;

@Repository
public class EquipmentScreenDaoImpl  implements IEquipmentScreenDao {
	@PersistenceContext
	EntityManager em;

	@Override
	public List<Object> queryPointIndicatorList(CommonDataQuery query) {
		em.clear();	
		String sql ="select distinct(t.INDICATOR_ID) from TBL_POIN_DATA t  where 1 =1 " ;
		if(query.getPointId() != null) {
			sql  += " and t.POINT_ID = " +  "'" + query.getPointId() + "'";
		}
		if(query.getBegin()!=null && query.getEnd() !=null ) {
			String beginDate = DateUtil.formatDate(query.getBegin(), "yyyy-MM-dd HH:mm:ss"); 
			String endDate = DateUtil.formatDate(query.getBegin(), "yyyy-MM-dd HH:mm:ss"); 
			sql += (" AND t.RECORD_TIME BETWEEN '"+beginDate+"'" + " AND '"+ endDate + "'" );
		}
		Query createNativeQuery = em.createNativeQuery(SqlUtil.addRowId(sql));
		return createNativeQuery.getResultList(); 
		
	}

	@Override
	public List<Object> queryTemperatureIndicatorList(CommonDataQuery query) {
		em.clear();	
		String sql ="select distinct(t.INDICATOR_ID) from TBL_TEMPERATURE_DATA t  where 1 =1 " ;
		if(query.getPointId() != null) {
			sql  += " and t.POINT_ID = " +  "'" + query.getPointId() + "'";
		}
		if(query.getBegin()!=null && query.getEnd() !=null ) {
			String beginDate = DateUtil.formatDate(query.getBegin(), "yyyy-MM-dd HH:mm:ss"); 
			String endDate = DateUtil.formatDate(query.getBegin(), "yyyy-MM-dd HH:mm:ss"); 
			sql += (" AND t.RECORD_TIME BETWEEN '"+beginDate+"'" + " AND '"+ endDate + "'" );
		}
		Query createNativeQuery = em.createNativeQuery(SqlUtil.addRowId(sql));
		return createNativeQuery.getResultList(); 
		
	}
	
	@Override
	public List<Object> queryPartDischargeIndicatorList(CommonDataQuery query) {
		em.clear();	
		query.setIndicatorIds("26");
		String sql ="select distinct(t.INDICATOR_ID) from DEVICEDATACOLL t  where 1 =1 " ;
		if(query.getPointId() != null) {
			sql  += " and t.POINT_ID = " +  "'" + query.getPointId() + "'";
		}
		if(query.getBegin()!=null && query.getEnd() !=null ) {
			String beginDate = DateUtil.formatDate(query.getBegin(), "yyyy-MM-dd HH:mm:ss"); 
			String endDate = DateUtil.formatDate(query.getBegin(), "yyyy-MM-dd HH:mm:ss"); 
			sql += (" AND t.RECORD_TIME BETWEEN '"+beginDate+"'" + " AND '"+ endDate + "'" );
		}
		Query createNativeQuery = em.createNativeQuery(SqlUtil.addRowId(sql));
		return createNativeQuery.getResultList(); 
		
	}
	
}
