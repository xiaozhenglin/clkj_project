package com.changlan.other.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.util.DaoUtil;
import com.changlan.common.util.DateUtil;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.SqlUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.other.dao.IPartialDischargeDao;
import com.changlan.other.entity.DeviceData;
import com.changlan.other.entity.DeviceDataSpecial;
import com.changlan.other.entity.PartialDischargeEntity;
import com.changlan.other.entity.SimpleEntity;
import com.changlan.other.pojo.PartialDischargeQuery;

@Repository
public class PartialDischargeDaoImpl implements IPartialDischargeDao{

	@PersistenceContext
	EntityManager em;

	@Override
	public Object list(PartialDischargeQuery query) {
		em.clear();
		String sql ="select DEVICESETTINGS.DEVICEID AS deviceId,CHANNELSETTINGS.CHANNEL_NUMBER AS channel_number,CHANNELSETTINGS.LOCATION AS location,REALTIMEDATA.AMPLITUDE AS amplitude,REALTIMEDATA.FREQUENCY AS frequency,REALTIMEDATA.ENERGY AS energy,REALTIMEDATA.UPDATETIME AS updatetime,REALTIMEDATA.ALARM_AMPLITUDE_FREQUENCY AS alarm_amplitude_frequency, CHANNELSETTINGS.LOCATION_DETAIL,DEVICESETTINGS.POINT_ID,TBL_POINTS.POINT_NAME "
				+ " from ((DEVICESETTINGS join CHANNELSETTINGS on ((CHANNELSETTINGS.DEVICESETTING_ID = DEVICESETTINGS.ID))) join REALTIMEDATA on((CHANNELSETTINGS.ID = REALTIMEDATA.CHANNELSETTINGS_ID)))"
				+ " LEFT JOIN TBL_POINTS on  TBL_POINTS.POINT_ID = DEVICESETTINGS.POINT_ID "
				+ " WHERE 1=1 ";
		if(query.getPointId()!=null) {
			//因为是左连接，所以判断监控点名称是空值就不要。
			sql +=" AND DEVICESETTINGS.POINT_ID ="+query.getPointId() + " AND TBL_POINTS.POINT_NAME IS NOT NULL ";
		}
		Query createNativeQuery = em.createNativeQuery(SqlUtil.addRowId(sql.toString()),PartialDischargeEntity.class);
		return createNativeQuery.getResultList(); 
	}

	@Override
	public Object table(PartialDischargeQuery query) {
		em.clear();
		String sql = "SELECT DEVICEDATA.* from DEVICEDATA INNER  JOIN CHANNELSETTINGS on DEVICEDATA.CHANNELSETTINGS_ID = CHANNELSETTINGS.ID WHERE 1=1 " ;
		if(query.getChannelSettings_id()!=null) {
			sql+= " AND DEVICEDATA.CHANNELSETTINGS_ID = "+query.getChannelSettings_id();
		}
		if(query.getPointId()!=null) {
			sql+=" AND DEVICEDATA.POINT_ID = "+query.getPointId();
		}
		
		if(query.getRecord_id()!=null) {
			sql+=" AND DEVICEDATA.RECORD_ID = "+ "'" + query.getRecord_id() + "'";
		}
//		if(query.getPointId()!=null) {
//			sql+=" INNER JOIN devicesettings on channelSettings.deviceSetting_id = devicesettings.id and devicesettings.point_id = "+query.getPointId();
//		}
		if(query.getBegin()!=null && query.getEnd()!=null) {
			String beginDate = DateUtil.formatDate(query.getBegin(), "yyyy-MM-dd HH:mm:ss"); 
			String endDate = DateUtil.formatDate(query.getEnd(), "yyyy-MM-dd HH:mm:ss"); 
			sql+=" AND DEVICEDATA.CREATETIME BETWEEN '"+beginDate+"'" + " AND '"+ endDate + "'" ;
		}
		sql+= " ORDER BY DEVICEDATA.CREATETIME DESC LIMIT 0,2000 ";
		Query createNativeQuery = em.createNativeQuery(sql.toString(),DeviceData.class);
		return createNativeQuery.getResultList();
	}
	
	@Override
	public Page<DeviceDataSpecial> table(PartialDischargeQuery query,Pageable page) {
		em.clear();
		String sql = "SELECT DEVICEDATASPECIAL.* FROM DEVICEDATASPECIAL  WHERE 1=1 " ;
		/*
		 * if(query.getChannelSettings_id()!=null) { sql+=
		 * " AND deviceDataSpecial.channelSettings_id = "+query.getChannelSettings_id();
		 * }
		 */
		if(query.getPointId()!=null) {
			sql+=" AND DEVICEDATASPECIAL.POINT_ID = "+query.getPointId();
		}
		
		if(query.getRecord_id()!=null) {
			sql+=" AND DEVICEDATASPECIAL.RECORD_ID = "+ "'" + query.getRecord_id() + "'";
		}
//		if(query.getPointId()!=null) {
//			sql+=" INNER JOIN devicesettings on channelSettings.deviceSetting_id = devicesettings.id and devicesettings.point_id = "+query.getPointId();
//		}
		if(query.getBegin()!=null && query.getEnd()!=null) {
			String beginDate = DateUtil.formatDate(query.getBegin(), "yyyy-MM-dd HH:mm:ss"); 
			String endDate = DateUtil.formatDate(query.getEnd(), "yyyy-MM-dd HH:mm:ss"); 
			sql+=" AND DEVICEDATASPECIAL.CREATETIME BETWEEN '"+beginDate+"'" + " AND '"+ endDate + "'" ;
		}
		sql+= " ORDER BY DEVICEDATASPECIAL.CREATETIME DESC ";
		
		Query nativeQuery = em.createNativeQuery(sql.toString(),DeviceDataSpecial.class);
	       // nativeQuery = setParam(nativeQuery, param);
	    int startNum = page.getPageNumber() * page.getPageSize(); //分页从零开始
	    nativeQuery.setFirstResult(startNum);
	    nativeQuery.setMaxResults(page.getPageSize());
	    List<DeviceDataSpecial> resultList = nativeQuery.getResultList();
	        
	    //查出总条数
	    int count = resultList.size();
	    return new PageImpl<DeviceDataSpecial>(resultList, page, count);
	    
		
	}
	

	@Override
	public Object channelSettingList(PartialDischargeQuery query) {
		em.clear();
		String sql = "SELECT CHANNELSETTINGS.*  FROM CHANNELSETTINGS " ;
		if(query.getPointId()!=null) {
			sql+=" INNER JOIN DEVICESETTINGS ON CHANNELSETTINGS.DEVICESETTING_ID = DEVICESETTINGS.ID AND DEVICESETTINGS.POINT_ID = "+query.getPointId();
		}
		if(query.getChannelSettings_id()!=null) {
			sql+=" WHERE 1=1 AND CHANNELSETTINGS.ID = "+query.getChannelSettings_id();
		}
		Query createNativeQuery = em.createNativeQuery(sql.toString(),SimpleEntity.class);
		return createNativeQuery.getResultList();
	}
}
