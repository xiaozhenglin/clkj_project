package com.changlan.point.dao.impl;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.changlan.common.util.SqlUtil;
import com.changlan.point.dao.IEquipmentScreenDao;
import com.changlan.point.pojo.MonitorScreenQuery;
import com.changlan.point.vo.MonitorScreenVO;

@Repository
public class EquipmentScreenDaoImpl  implements IEquipmentScreenDao {
	@PersistenceContext
	EntityManager em;

	@Override
	public List<Object> queryPointInfo(String pointId) {
		em.clear();	
		String sql ="select  p.POINT_ID,p.POINT_NAME,p.POINT_ADDRESS, p.PHONES,p.PRINCIPAL,p.COMPANY,  " + 
				"(select c.PONT_CATAGORY_NAME from tbl_point_category c where c.POINT_CATGORY_ID = p.POINT_CATAGORY_ID ) as POINT_CATAGORY_NAME , " + 
				"p.INDICATORS, (select count(a.DOWN_STATUS) from tbl_point_alam_data a where a.POINT_ID = p.POINT_ID AND a.DOWN_STATUS = 'DOWN') as alarm_deal ,   " + 
				"(select count(a.DOWN_STATUS) from tbl_point_alam_data a where a.POINT_ID = p.POINT_ID AND a.DOWN_STATUS = 'UN_DOWN') as alarm_not_deal  " + 
				"from tbl_points p ";
		if(pointId != null) {
			sql  += " where p.POINT_ID = " +  "'" + pointId + "'";
		}
		Query createNativeQuery = em.createNativeQuery(SqlUtil.addRowId(sql.toString()));
		return createNativeQuery.getResultList(); 
		
	}

	@Override
	public List<Object> queryPointCurrentInfo(String search) {
		// TODO Auto-generated method stub
		return null;
	}
		
	
}
