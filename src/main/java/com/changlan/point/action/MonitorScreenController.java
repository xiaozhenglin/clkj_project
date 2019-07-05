package com.changlan.point.action;

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
	public ResponseEntity<Object>  display(String pointName) {
		List<Object> list = (List<Object>)monitorScreenService.queryPointId(pointName);
		ScreenPointIdVO vo = new ScreenPointIdVO();
		
		
		Object[] object0 = (Object[]) list.get(0);
		vo.setPoint_id(object0[1].toString());
		
		//Object[] object1 = (Object[]) list.get(0);
		vo.setPoint_name(object0[2].toString());
		
		//Object[] object2 = (Object[]) list.get(0);
		vo.setPoint_address(object0[3].toString());
		
		//Object[] object3 = (Object[]) list.get(0);		
		vo.setAlarm_total(object0[4].toString());
		
		//Object[] object4 = (Object[]) list.get(0);
		vo.setAlarm_deal(object0[5].toString());
		
		String not_deal_num = Integer.toString(Integer.parseInt(object0[4].toString()) - Integer.parseInt(object0[5].toString()));
		
		vo.setAlarm_not_deal(not_deal_num);
		//vo.setCaculate();
		
		return success(vo);
	}
	
	
}
