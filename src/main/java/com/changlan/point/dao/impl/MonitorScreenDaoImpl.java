package com.changlan.point.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.changlan.common.util.SqlUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.other.entity.PartialDischargeEntity;
import com.changlan.point.dao.IMonitorScreenDao;
import com.changlan.point.pojo.MonitorScreenQuery;
import com.changlan.point.pojo.ScreenQuery;
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
	public List<Object> queryPointId(ScreenQuery query) {
		em.clear();
		String sql =" select  t.POINT_ID , t.POINT_NAME, t.POINT_ADDRESS, count(a.ALARM_ID) ,  "
				+ "count(a.ALAM_DOWN_RECORD_ID) , t.LONG_LATI , t.LINE_ID, t.LINE_ORDER ,  (select  s.LINE_NAME  from tbl_lines s  where t.LINE_ID = s.LINE_ID) as LINE_NAME  " + 
				"from tbl_points t  left join tbl_point_alam_data a on  a.POINT_ID = t.POINT_ID ";
		if(StringUtil.isNotEmpty(query.getPointName())) {
			sql += " where t.POINT_NAME = " +  "'" + query.getPointName() + "'";
		}
		if(StringUtil.isNotEmpty(String.valueOf(query.getPointId()))) {
			sql += " where t.POINT_ID = " +  "'" + query.getPointId() + "'";
		}
		Query createNativeQuery = em.createNativeQuery(SqlUtil.addRowId(sql.toString()));
		return createNativeQuery.getResultList(); 
	}

	@Override
	public List<Object> searchPoints(ScreenQuery query) {
		em.clear();
		String sql =" select t.`STATUS` , t.POINT_ID , t.POINT_NAME ,  t.LONG_LATI, t.LINE_ID,  t.LINE_ORDER ,(select  s.LINE_NAME  from tbl_lines s  where t.LINE_ID = s.LINE_ID) as LINE_NAME from tbl_points t left join tbl_lines k  on  k.LINE_ID = t.LINE_ID   ";
		if(StringUtil.isNotEmpty(query.getLineName())) {
			sql += " where ( t.POINT_NAME like " +  "'%" + query.getPointName() + "%'" +  " or k.LINE_NAME like " +  "'%" + query.getLineName() + "%'" +  " or t.LINE_ID like " +  "'%" + query.getLineId() + "%'" + ")";	
		}
		if( StringUtil.equals(query.getPointName(), "ALL")) {
			sql +=   " where  k.LINE_NAME like " +  "'%" + query.getLineName() + "%'" ;	
		}else if(StringUtil.isNotEmpty(query.getLineName()) && StringUtil.isNotEmpty(query.getPointName())){
			sql += "  where (k.LINE_NAME like " +  "'%" + query.getLineName() + "%'" + ")" ;
			sql += "  where (t.POINT_NAME like " +  "'%" + query.getPointName() + "%'" + ")" ;
		}
		
		Query createNativeQuery = em.createNativeQuery(SqlUtil.addRowId(sql.toString()));
		return createNativeQuery.getResultList(); 
	}
	
	

}
