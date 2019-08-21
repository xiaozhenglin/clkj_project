package com.changlan.netty.event;

import javax.transaction.Transactional;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.point.pojo.PointStatus;
import com.changlan.point.pojo.SimplePoint;
import com.changlan.point.service.IPointDefineService;

@Component
public class PoinRegitPackageListener implements ApplicationListener<PointRegistPackageEvent>{

	@Override
	@Transactional
	public void onApplicationEvent(PointRegistPackageEvent event) {
		SimplePoint source = (SimplePoint)event.getSource(); 
		if(StringUtil.isNotEmpty(source.getRegistPackage())) {
			//修改监控点当前状态
			IPointDefineService service = SpringUtil.getBean(IPointDefineService.class);
			TblPointsEntity entity = service.getByRegistPackageOrId(null, source.getRegistPackage());
			if(entity==null) {
				return ;
			}
			PointStatus status = source.getStatus();
			if(status!=null) {
				changeStatus(entity, status); 
			}
//			PointStatus agoStatus = null;
		
//			else {
//				agoStatus = PointStatus.valueOf(entity.getStatus());
//			}
//			if(entity !=null && agoStatus == null) {
//				changeStatus(entity,currentStatus);
//				return;
//			}
//			if(agoStatus == PointStatus.OUT_CONNECT && currentStatus != PointStatus.OUT_CONNECT) {
//				changeStatus(entity,currentStatus);
//				return;
//			}
//			
//			if(agoStatus == PointStatus.CONNECT && currentStatus!=PointStatus.CONNECT ) {
//				changeStatus(entity,currentStatus);
//				return;
//			}
//			
//			if(agoStatus == PointStatus.DATA_CAN_IN && (currentStatus==PointStatus.DATA_CAN_NOT_IN ||currentStatus==PointStatus.OUT_CONNECT )) {
//				changeStatus(entity,currentStatus);
//				return;
//			}
//			
//			if(agoStatus == PointStatus.DATA_CAN_NOT_IN && (currentStatus!=PointStatus.CONNECT)) {
//				changeStatus(entity,currentStatus);
//			}
		}
	}

	private void changeStatus(TblPointsEntity entity, PointStatus status) {
		entity.setStatus(status.toString()); 
		ICrudService crudService = SpringUtil.getICrudService();
		crudService.update(entity, true);
	}

}
