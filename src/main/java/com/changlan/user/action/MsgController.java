package com.changlan.user.action;

import java.util.ArrayList;
import java.util.List;

import org.smslib.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TBLRoleDefineEntity;
import com.changlan.common.entity.TblMsgDataEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.SmsParams;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.GsmCat;
import com.changlan.user.pojo.MsgDataDetail;
import com.changlan.user.service.IMsgDataService;
import com.changlan.user.service.ISmsCatService;

@RestController
@RequestMapping("/admin/msg")
public class MsgController extends BaseController{

	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private IMsgDataService msgDataService;
	
	@Autowired
	private ISmsCatService smsCatService;
	
	//未加入权限表 接口没用
	@RequestMapping("/data/list")
	public ResponseEntity<Object>  list(TblMsgDataEntity data) throws Exception {  
		Page<MsgDataDetail> result = msgDataService.getAllByPage(data,getPage());
		return success(result);
	} 
	
	//未加入权限表 接口没用
	@RequestMapping("/send/sms")
	public ResponseEntity<Object>  send(SmsParams param ) throws Exception {  
        smsCatService.sendSmsCat(param);
		return success(true);
	} 
}
