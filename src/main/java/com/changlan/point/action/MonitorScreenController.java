package com.changlan.point.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.FileUtil;
import com.changlan.common.util.ListUtil;
import com.changlan.other.entity.DeviceData;
import com.changlan.other.pojo.PartialDischargeQuery;
import com.changlan.point.dao.IMonitorScreenDao;
import com.changlan.point.entity.LineMonitorCountEntity;
import com.changlan.point.entity.PointCountEntity;
import com.changlan.point.entity.ScreenPointEntity;
import com.changlan.point.pojo.PointQuery;
import com.changlan.point.pojo.ScreenQuery;
import com.changlan.point.service.IMonitorScreenService;
import com.changlan.point.vo.ScreenPointIdVO;
import com.changlan.point.vo.ScreenPointsVO;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;

@RestController
@RequestMapping("/admin/screen")
public class MonitorScreenController extends BaseController {
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
		for(ScreenPointEntity screenPoint : list) {
			String picturePath = screenPoint.getPicture_url();
			//HttpServletResponse response =  getResponse();

			String pictureChar = FileUtil.getImageStr(picturePath);
			screenPoint.setPicture_url(pictureChar);
		}
		return success(list);
	}
	
	@RequestMapping("/searchPoints") 
	public ResponseEntity<Object>  searchPoints(ScreenQuery query) {
		List<ScreenPointEntity> list =  monitorScreenService.queryPointId(query);
		return success(list);
	}
	
	@RequestMapping("/queryLine") 
	public ResponseEntity<Object>  queryLine(ScreenQuery query) {
		List<LineMonitorCountEntity> list = monitorScreenDao.queryLine(query); 
		if(!ListUtil.isEmpty(list)) {
			LineMonitorCountEntity result = list.get(0); 
			result.setSystemVar();
			return success(result);
		}
		return success( new LineMonitorCountEntity());
	}
	
}
