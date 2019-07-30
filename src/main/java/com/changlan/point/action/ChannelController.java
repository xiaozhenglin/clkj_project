package com.changlan.point.action;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblAlarmRuleEntity;
import com.changlan.common.entity.TblCompanyChannelEntity;
import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.entity.TblMonitorSystemEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.SpringUtil;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.point.service.IChannelService;
import com.changlan.point.service.ICompanyGropService;
import com.changlan.point.service.IMonitorSystemService;
import com.changlan.point.vo.ChannelVO;

@RestController
@RequestMapping("/admin/company/channel")
public class ChannelController extends BaseController{
	
	@Autowired
	ICrudService crudService;
	
	@Autowired
	IChannelService  channelService;
	
	@Autowired
	IMonitorSystemService  monitorSystemservice ;
	
	//修改或者保存 
	@RequestMapping("/save")
	@Transactional
	public ResponseEntity<Object>  functionList(TblCompanyChannelEntity entity , TblMonitorSystemEntity tblMonitorSystemEntity ) throws Exception { 
		Boolean exist = channelService.existChannelName(entity);
		if(exist) {
			throw new MyDefineException(PoinErrorType.COMPANY_CHANNEL_NAME_EXIST);
		}

		List<TblMonitorSystemEntity> all = monitorSystemservice.getAll(tblMonitorSystemEntity); 
		
		//String monitor_ids = "";
		List<String> monitorList = new ArrayList<String>();
		for(TblMonitorSystemEntity tblMonitorSystem : all) {
			Integer monitor =  tblMonitorSystem.getMonitorId();
			monitorList.add(String.valueOf(monitor));
		}
		
		String monitor_ids =  StringUtils.join(monitorList.toArray(),",");  
		entity.setMonitor_ids(monitor_ids);
		
		TblCompanyChannelEntity update = (TblCompanyChannelEntity)crudService.update(entity, true); 
		if(update == null) {
			throw new MyDefineException(PoinErrorType.SAVE_EROOR);
		}
		return success(update);
	}

	
	@RequestMapping("/list")
	public ResponseEntity<Object>  companyGropList(TblCompanyChannelEntity entity) {
		List<TblCompanyChannelEntity> list = channelService.getAllChannel(entity);
		return success(list);
	}
	
	/**
	 * @param entity
	 * @return
	 * @throws Exception
	 * 隧道删除
	 */
	@RequestMapping("/delete")
	public ResponseEntity<Object>  delete(TblCompanyChannelEntity entity) throws Exception {   
		List<TblCompanyChannelEntity> list = channelService.getAllChannel(entity);
		if(ListUtil.isEmpty(list)) {
			throw new MyDefineException("0000","没有找到该隧道",false,null); 
		}
		Boolean isSuccess = crudService.deleteBySql("DELETE FROM TBL_COMPANY_CHANNEL WHERE CHANNEL_ID ="+entity.getChannelId(), true); 
		return success(isSuccess);
	}

}
