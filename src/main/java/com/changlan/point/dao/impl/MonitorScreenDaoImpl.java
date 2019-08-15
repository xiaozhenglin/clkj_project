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
import com.changlan.point.entity.AppCountEntity;
import com.changlan.point.entity.LineMonitorCountEntity;
import com.changlan.point.entity.PointCountEntity;
import com.changlan.point.entity.ScreenPointEntity;
import com.changlan.point.pojo.PointQuery;
import com.changlan.point.pojo.ScreenQuery;
import com.changlan.user.pojo.LoginUser;
import com.changlan.user.pojo.OperationDataType;

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
				+ " ,( select count(*) AS early_alarm from tbl_point_alam_data  where tbl_point_alam_data.ALARM_TYPE != 'ALARM'  " + paramQuery +  " )  AS early_alarm"
				+ " ,( select count(*) AS alarm from tbl_point_alam_data  where tbl_point_alam_data.ALARM_TYPE = 'ALARM'  " + paramQuery +  " )  AS alarm ) ";
		Query createNativeQuery = em.createNativeQuery(SqlUtil.addRowId(sql),PointCountEntity.class);
		return createNativeQuery.getResultList(); 
	}

	@Override
	public List<ScreenPointEntity> queryPoint(ScreenQuery query) {
		em.clear();
		String sql =" select  point.* , line.LINE_NAME ,  channel.`NAME` from tbl_points  point  left join tbl_lines line on  point.LINE_ID = line.LINE_ID "
				+ " left join tbl_company_channel channel on  channel.CHANNEL_ID = line.CHANNEL_ID where 1=1  ";
		
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
		if(StringUtil.isNotEmpty(query.getChannelName())) {
			sql += " AND channel.`NAME` LIKE '%" +  query.getChannelName() + "%' " ;;
		}
		if(StringUtil.isNotEmpty(query.getPointNameOrLineName())) {
			sql += " AND ( line.LINE_NAME LIKE '%" +  query.getPointNameOrLineName() + "%'  or  point.POINT_NAME LIKE '%" +  query.getPointNameOrLineName() + "%' ) " ;
		}
		if(query.getIsCorner()!=null) {
			sql += " AND point.IS_CORNER = "+query.getIsCorner();
		}else {
			sql += " AND (point.is_corner is null or point.is_corner!= 1) ";
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
				+  "( select count(*)  AS operation_total from tbl_user_operation  where 1=1  )  AS operation_total ,  "
				+  "( select count(*)  AS success_count from tbl_user_operation  where OPERATION_TYPE = " + "'" + OperationDataType.SUCCESS.getName() + "'" + "  )AS success_count , "
			    +  "( select count(*)  AS exception_count from tbl_user_operation  where OPERATION_TYPE = " + "'" + OperationDataType.EXCEPTION.getName() + "'" + "  )AS exception_count , "
				+  "( select count(*)  AS unauthority_count from tbl_user_operation  where OPERATION_TYPE = " + "'" + OperationDataType.UNAUTHORITY.getName() + "'" + "  )AS unauthority_count ) "
				;
		Query createNativeQuery = em.createNativeQuery(SqlUtil.addRowId(sql),LineMonitorCountEntity.class);
		return createNativeQuery.getResultList(); 
	}

	
	@Override
	public List<AppCountEntity> queryBack(ScreenQuery query) {		
		em.clear();		
		String pointQuery = "";
		String lineQuery = "";
		String channelQuery = "";
		if(query.getPointId()!=null) {
			 pointQuery = " and  point_id = "+ query.getPointId();
		}
		if(query.getLineId()!=null) {
			 lineQuery = " and  line_id = "+ query.getLineId();
		}
		if(query.getChannelId()!=null) {
			 channelQuery = " and  channel_id = "+ query.getChannelId();
		}
		String sql =" SELECT * from ( "
				+ "  ( select count(*) AS alarm_total from tbl_point_alam_data where 1=1  " +  pointQuery +   " )  AS alarm_total "
				+"  ,( select count(*) AS alarm_deal from tbl_point_alam_data  where tbl_point_alam_data.down_status = 'DOWN' " +  pointQuery +  " )  AS alarm_deal "
				+"  ,( select count(*) AS alarm_not_deal from tbl_point_alam_data  where tbl_point_alam_data.down_status != 'DOWN'  " + pointQuery +  " )  AS alarm_not_deal "
				+"  ,( select count(*) AS point_total from  tbl_points where 1=1 " + pointQuery +  " )  AS point_total "
				+"  ,( select count(*) AS point_online from  tbl_points  where STATUS!='OUT_CONNECT'  " + pointQuery +  " )  AS point_online "
				+"  ,( select count(*) AS point_not_online from  tbl_points  where STATUS='OUT_CONNECT'  " + pointQuery + " )  AS point_not_online "				
				+ " ,( select count(*) AS huanliu_points_total from tbl_points  where  POINT_CATAGORY_ID = 1 " + pointQuery +  " ) AS huanliu_points_total"
				+ " ,( select count(*) AS jufang_points_total from tbl_points  where  POINT_CATAGORY_ID = 8  " + pointQuery +  " )  AS jufang_points_total"
				+ " ,( select count(*) AS guangqian_points_total from tbl_points  where  POINT_CATAGORY_ID = 9 " + pointQuery +  " )  AS guangqian_points_total"
				+ " ,( select count(*) AS line_total from tbl_lines " + lineQuery +  " )  AS line_total"
				+ " ,( select count(*) AS line35_total from tbl_lines  where DIANYA_LEVEL ='35KV' " + lineQuery +  " ) AS line35_total"
				+ " ,( select count(*) AS line110_total from tbl_lines  where DIANYA_LEVEL ='110KV' " + lineQuery +  " ) AS line110_total"
				+ " ,( select count(*) AS line220_total from tbl_lines  where DIANYA_LEVEL ='220KV' " + lineQuery +  " ) AS line220_total"
				+ " ,( select count(*) AS channel_total from tbl_company_channel " + channelQuery +  " )  AS channel_total"
				+ " ,( select count(*) AS shipin_total from tbl_company_channel where  find_in_set('4', MONITOR_IDS)  " + channelQuery +  " ) AS shipin_total "
				+ " ,(  select count(*) AS jingai_total from tbl_company_channel where  find_in_set('1', MONITOR_IDS)  " + channelQuery +  " ) AS jingai_total"
				+ " ,(  select count(*) AS huanjing_total from tbl_company_channel where  find_in_set('3', MONITOR_IDS) " + channelQuery +  " )  AS huanjing_total" + ") ";
		Query createNativeQuery = em.createNativeQuery(SqlUtil.addRowId(sql),AppCountEntity.class);
		return createNativeQuery.getResultList(); 
	}
	

}
