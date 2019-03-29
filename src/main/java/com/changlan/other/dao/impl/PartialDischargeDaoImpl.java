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

import org.springframework.stereotype.Repository;

import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.util.DaoUtil;
import com.changlan.common.util.DateUtil;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.SqlUtil;
import com.changlan.other.dao.IPartialDischargeDao;
import com.changlan.other.entity.DeviceData;
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
				+ " LEFT JOIN tbl_points on  tbl_points.POINT_ID = devicesettings.point_id "
				+ " WHERE 1=1 ";
		if(query.getPointId()!=null) {
			sql +=" AND devicesettings.point_id ="+query.getPointId();
		}
		Query createNativeQuery = em.createNativeQuery(SqlUtil.addRowId(sql.toString()),PartialDischargeEntity.class);
		return createNativeQuery.getResultList(); 
	}

	@Override
	public Object table(PartialDischargeQuery query) {
		em.clear();
		String sql = "SELECT deviceData.* from deviceData INNER  JOIN channelSettings on deviceData.channelSettings_id = channelSettings.id " ;
		if(query.getChannelSettings_id()!=null) {
			sql+= " AND deviceData.channelSettings_id = "+query.getChannelSettings_id();
		}
		if(query.getPointId()!=null) {
			sql+=" INNER JOIN devicesettings on channelSettings.deviceSetting_id = devicesettings.id and devicesettings.point_id = "+query.getPointId();
		}
		String beginDate = DateUtil.formatDate(query.getBegin(), "yyyy-MM-dd HH:mm:ss"); 
		System.out.println(beginDate);
		String endDate = DateUtil.formatDate(query.getEnd(), "yyyy-MM-dd HH:mm:ss"); 
		System.out.println(endDate);
		sql+=" WHERE deviceData.createtime BETWEEN '"+beginDate+"'" + " AND '"+ endDate + "'" ;
		sql+= " ORDER BY deviceData.createtime DESC ";
		Query createNativeQuery = em.createNativeQuery(sql.toString(),DeviceData.class);
		return createNativeQuery.getResultList();
	}

	@Override
	public Object channelSettingList() {
		em.clear();
		String sql = "SELECT channelSettings.id as Id from channelSettings " ;
		Query createNativeQuery = em.createNativeQuery(sql.toString(),SimpleEntity.class);
		return createNativeQuery.getResultList();
	}
}
