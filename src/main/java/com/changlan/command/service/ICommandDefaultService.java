package com.changlan.command.service;

import java.util.List;

import com.changlan.command.pojo.CommandDefaultDetail;
import com.changlan.common.entity.TblPointSendCommandEntity;

public interface ICommandDefaultService {

	Boolean existName(TblPointSendCommandEntity entity);

	TblPointSendCommandEntity save(TblPointSendCommandEntity entity);

	List<CommandDefaultDetail> commandList(TblPointSendCommandEntity command);   
 
}
