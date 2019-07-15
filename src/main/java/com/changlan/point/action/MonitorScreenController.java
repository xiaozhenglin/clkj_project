package com.changlan.point.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.service.ICrudService;
import com.changlan.other.entity.DeviceData;
import com.changlan.other.pojo.PartialDischargeQuery;
import com.changlan.point.dao.IMonitorScreenDao;
import com.changlan.point.pojo.MonitorScreenQuery;
import com.changlan.point.service.IMonitorScreenService;
import com.changlan.point.vo.MonitorScreenVO;
import com.changlan.point.vo.ScreenPointIdVO;
import com.changlan.point.vo.ScreenPointsVO;

import java.math.BigInteger;

@RestController
@RequestMapping("/admin/screen")
public class MonitorScreenController extends BaseController {
	private static final int List = 0;

	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private IMonitorScreenDao monitorScreenDao; 
	
	@Autowired
	private IMonitorScreenService monitorScreenService;
	
	@RequestMapping("/display") 
	public ResponseEntity<Object>  display(MonitorScreenQuery query) {
		List<Object> list = (List<Object>)monitorScreenService.display(query);
		MonitorScreenVO vo = new MonitorScreenVO();
		Object[] object0 = (Object[]) list.get(0);		
		vo.setAlarm_total(object0[1].toString());
		
		Object[] object1 = (Object[]) list.get(1);
		vo.setAlarm_deal(object1[1].toString());
		
		Object[] object2 = (Object[]) list.get(2);
		vo.setAlarm_not_deal(object2[1].toString());
		
		Object[] object3 = (Object[]) list.get(3);
		vo.setPoint_total(object3[1].toString());
		
		Object[] object4 = (Object[]) list.get(4);
		vo.setPoint_online(object4[1].toString());
		
		Object[] object5 = (Object[]) list.get(5);
		vo.setPoint_not_online(object5[1].toString());
		
		vo.setCaculate();
		
		return success(vo);
	}
	
	
	@RequestMapping("/getPointInfo") 
	public ResponseEntity<Object>  display(String pointName ,String pointId) {
		List<Object> list = (List<Object>)monitorScreenService.queryPointId(pointName,pointId);
		ScreenPointIdVO vo = new ScreenPointIdVO();
		
		
		Object[] object0 = (Object[]) list.get(0);
		if(object0[1]==null) {
			return success(0);
		}else {
			vo.setPoint_id(object0[1].toString());
			
			vo.setPoint_name(object0[2].toString());
			
			vo.setPoint_address(object0[3].toString());
					
			vo.setAlarm_total(object0[4].toString());
			
			vo.setAlarm_deal(object0[5].toString());
			
			vo.setLong_lati(object0[6].toString());
			
			vo.setLine_id(object0[7].toString());
			
			vo.setLine_order(object0[8].toString());
			
			vo.setLine_name(object0[9].toString());
			
			String not_deal_num = Integer.toString(Integer.parseInt(object0[4].toString()) - Integer.parseInt(object0[5].toString()));
			
			vo.setAlarm_not_deal(not_deal_num);
			
			return success(vo);
		}
	}
	
	@RequestMapping("/searchPoints") 
	public ResponseEntity<Object>  searchPoints(String search ) {
		List<Object> list = (List<Object>)monitorScreenService.searchPoints(search);
		List<ScreenPointsVO> voList = new ArrayList<ScreenPointsVO>();
		
		for(int i= 0;i<list.size();i++) {
			
			ScreenPointsVO vo = new ScreenPointsVO();
		    Object[] object = (Object[]) list.get(i);
		    if(object[1]== null) {
		    	vo.setPoint_status("离线");
		    }else if(object[1].toString().trim().equals("DATA_CAN_IN")){
		    	vo.setPoint_status("在线");
		    }else if(object[1].toString().trim().equals("OUT_CONNECT")){
		    	vo.setPoint_status("离线");}
		    else {
		    	vo.setPoint_status(object[1].toString());
		    }
			vo.setPoint_id(object[2].toString());
			
			vo.setPoint_name(object[3].toString());
			
			vo.setLong_lati(object[4].toString());
			
			vo.setLine_id(object[5].toString());
			
			vo.setLine_order(object[6].toString());
			
			vo.setLine_name(object[7].toString());
								
			voList.add(vo);
			//return success(vo);
		}
		
		return success(voList);
	}
	
}
