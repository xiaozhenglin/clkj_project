package com.changlan.point.action;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblCompanyChannelEntity;
import com.changlan.common.entity.TblMonitorSystemEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.point.service.IChannelService;
import com.changlan.point.service.IMonitorSystemService;

@RestController
@RequestMapping("/admin/monitor/system")
public class MonitorSystemController  extends BaseController{
	
	@Autowired
	ICrudService crudService;
	
	@Autowired
	IMonitorSystemService  monitorSystemService;
	
	//修改或者保存 
	@RequestMapping("/save")
	public ResponseEntity<Object>  functionList(TblMonitorSystemEntity entity ) throws Exception { 
		TblMonitorSystemEntity update = (TblMonitorSystemEntity)crudService.update(entity, true); 
		if(update == null) {
			throw new MyDefineException(PoinErrorType.SAVE_EROOR);
		}
		return success(update);
	}

	@RequestMapping("/list")
	public ResponseEntity<Object>  companyGropList(TblMonitorSystemEntity entity) {
		List<TblMonitorSystemEntity> list = monitorSystemService.getAll(entity);
		return success(list);
	}
	
	/**
	 */
	@RequestMapping("/delete")
	@Transactional
	public ResponseEntity<Object>  delete(TblMonitorSystemEntity entity) throws Exception {   
		List<TblMonitorSystemEntity> list = monitorSystemService.getAll(entity);
		if(ListUtil.isEmpty(list)) {
			throw new MyDefineException("0000","没有找到该监控系统",false,null); 
		}
		Boolean isSuccess = crudService.deleteBySql("DELETE FROM TBL_MONITOR_SYSTEM WHERE MONITOR_ID ="+entity.getMonitorId(), true); 
		return success(isSuccess);
	}


}
