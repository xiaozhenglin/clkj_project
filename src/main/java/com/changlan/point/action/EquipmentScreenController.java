package com.changlan.point.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.service.ICrudService;
import com.changlan.point.dao.IEquipmentScreenDao;
import com.changlan.point.service.IEquipmentScreenService;
import com.changlan.point.vo.EquipmentScreenVO;

import java.math.BigInteger;

@RestController
@RequestMapping("/admin/equipment")
public class EquipmentScreenController extends BaseController {
	private static final int List = 0;

	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private IEquipmentScreenDao equipmentScreenDao; 
	
	@Autowired
	private IEquipmentScreenService equipmentScreenService;
	
	
	
	
	@RequestMapping("/queryPointInfo") 
	public ResponseEntity<Object>  display(String pointId) {
		List<Object> list = (List<Object>)equipmentScreenDao.queryPointInfo(pointId);
		EquipmentScreenVO vo = new EquipmentScreenVO();
				
		Object[] object0 = (Object[]) list.get(0);
		
		vo.setPoint_id(object0[1].toString());
		vo.setPoint_name(object0[2].toString());
		vo.setPoint_address(object0[3].toString());
		vo.setPhones(object0[4].toString());
		vo.setPrincipal(object0[5].toString());
		vo.setCompany(object0[6].toString());
		vo.setPoint_catagory_name(object0[7].toString());
		//vo.setIndicators(object0[8].toString());
		vo.setAlarm_deal(object0[9].toString());
		vo.setAlarm_not_deal(object0[10].toString());
						
		return success(vo);
	}
	

}
