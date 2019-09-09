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
			 paramQuery = " and  POINT_ID = "+ query.getPointId();
		}
		String sql =" SELECT * from ( "
				+ "  ( select count(*) AS alarm_total from TBL_POINT_ALAM_DATA where 1=1  " +  paramQuery +   " )  AS alarm_total "
				+"  ,( select count(*) AS alarm_deal from TBL_POINT_ALAM_DATA  where TBL_POINT_ALAM_DATA.DOWN_STATUS = 'DOWN' " +  paramQuery +  " )  AS alarm_deal "
				+"  ,( select count(*) AS alarm_not_deal from TBL_POINT_ALAM_DATA  where TBL_POINT_ALAM_DATA.DOWN_STATUS != 'DOWN'  " + paramQuery +  " )  AS alarm_not_deal "
				+"  ,( select count(*) AS point_total from  TBL_POINTS where 1=1 and (IS_CORNER!= 1 or IS_CORNER is null)" + paramQuery +  " )  AS point_total "
				+"  ,( select count(*) AS point_online from  TBL_POINTS  where STATUS!='OUT_CONNECT' and (IS_CORNER!= 1 or IS_CORNER is null) " + paramQuery +  " )  AS point_online "
				+"  ,( select count(*) AS point_not_online from  TBL_POINTS  where STATUS='OUT_CONNECT' and (IS_CORNER!= 1 or IS_CORNER is null) " + paramQuery + " )  AS point_not_online "
				+ " ,( select count(*) AS early_alarm from TBL_POINT_ALAM_DATA  where TBL_POINT_ALAM_DATA.ALARM_TYPE != 'ALARM'  " + paramQuery +  " )  AS early_alarm"
				+ " ,( select count(*) AS alarm from TBL_POINT_ALAM_DATA  where TBL_POINT_ALAM_DATA.ALARM_TYPE = 'ALARM'  " + paramQuery +  " )  AS alarm ) ";
		Query createNativeQuery = em.createNativeQuery(SqlUtil.addRowId(sql),PointCountEntity.class);
		return createNativeQuery.getResultList(); 
	}

	@Override
	public List<ScreenPointEntity> queryPoint(ScreenQuery query) {
		em.clear();
		String sql =" select  point.* , line.LINE_NAME ,  channel.`NAME` from TBL_POINTS  point  left join TBL_LINES line on  point.LINE_ID = line.LINE_ID "
				+ " left join TBL_COMPANY_CHANNEL channel on  channel.CHANNEL_ID = line.CHANNEL_ID where 1=1  ";
		
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
			sql += " AND ( line.LINE_NAME LIKE '%" +  query.getPointNameOrLineName() + "%'  or  point.POINT_NAME LIKE '%" +  query.getPointNameOrLineName() + "%'  or channel.`NAME` LIKE '%" +  query.getPointNameOrLineName() + "%'  ) " ;
		}
		if(query.getIsCorner()!=null) {
			sql += " AND point.IS_CORNER = "+query.getIsCorner();
		}else {
			sql += " AND (point.IS_CORNER is null or point.IS_CORNER!= 1) ";
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
			 paramQuery = " and  POINT_ID = "+ query.getPointId();
		}
		String sql =" select * from ( ( select count(*) AS point_total from  TBL_POINTS where 1=1 and (IS_CORNER!= 1 or IS_CORNER is null) )  AS point_total ,  "
				+ "( select count(*) AS line_total from  TBL_LINES   where 1=1    )  AS line_total ,   " 
				+  "( select count(*)  AS operation_total from TBL_USER_OPERATION  where 1=1  )  AS operation_total ,  "
				+  "( select count(*)  AS success_count from TBL_USER_OPERATION  where OPERATION_TYPE = " + "'" + OperationDataType.SUCCESS.getName() + "'" + "  )AS success_count , "
			    +  "( select count(*)  AS exception_count from TBL_USER_OPERATION  where OPERATION_TYPE = " + "'" + OperationDataType.EXCEPTION.getName() + "'" + "  )AS exception_count , "
				+  "( select count(*)  AS unauthority_count from TBL_USER_OPERATION  where OPERATION_TYPE = " + "'" + OperationDataType.UNAUTHORITY.getName() + "'" + "  )AS unauthority_count ) "
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
			 pointQuery = " and  POINT_ID = "+ query.getPointId();
		}
		if(query.getLineId()!=null) {
			 lineQuery = " and  LINE_ID = "+ query.getLineId();
		}
		if(query.getChannelId()!=null) {
			 channelQuery = " and  CHANNEL_ID = "+ query.getChannelId();
		}
		String sql =" SELECT * from ( "
				+ "  ( select count(*) AS alarm_total from TBL_POINT_ALAM_DATA where 1=1  " +  pointQuery +   " )  AS alarm_total "
				+"  ,( select count(*) AS alarm_deal from TBL_POINT_ALAM_DATA  where TBL_POINT_ALAM_DATA.DOWN_STATUS = 'DOWN' " +  pointQuery +  " )  AS alarm_deal "
				+"  ,( select count(*) AS alarm_not_deal from TBL_POINT_ALAM_DATA  where TBL_POINT_ALAM_DATA.DOWN_STATUS != 'DOWN'  " + pointQuery +  " )  AS alarm_not_deal "
				+"  ,( select count(*) AS point_total from  TBL_POINTS where 1=1 and (IS_CORNER!= 1 or IS_CORNER is null) " + pointQuery +  " )  AS point_total "
				+"  ,( select count(*) AS point_online from  TBL_POINTS  where STATUS!='OUT_CONNECT' and (IS_CORNER!= 1 or IS_CORNER is null) " + pointQuery +  " )  AS point_online "
				+"  ,( select count(*) AS point_not_online from  TBL_POINTS  where STATUS='OUT_CONNECT' and (IS_CORNER!= 1 or IS_CORNER is null) " + pointQuery + " )  AS point_not_online "				
				+ " ,( select count(*) AS huanliu_points_total from TBL_POINTS  where  POINT_CATAGORY_ID = 1 " + pointQuery +  " ) AS huanliu_points_total"
				+ " ,( select count(*) AS jufang_points_total from TBL_POINTS  where  POINT_CATAGORY_ID = 8  " + pointQuery +  " )  AS jufang_points_total"
				+ " ,( select count(*) AS guangqian_points_total from TBL_POINTS  where  POINT_CATAGORY_ID = 9 " + pointQuery +  " )  AS guangqian_points_total"
				+ " ,( select count(*) AS cewen_points_total from TBL_POINTS  where  POINT_CATAGORY_ID = 10 " + pointQuery +  " )  AS cewen_points_total"
				+ " ,( select count(*) AS line_total from TBL_LINES where 1=1" + lineQuery +  " )  AS line_total"
				+ " ,( select count(*) AS line35_total from TBL_LINES  where DIANYA_LEVEL ='35KV' " + lineQuery +  " ) AS line35_total"
				+ " ,( select count(*) AS line110_total from TBL_LINES  where DIANYA_LEVEL ='110KV' " + lineQuery +  " ) AS line110_total"
				+ " ,( select count(*) AS line220_total from TBL_LINES  where DIANYA_LEVEL ='220KV' " + lineQuery +  " ) AS line220_total"
				+ " ,( select count(*) AS line500_total from TBL_LINES  where DIANYA_LEVEL ='500KV' " + lineQuery +  " ) AS line500_total"
				+ " ,( select count(*) AS channel_total from TBL_COMPANY_CHANNEL where 1=1" + channelQuery +  " )  AS channel_total"
				+ " ,( select count(*) AS shipin_total from TBL_COMPANY_CHANNEL where  find_in_set('4', MONITOR_IDS)  " + channelQuery +  " ) AS shipin_total "
				+ " ,(  select count(*) AS jingai_total from TBL_COMPANY_CHANNEL where  find_in_set('1', MONITOR_IDS)  " + channelQuery +  " ) AS jingai_total"
				+ " ,(  select count(*) AS paifeng_total from TBL_COMPANY_CHANNEL where  find_in_set('2', MONITOR_IDS)  " + channelQuery +  " ) AS paifeng_total"
				+ " ,(  select count(*) AS paishui_total from TBL_COMPANY_CHANNEL where  find_in_set('3', MONITOR_IDS) " + channelQuery +  " )  AS paishui_total" + ") ";
		Query createNativeQuery = em.createNativeQuery(SqlUtil.addRowId(sql),AppCountEntity.class);
		return createNativeQuery.getResultList(); 
	}
	

}
