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
import com.changlan.point.entity.PointCountEntity;
import com.changlan.point.entity.ScreenPointEntity;
import com.changlan.point.pojo.PointQuery;
import com.changlan.point.pojo.ScreenQuery;

@Repository
public class MonitorScreenDaoImpl implements IMonitorScreenDao{
	@PersistenceContext
	EntityManager em;
	
	@Override
	public List<PointCountEntity> query(ScreenQuery query) {		
		em.clear();		
		String paramQuery = "";
		if(query.getPointId()!=null) {
			 paramQuery = " and  point_id = "+ query.getPointId();
		}
		String sql =" SELECT * from ( "
				+ "  ( select count(*) AS alarm_total from tbl_point_alam_data where 1=1  " +  paramQuery +   " )  AS alarm_total "
				+"  ,( select count(*) AS alarm_deal from tbl_point_alam_data  where tbl_point_alam_data.down_status = 'DOWN' " +  paramQuery +  " )  AS alarm_deal "
				+"  ,( select count(*) AS alarm_not_deal from tbl_point_alam_data  where tbl_point_alam_data.down_status != 'DOWN'  " + paramQuery +  " )  AS alarm_not_deal "
				+"  ,( select count(*) AS point_total from  tbl_points where 1=1 " + paramQuery +  " )  AS point_total "
				+"  ,( select count(*) AS point_online from  tbl_points  where STATUS!='OUT_CONNECT'  " + paramQuery +  " )  AS point_online "
				+"  ,( select count(*) AS point_not_online from  tbl_points  where STATUS='OUT_CONNECT'  " + paramQuery + " )  AS point_not_online "
				+ " ) ";
		Query createNativeQuery = em.createNativeQuery(SqlUtil.addRowId(sql),PointCountEntity.class);
		return createNativeQuery.getResultList(); 
	}

	@Override
	public List<ScreenPointEntity> queryPoint(ScreenQuery query) {
		em.clear();
		String sql =" select  point.* , line.LINE_NAME  from tbl_points  point  left join tbl_lines line on  point.LINE_ID = line.LINE_ID  where 1=1  ";
		
		if(StringUtil.isNotEmpty(query.getPointName())) {
			sql += " AND point.POINT_NAME LIKE '%" +  query.getPointName() + "%' " ;
		}
		if(query.getPointId()!=null) {
			sql += " AND  point.POINT_ID = " + query.getPointId();
		}
		if(query.getLineId()!=null) {
			sql += " AND  line.LINE_ID = " + query.getLineId();
		}
		if(StringUtil.isNotEmpty(query.getLineName())) {
			sql += " AND line.LINE_NAME LIKE '%" +  query.getLineName() + "%' " ;;
		}
		if(StringUtil.isNotEmpty(query.getPointNameOrLineName())) {
			sql += " AND line.LINE_NAME LIKE '%" +  query.getPointNameOrLineName() + "%'  or  point.POINT_NAME LIKE '%" +  query.getPointNameOrLineName() + "%' " ;
		}
		Query createNativeQuery = em.createNativeQuery(SqlUtil.addRowId(sql),ScreenPointEntity.class);
		return createNativeQuery.getResultList(); 
	}
	
	@Override
	public List<Object> countAlarmDataByPointId(ScreenQuery query) {
		return null;
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
			sql += "  and (t.POINT_NAME like " +  "'%" + query.getPointName() + "%'" + ")" ;
		}
		
		Query createNativeQuery = em.createNativeQuery(SqlUtil.addRowId(sql.toString()));
		return createNativeQuery.getResultList(); 
	}

	
	

}
