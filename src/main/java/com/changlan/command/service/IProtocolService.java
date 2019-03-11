package com.changlan.command.service;

import java.util.List;

import com.changlan.command.pojo.CommandProtolDetail;
import com.changlan.common.entity.TblCommandProtocolEntity;

public interface IProtocolService {

	Boolean existName(TblCommandProtocolEntity entity);

	TblCommandProtocolEntity save(TblCommandProtocolEntity entity);

	List<CommandProtolDetail> protocolList(Integer id,Integer categoryId);    

}
