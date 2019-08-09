package com.changlan.command.action;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.command.pojo.CommandDefaultDetail;
import com.changlan.command.pojo.CommandProtolDetail;
import com.changlan.command.service.ICommandCategoryService;
import com.changlan.command.service.ICommandDefaultService;
import com.changlan.command.vo.CommandDefaultVO;
import com.changlan.command.vo.CommandProtolVO;
import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblCommandCategoryEntity;
import com.changlan.common.entity.TblIndicatorCategoriesEntity;
import com.changlan.common.entity.TblPointSendCommandEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.point.pojo.PoinErrorType;

@RestController
@RequestMapping("/admin/command/default")
public class CommandDefaultSendController extends BaseController{
	@Autowired
	ICrudService crudService;
	
	@Autowired
	private ICommandDefaultService commandDefaultService;

	@RequestMapping("/save")
	public ResponseEntity<Object>  save(TblPointSendCommandEntity entity) throws Exception {
		Boolean existName = commandDefaultService.existName(entity); 
		if(existName) {
			throw new MyDefineException(PoinErrorType.NAME_EXIST);
		}
		TblPointSendCommandEntity update = commandDefaultService.save(entity); 
		if(update ==null) {
			throw new MyDefineException(PoinErrorType.SAVE_EROOR);
		}
		return success(update);
	}
	
	@RequestMapping("/list")
	public ResponseEntity<Object>  list(TblPointSendCommandEntity command) {
		List<CommandDefaultDetail> list = commandDefaultService.commandList(command);
		List result = new ArrayList();
		for(CommandDefaultDetail detail : list ) {
			CommandDefaultVO vo = new CommandDefaultVO(detail);
			result.add(vo);
		}
		
		return success(result);
	}
	
	@RequestMapping("/delete")
	public ResponseEntity<Object>  delete(TblPointSendCommandEntity entity) throws MyDefineException { 
		//entity.setSendCommandId(74);
		TblPointSendCommandEntity find = (TblPointSendCommandEntity)crudService.get(entity.getSendCommandId(),TblPointSendCommandEntity.class,true);
		if(find == null) {
			throw new MyDefineException(PoinErrorType.NOT_EXIST);
		}
		
		Boolean isSuccess = crudService.deleteBySql("DELETE FROM TBL_POINT_SEND_COMMAND WHERE SEND_COMMAND_ID ="+entity.getSendCommandId(), true); 
		return success(isSuccess);
	}
	
}
