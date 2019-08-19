package com.changlan.common.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.changlan.alarm.pojo.AlarmDownType;
import com.changlan.alarm.pojo.TblAlarmDataDetail;
import com.changlan.alarm.service.IAlarmDataService;
import com.changlan.alarm.vo.AlarmDataVo;
import com.changlan.alarm.vo.ScreenAlarmMessageBoxVO;
import com.changlan.common.entity.TblPointAlamDataEntity;
import com.changlan.common.pojo.BaseResult;
import com.changlan.common.util.FastjsonUtil;
import com.changlan.common.util.SpringUtil;
import com.changlan.netty.service.INettyService;

@Component
public class ScheduleConfiguration {
	
		@Scheduled(cron = "0 30 * * * ?")
		public void work() {
			INettyService nettyService = SpringUtil.getBean(INettyService.class); 
			//查找报警数据 
			IAlarmDataService alarmdAlarmDataService = SpringUtil.getBean(IAlarmDataService.class);
			TblPointAlamDataEntity query = new TblPointAlamDataEntity();
			query.setDownStatus(AlarmDownType.UN_DOWN.toString());
			List<TblAlarmDataDetail> list = alarmdAlarmDataService.getList(query);
			
			//封装数据格式
			List<ScreenAlarmMessageBoxVO> result = new ArrayList<ScreenAlarmMessageBoxVO>();
			for(TblAlarmDataDetail  detail :  list) {
				ScreenAlarmMessageBoxVO  vo = new ScreenAlarmMessageBoxVO(detail);
				result.add(vo);
			}
			
			//发送数据信息
			for(ScreenAlarmMessageBoxVO vo : result) {
				try {
					nettyService.serverSendMessageBox(FastjsonUtil.beanToJson(vo));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
}
