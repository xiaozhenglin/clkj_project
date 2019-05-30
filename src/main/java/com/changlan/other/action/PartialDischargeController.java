package com.changlan.other.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.other.dao.IPartialDischargeDao;
import com.changlan.other.entity.DeviceData;
import com.changlan.other.entity.PartialDischargeEntity;
import com.changlan.other.entity.SimpleEntity;
import com.changlan.other.pojo.PartialDischargeQuery;
import com.changlan.other.service.IPartialDischargeService;
import com.changlan.other.vo.DeviceDataVo;
import com.changlan.point.pojo.PointInfoDetail;
import com.changlan.point.pojo.PointQuery;
import com.changlan.point.service.IPointDefineService;
import com.changlan.point.vo.PointDataListVo;
/**
 *局放数据*/
@RestController
@RequestMapping("/admin/Partial/Discharge")
public class PartialDischargeController extends BaseController{
	
	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private IPartialDischargeService partialDischargeService ;
	
	@Autowired
	private IPartialDischargeDao dao;
	
	@Autowired
	private IPointDefineService pointDefineService;
	
	@RequestMapping("/list") 
	public ResponseEntity<Object>  list(PartialDischargeQuery query) {
		
//		TblPointsEntity point = new TblPointsEntity();
//		Integer lineId = query.getLineId(); 
//		if(lineId!=null) { 
//			point.setLineId(lineId);
//		}
//		if(query.getCategoryId()!=null) {
//			point.setPointCatagoryId(query.getCategoryId()); 
//		}
//		if(query.getPointId()!=null) {
//			point.setPointId(query.getPointId());  
//		}
//		//当前线路下的监控系统      找出所有的监控点
//		List<PointInfoDetail> all = pointDefineService.getAll(point);
		
		List<PartialDischargeEntity> list = (List<PartialDischargeEntity>)partialDischargeService.list(query); 
		return success(list);
	}
	 
	@RequestMapping("/table") 
	public ResponseEntity<Object>  table(PartialDischargeQuery query) {
		List<DeviceDataVo> result = new ArrayList<DeviceDataVo>();
		//找出所有的通道id
		List<SimpleEntity> channelSettingList = (List<SimpleEntity>)partialDischargeService.channelSettingList(query); 
		if(!ListUtil.isEmpty(channelSettingList)) {
			for(SimpleEntity simple :channelSettingList ) {
				//根据通道Id来筛选数据
				query.setChannelSettings_id(Integer.parseInt(simple.getId())); 
				List<DeviceData> table = (List<DeviceData>)partialDischargeService.table(query); 
				DeviceDataVo vo = new DeviceDataVo(Integer.parseInt(simple.getId()),table);
				result.add(vo);
			}
		}
		return success(result);
	}
	
	 
}
