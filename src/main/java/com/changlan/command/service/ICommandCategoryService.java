package com.changlan.command.service;

import java.util.List;

import com.changlan.command.pojo.CommandCategoryDetail;
import com.changlan.common.entity.TblCommandCategoryEntity;

public interface ICommandCategoryService {

	TblCommandCategoryEntity save(TblCommandCategoryEntity category);

	Boolean existName(TblCommandCategoryEntity entity);

	List<TblCommandCategoryEntity> categoryList(Integer id);    

}
