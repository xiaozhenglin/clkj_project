package com.changlan.command.dao;

import com.changlan.common.entity.TblCommandRecordEntity;

public interface ICommandRecordDao {

	TblCommandRecordEntity getOneRecordOrderByTime(String registPackage, String receiveMessage);
}
