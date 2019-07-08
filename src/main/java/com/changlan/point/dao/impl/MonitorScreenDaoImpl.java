package com.changlan.point.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.changlan.common.util.SqlUtil;
import com.changlan.other.entity.PartialDischargeEntity;
import com.changlan.point.dao.IMonitorScreenDao;
import com.changlan.point.pojo.MonitorScreenQuery;
import com.changlan.point.vo.MonitorScreenVO;

@Repository
public class MonitorScreenDaoImpl implements IMonitorScreenDao{
	@PersistenceContext
	EntityManager em;
	
	@Override
	public List<Object> query(MonitorScreenQuery query) {		
		em.clear();		
		String sql =" select count(1)  from tbl_point_alam_data " + 
				" union all select count(1)  from tbl_alarm_down_record " + 
				" union all select count(1) from tbl_point_alam_data a  where a.ALAM_DOWN_RECORD_ID is null " + 
				" union all select count(1) from  tbl_points " + 
				" union all select count(1) from  tbl_points b where b.`STATUS`='DATA_CAN_IN'  " + 
				" union all select count(1) from  tbl_points b where b.`STATUS`='OUT_CONNECT'";
		
		Query createNativeQuery = em.createNativeQuery(SqlUtil.addRowId(sql.toString()));
		return createNativeQuery.getResultList(); 
	}

	@Override
	public List<Object> queryPointId(String pointName , String pointId) {
		em.clear();
		String sql =" select  t.POINT_ID , t.POINT_NAME, t.POINT_ADDRESS ,count(a.ALARM_ID) ,  "
				+ "count(a.ALAM_DOWN_RECORD_ID)  " + 
				"from tbl_points t , tbl_point_alam_data a where  a.POINT_ID = t.POINT_ID  and 1 = 1";
		if(pointName != null) {
			sql += " and t.POINT_NAME = " +  "'" + pointName + "'";
		}
		if(pointId != null) {
			sql += " and t.POINT_ID = " +  "'" + pointId + "'";
		}
		Query createNativeQuery = em.createNativeQuery(SqlUtil.addRowId(sql.toString()));
		return createNativeQuery.getResultList(); 
	}

	@Override
	public List<Object> searchPoints(String pointName, String pointId) {
		em.clear();
		String sql =" select t.`STATUS` , t.POINT_ID , t.POINT_NAME from tbl_points t  where   ";
		if(pointName != null) {
			sql += "  POINT_NAME like " +  "'%" + pointName + "%'";
		}
		if(pointId != null) {
			sql += "  POINT_ID like " +  "'%" + pointId + "%'";
		}
		Query createNativeQuery = em.createNativeQuery(SqlUtil.addRowId(sql.toString()));
		return createNativeQuery.getResultList(); 
	}
	
	

}
