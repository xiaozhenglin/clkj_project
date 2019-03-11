package com.changlan.netty.event;

import javax.transaction.Transactional;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.PointStatus;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;
import com.changlan.point.pojo.SimplePoint;
import com.changlan.point.service.IPointDefineService;

@Component
public class PoinRegitPackageListener implements ApplicationListener<PointRegistPackageEvent>{

	@Override
	@Transactional
	public void onApplicationEvent(PointRegistPackageEvent event) {
		SimplePoint source = (SimplePoint)event.getSource(); 
		//修改监控点当前状态
		IPointDefineService service = SpringUtil.getBean(IPointDefineService.class);
		TblPointsEntity entity = service.getByRegistPackage(source.getRegistPackage());
		entity.setStatus(source.getStatus()); 
		ICrudService crudService = SpringUtil.getICrudService();
		crudService.update(entity, true);
	}

}
