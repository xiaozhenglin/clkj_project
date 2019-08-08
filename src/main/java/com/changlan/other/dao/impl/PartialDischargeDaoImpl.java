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
		String sql ="select devicesettings.deviceId AS deviceId,channelsettings.channel_number AS channel_number,channelsettings.location AS location,realtimedata.amplitude AS amplitude,realtimedata.frequency AS frequency,realtimedata.energy AS energy,realtimedata.updatetime AS updatetime,realtimedata.alarm_amplitude_frequency AS alarm_amplitude_frequency, channelsettings.location_detail,devicesettings.point_id,tbl_points.POINT_NAME "
				+ " from ((devicesettings join channelsettings on ((channelsettings.deviceSetting_id = devicesettings.id))) join realtimedata on((channelsettings.id = realtimedata.channelSettings_id)))"
				+ " LEFT JOIN TBL_POINTS on  TBL_POINTS.POINT_ID = devicesettings.point_id "
				+ " WHERE 1=1 ";
		if(query.getPointId()!=null) {
			//因为是左连接，所以判断监控点名称是空值就不要。
			sql +=" AND devicesettings.point_id ="+query.getPointId() + " AND TBL_POINTS.POINT_NAME IS NOT NULL ";
		}
		Query createNativeQuery = em.createNativeQuery(SqlUtil.addRowId(sql.toString()),PartialDischargeEntity.class);
		return createNativeQuery.getResultList(); 
	}

	@Override
	public Object table(PartialDischargeQuery query) {
		em.clear();
		String sql = "SELECT deviceData.* from deviceData INNER  JOIN channelSettings on deviceData.channelSettings_id = channelSettings.id WHERE 1=1 " ;
		if(query.getChannelSettings_id()!=null) {
			sql+= " AND deviceData.channelSettings_id = "+query.getChannelSettings_id();
		}
		if(query.getPointId()!=null) {
			sql+=" AND deviceData.POINT_ID = "+query.getPointId();
		}
		
		if(StringUtil.isNotEmpty(query.getRecord_id())) {
			sql+=" AND deviceData.record_id = "+ "'" + query.getRecord_id() + "'";
		}
//		if(query.getPointId()!=null) {
//			sql+=" INNER JOIN devicesettings on channelSettings.deviceSetting_id = devicesettings.id and devicesettings.point_id = "+query.getPointId();
//		}
		if(query.getBegin()!=null && query.getEnd()!=null) {
			String beginDate = DateUtil.formatDate(query.getBegin(), "yyyy-MM-dd HH:mm:ss"); 
			String endDate = DateUtil.formatDate(query.getEnd(), "yyyy-MM-dd HH:mm:ss"); 
			sql+=" AND deviceData.createtime BETWEEN '"+beginDate+"'" + " AND '"+ endDate + "'" ;
		}
		sql+= " ORDER BY deviceData.createtime DESC ";
		Query createNativeQuery = em.createNativeQuery(sql.toString(),DeviceData.class);
		return createNativeQuery.getResultList();
	}
	
	@Override
	public Page<DeviceDataSpecial> table(PartialDischargeQuery query,Pageable page) {
		em.clear();
		String sql = "SELECT deviceDataSpecial.* from deviceDataSpecial  WHERE 1=1 " ;
		/*
		 * if(query.getChannelSettings_id()!=null) { sql+=
		 * " AND deviceDataSpecial.channelSettings_id = "+query.getChannelSettings_id();
		 * }
		 */
		if(query.getPointId()!=null) {
			sql+=" AND deviceDataSpecial.POINT_ID = "+query.getPointId();
		}
		
		if(StringUtil.isNotEmpty(query.getRecord_id())) {
			sql+=" AND deviceDataSpecial.record_id = "+ "'" + query.getRecord_id() + "'";
		}
//		if(query.getPointId()!=null) {
//			sql+=" INNER JOIN devicesettings on channelSettings.deviceSetting_id = devicesettings.id and devicesettings.point_id = "+query.getPointId();
//		}
		if(query.getBegin()!=null && query.getEnd()!=null) {
			String beginDate = DateUtil.formatDate(query.getBegin(), "yyyy-MM-dd HH:mm:ss"); 
			String endDate = DateUtil.formatDate(query.getEnd(), "yyyy-MM-dd HH:mm:ss"); 
			sql+=" AND deviceDataSpecial.createtime BETWEEN '"+beginDate+"'" + " AND '"+ endDate + "'" ;
		}
		sql+= " ORDER BY deviceDataSpecial.createtime DESC ";
		
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
		String sql = "SELECT channelSettings.*  from channelSettings " ;
		if(query.getPointId()!=null) {
			sql+=" INNER JOIN devicesettings on channelSettings.deviceSetting_id = devicesettings.id and devicesettings.point_id = "+query.getPointId();
		}
		if(query.getChannelSettings_id()!=null) {
			sql+=" WHERE 1=1 AND channelSettings.id = "+query.getChannelSettings_id();
		}
		Query createNativeQuery = em.createNativeQuery(sql.toString(),SimpleEntity.class);
		return createNativeQuery.getResultList();
	}
}
