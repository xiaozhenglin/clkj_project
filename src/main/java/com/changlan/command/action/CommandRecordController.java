package com.changlan.command.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.command.pojo.CommandDefaultDetail;
import com.changlan.command.pojo.CommandRecordDetail;
import com.changlan.command.service.ICommandDefaultService;
import com.changlan.command.service.ICommandRecordService;
import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblCommandProtocolEntity;
import com.changlan.common.entity.TblCommandRecordEntity;
import com.changlan.common.entity.TblPointSendCommandEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.point.pojo.PoinErrorType;

@RestController
@RequestMapping("/admin/command/record")
public class CommandRecordController extends BaseController{
	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private ICommandRecordService commandRecordService;
	
	@RequestMapping("/list")
	public ResponseEntity<Object>  list(Integer recordId,String registPackage,String backContent) {
		List<CommandRecordDetail> result = commandRecordService.getList(recordId, registPackage, backContent); 
		return success(result);
	}
	
	//保存为发送指令时自动保存。
}
