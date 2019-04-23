package com.changlan.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.changlan.common.configuration.SmsCatConfiguration;
import com.changlan.common.entity.TblCommandRecordEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.SmsParams;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.GsmCat;
import com.changlan.point.service.IPointDefineService;
import com.changlan.user.pojo.LoginUser;
import com.changlan.user.service.ISmsCatService;
import org.springframework.stereotype.Service;

@Service
public class SmsCatServiceImpl implements ISmsCatService {
	
	@Autowired
	ICrudService crudService;
	
	@Autowired
	IPointDefineService pointDefineService;

//	@Override
//	public void initSmsCat() {
//		List<SmsParams> list = new ArrayList<SmsParams>();
//		SmsParams param = new SmsParams(SmsCatConfiguration.serverPortName, Integer.parseInt(SmsCatConfiguration.serverPortBound)); //设备
//		list.add(param);
//		GsmCat cat = GsmCat.getInstance();
//		try {
//			org.smslib.Service initService = cat.initService(list);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	@Override
	public boolean canSendMsgOrNot(Integer pointId) {
		return false;
	}
	

	@Override
	public void sendSmsCat(SmsParams param) throws Exception {
		String phones = param.getSmsMob(); 
		if(param.getPointId()!=null) {
			TblPointsEntity point =  (TblPointsEntity)crudService.get(param.getPointId(), TblPointsEntity.class, true);
			phones = point.getPhones();
		}
		GsmCat.sendMsgToOher(param.getSmsMob(),param.getSmsText()); 
	}

	@Override
	public Object analysis(String receiveMsg, String fromPhone) {
		return null;
	}



	
	
}
