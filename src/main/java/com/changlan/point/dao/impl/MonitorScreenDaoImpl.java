package com.changlan.point.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.util.SqlUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.other.entity.PartialDischargeEntity;
import com.changlan.point.dao.IMonitorScreenDao;
import com.changlan.point.entity.LineMonitorCountEntity;
import com.changlan.point.entity.PointCountEntity;
import com.changlan.point.entity.ScreenPointEntity;
import com.changlan.point.pojo.PointQuery;
import com.changlan.point.pojo.ScreenQuery;
import com.changlan.user.pojo.LoginUser;

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
			sql += " AND ( line.LINE_NAME LIKE '%" +  query.getPointNameOrLineName() + "%'  or  point.POINT_NAME LIKE '%" +  query.getPointNameOrLineName() + "%' ) " ;
		}
		Query createNativeQuery = em.createNativeQuery(SqlUtil.addRowId(sql),ScreenPointEntity.class);
		return createNativeQuery.getResultList(); 
	}
	

	@Override
	public List<LineMonitorCountEntity> queryLine(ScreenQuery query) {		
		em.clear();		
		String paramQuery = "";
		TblAdminUserEntity currentUser = LoginUser.getCurrentUser();
		if(currentUser!=null) {
			String userId = currentUser.getAdminUserId();
		}
		if(query.getPointId()!=null) {
			 paramQuery = " and  point_id = "+ query.getPointId();
		}
		String sql =" SELECT * from ( ( select count(*) AS point_total from  tbl_points where 1=1  )  AS point_total ,  "
				+ "( select count(*) AS line_total from  tbl_lines   where 1=1    )  AS line_total ,   " 
				+  "( select count(*)  AS operation_total from tbl_user_operation  where 1=1  )  AS operation_total  ) ";
		Query createNativeQuery = em.createNativeQuery(SqlUtil.addRowId(sql),LineMonitorCountEntity.class);
		return createNativeQuery.getResultList(); 
	}

	
	

}
