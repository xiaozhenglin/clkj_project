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

}