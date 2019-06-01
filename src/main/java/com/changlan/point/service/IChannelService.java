package com.changlan.point.service;

import java.util.List;

import com.changlan.common.entity.TblCompanyChannelEntity;

public interface IChannelService {

	Boolean existChannelName(TblCompanyChannelEntity entity);

	List<TblCompanyChannelEntity> getAllChannel(TblCompanyChannelEntity entity);  

}
