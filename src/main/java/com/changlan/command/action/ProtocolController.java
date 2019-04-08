package com.changlan.command.action;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.command.pojo.CommandDefaultDetail;
import com.changlan.command.pojo.CommandProtolDetail;
import com.changlan.command.service.ICommandDefaultService;
import com.changlan.command.service.IProtocolService;
import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblCommandProtocolEntity;
import com.changlan.common.entity.TblIndicatorCategoriesEntity;
import com.changlan.common.entity.TblPointSendCommandEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.point.pojo.PoinErrorType;

@RestController
@RequestMapping("/admin/command/protocol")
public class ProtocolController extends BaseController{
	@Autowired
	ICrudService crudService;
	
	@Autowired
	private IProtocolService protocolService;

	@RequestMapping("/save")
	@Transactional
	public ResponseEntity<Object>  save(TblCommandProtocolEntity entity) throws Exception {
//		Boolean existName = protocolService.existName(entity); 
//		if(existName) {
//			throw new MyDefineException(PoinErrorType.NAME_EXIST);
//		}
		TblCommandProtocolEntity update = protocolService.save(entity); 
		if(update ==null) {
			throw new MyDefineException(PoinErrorType.SAVE_EROOR);
		}
		return success(update);
	}
	
	@RequestMapping("/list")
	public ResponseEntity<Object>  list(TblCommandProtocolEntity protocol) {
		List<CommandProtolDetail> list = protocolService.protocolList(protocol);
		return success(list);
	} 
	
	//未加入权限表
	@RequestMapping("/delete")
	@Transactional
	public ResponseEntity<Object>  delete(TblCommandProtocolEntity entity) throws MyDefineException { 
		TblCommandProtocolEntity find = (TblCommandProtocolEntity)crudService.get(entity.getProtocolId(),TblCommandProtocolEntity.class,true);
		if(find == null) {
			throw new MyDefineException(PoinErrorType.NOT_EXIST);
		}
		Boolean delete = crudService.delete(entity, true);
		return success(delete);
	}
	
}
