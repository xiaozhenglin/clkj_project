package com.changlan.point.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changlan.common.util.ListUtil;
import com.changlan.point.dao.IMonitorScreenDao;
import com.changlan.point.entity.PointCountEntity;
import com.changlan.point.entity.ScreenPointEntity;
import com.changlan.point.pojo.PointQuery;
import com.changlan.point.pojo.ScreenQuery;
import com.changlan.point.service.IMonitorScreenService;


@Service
public class MonitorScreenServiceImpl implements IMonitorScreenService{

	@Autowired
	private IMonitorScreenDao dao; 
	
	@Override
	public List<PointCountEntity> display(ScreenQuery query) {
		List<PointCountEntity> list = dao.query(query); 
		return list;
	}

	@Override
	public List<ScreenPointEntity> queryPointId(ScreenQuery query) {
		List<ScreenPointEntity> list = dao.queryPoint(query); 
		if(!ListUtil.isEmpty(list)) {
			
			for(ScreenPointEntity point : list ) {
				
				//报警数据统计
				Integer alarm_deal = 0 ;
			    Integer alarm_not_deal= 0 ;
			    Integer alarm_total = 0 ;
			    float alarm_deal_ratio = 0;
			     Integer early_alarm= 0 ;
				 Integer alarm= 0 ;
			    
			    query.setPointId(point.getPoint_id());
				List<PointCountEntity> pointCount = dao.query(query);
				
				if(!ListUtil.isEmpty(pointCount)) {
					PointCountEntity count = pointCount.get(0); 
					count.setCaculate(); 
					
					if(count.getAlarm_total()!=null) {
						alarm_total = count.getAlarm_total();
					}
					
					if(count.getAlarm_deal()!=null) {
						alarm_deal = count.getAlarm_deal();
					}
					
					if(count.getAlarm_not_deal()!=null) {
						alarm_not_deal = count.getAlarm_not_deal();
					}
					if(count.getAlarm_deal_ratio()!=0) {
						alarm_deal_ratio = count.getAlarm_deal_ratio();
					}
					
					if(count.getEarly_alarm()!=0) {
						early_alarm = count.getEarly_alarm();
					}
					if(count.getAlarm()!=0) {
						alarm = count.getAlarm();
					}
					
				}
				
				point.setAlarm_total(alarm_total); 
				point.setAlarm_deal(alarm_deal); 
				point.setAlarm_not_deal(alarm_not_deal); 
				point.setAlarm_deal_ratio(alarm_deal_ratio); 
				point.setEarly_alarm(early_alarm);
				point.setAlarm(alarm); 
			}
			
		}
		return list;
	}
	
}
