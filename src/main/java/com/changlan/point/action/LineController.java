package com.changlan.point.action;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblMonitorSystemEntity;
import com.changlan.common.entity.TblPointAlamDataEntity;
import com.changlan.common.entity.TblPointCategoryEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.point.pojo.CompanyDetail;
import com.changlan.point.pojo.LineDetail;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.point.pojo.PointInfoDetail;
import com.changlan.point.service.ILineService;
import com.changlan.point.service.IMonitorSystemService;
import com.changlan.point.service.IPointDefineService;

@RestController
@RequestMapping("/admin/line")
public class LineController extends BaseController{
	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private ILineService lineService;
	
	@Autowired
	private IPointDefineService pointDefineService;
	
	
	
	//修改或者保存
	@RequestMapping("/save")
	@Transactional
	public ResponseEntity<Object>  lineSave(TblLinesEntity entity ) throws Exception { 
		Boolean exist = lineService.existName(entity);
		if(exist) {
			throw new MyDefineException(PoinErrorType.NAME_EXIST);
		}
		if(StringUtil.isNotEmpty(entity.getPicture_address())){
			
		}
		TblMonitorSystemEntity tblMonitorSystem = (TblMonitorSystemEntity)crudService.get(entity.getMonitorId(), TblMonitorSystemEntity.class, true);
		if(tblMonitorSystem.getName().indexOf("本体")>-1) {					
			entity.setAddTime(new Date());
			TblLinesEntity update = (TblLinesEntity)crudService.update(entity, true); 			
			if(update == null) {
				throw new MyDefineException(PoinErrorType.SAVE_EROOR);
			}
			return success(update);
		}else {
			throw new MyDefineException(PoinErrorType.LINE_CANNOT_CREATE);
		}
	}
	
	@RequestMapping("/list") 
	public ResponseEntity<Object>  lineList(TblLinesEntity line) throws Exception {
		List<LineDetail> list = lineService.getAll(line); 
		return success(list);
	}
	
	
	@RequestMapping("/delete")
	@Transactional
	public ResponseEntity<Object>  delete(TblLinesEntity entity) throws Exception { 
		TblLinesEntity find = (TblLinesEntity)crudService.get(entity.getLineId(),TblLinesEntity.class,true);
		if(find == null) {
			throw new MyDefineException(PoinErrorType.LINE_NOT_EXITS);
		}
		TblPointsEntity point = new TblPointsEntity();
		point.setLineId(find.getLineId()); 
		List<PointInfoDetail> all = pointDefineService.getAll(point); 
		if(!ListUtil.isEmpty(all)) {
			throw new Exception("线路包含了监控点,不能删除");    
		}
		Boolean delete = crudService.deleteBySql("DELETE FROM TBL_LINES WHERE LINE_ID = " +entity.getLineId() , true);
		return success(delete);
	}
	
	
	
	
	
	
	
	
}
