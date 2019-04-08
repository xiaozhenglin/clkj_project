package com.changlan.user.service.impl;

import java.util.List;

import org.smslib.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.changlan.common.pojo.SmsParams;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.GsmCat;
import com.changlan.point.service.IPointDefineService;
import com.changlan.user.service.ISnsCatService;

public class SmsCatServiceImpl implements ISnsCatService {
	
	@Autowired
	ICrudService crudService;
	
	@Autowired
	IPointDefineService pointDefineService;

	@Override
	public void initSmsCat() {
//		pointDefineService.getAll(entity);
//		List<SmsParams> list;
		GsmCat cat = GsmCat.getInstance();
		try {
			Service initService = cat.initService(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void canSendMsgOrNot() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendSmsCat() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRecord() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void analysis() {
		// TODO Auto-generated method stub
		
	}
	
}
