package com.changlan.common.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.changlan.common.entity.TblDvdEntity;
import com.changlan.common.pojo.TblDvdQuery;



public interface FileOperationService {
	Page<TblDvdEntity> getPage(TblDvdQuery entity, Pageable page);  
	
	List<TblDvdEntity> getAll(Integer id, String name, Integer pointId); 
}
