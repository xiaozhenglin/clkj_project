package com.changlan.point.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.other.entity.DeviceData;
import com.changlan.other.pojo.PartialDischargeQuery;
import com.changlan.point.dao.IMonitorScreenDao;
import com.changlan.point.entity.PointCountEntity;
import com.changlan.point.entity.ScreenPointEntity;
import com.changlan.point.pojo.PointQuery;
import com.changlan.point.pojo.ScreenQuery;
import com.changlan.point.service.IMonitorScreenService;
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
	public ResponseEntity<Object>  display(ScreenQuery query) {
		List<PointCountEntity> list = monitorScreenService.display(query);
		if(!ListUtil.isEmpty(list)) {
			PointCountEntity result = list.get(0); 
			result.setCaculate();
			return success(result);
		}
		return success( new PointCountEntity());
	}
	
	@RequestMapping("/getPointInfo") 
	public ResponseEntity<Object>  query(ScreenQuery query) {
		List<ScreenPointEntity> list =  monitorScreenService.queryPointId(query);
		return success(list);
	}
	
	@RequestMapping("/searchPoints") 
	public ResponseEntity<Object>  searchPoints(ScreenQuery screenQuery, String pointName ) {
		List<Object> list = (List<Object>)monitorScreenService.searchPoints(screenQuery);
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
			if(object[6] != null) {
				vo.setLine_order(object[6].toString());
			}
			vo.setLine_name(object[7].toString());
								
			voList.add(vo);
			//return success(vo);
		}
		
		return success(voList);
	}
	
}
