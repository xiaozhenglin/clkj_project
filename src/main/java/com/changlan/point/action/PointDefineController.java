package com.changlan.point.action;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblPointCategoryEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.StringUtil;
import com.changlan.point.pojo.LineDetail;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.point.pojo.PointInfoDetail;
import com.changlan.point.service.ILineService;
import com.changlan.point.service.IPointDefineService;
import com.changlan.point.vo.PointDefineVO;

@RestController
@RequestMapping("/admin/point/define")
public class PointDefineController extends BaseController{
	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private IPointDefineService pointDefineService;
	
	//修改或者保存
	@RequestMapping("/save")
	@Transactional
	public ResponseEntity<Object>  lineSave(TblPointsEntity entity ) throws Exception { 
		Boolean exist = pointDefineService.existPointpName(entity);
		if(exist) {
			throw new MyDefineException(PoinErrorType.NAME_EXIST.getCode(), PoinErrorType.NAME_EXIST.getName(), false, null);
		}
		Map mapPoint = new HashMap();
		if(entity.getLineId()!=null) {
			mapPoint.put("lineId", new ParamMatcher(entity.getLineId()));
		}
        List<TblPointsEntity> listPoint = crudService.findByMoreFiled(TblPointsEntity.class, mapPoint, true);
		BigDecimal ToalJingDu = new BigDecimal(0);
		BigDecimal TotalWeiDu = new BigDecimal(0);
		if(listPoint!=null) {
			for(TblPointsEntity point:listPoint) {
				if(StringUtil.isNotEmpty(point.getLongLati())) {
					String longLati = point.getLongLati();
					String [] longLatis = longLati.split(",");
					BigDecimal jingdu = new BigDecimal(longLatis[0]);
					BigDecimal weidu = new BigDecimal(longLatis[1]);
					ToalJingDu = ToalJingDu.add(jingdu);
					TotalWeiDu = TotalWeiDu.add(weidu);
				}
			}
		}
		if(StringUtil.isNotEmpty(entity.getLongLati())) {
			String longLati = entity.getLongLati();
			String [] longLatis = longLati.split(",");
			BigDecimal jingdu = new BigDecimal(longLatis[0]);
			BigDecimal weidu = new BigDecimal(longLatis[1]);
			ToalJingDu = ToalJingDu.add(jingdu);
			TotalWeiDu = TotalWeiDu.add(weidu);
		}
		
		BigDecimal pointSize = new BigDecimal(listPoint.size()+1);
		BigDecimal singleJingDu = ToalJingDu.divide(pointSize,6, RoundingMode.HALF_UP);
		BigDecimal singleWeiDu = TotalWeiDu.divide(pointSize,6, RoundingMode.HALF_UP);
    	String jingWeiDu = singleJingDu.toString() + "," + singleWeiDu.toString(); 
		
    	//entity.setLongLati(jingWeiDu);
    	TblLinesEntity lineEntity = (TblLinesEntity) crudService.get(entity.getLineId(), TblLinesEntity.class, true);
    	lineEntity.setCenterAddress(jingWeiDu);
    	crudService.update(lineEntity,true);  //每次新建监控点更新所在线路经纬度.
    	
		TblPointsEntity update = (TblPointsEntity)crudService.update(entity, true); 
		if(update==null) {
			throw new MyDefineException(PoinErrorType.SAVE_EROOR.getCode(), PoinErrorType.SAVE_EROOR.getName(), false, null);
		}
		return success(update);
	}
	
	@RequestMapping("/list") 
	public ResponseEntity<Object>  list(TblPointsEntity point) {
		Page<PointInfoDetail> all = pointDefineService.getPage(point,getPage()); 
		List result = new ArrayList();
		for(PointInfoDetail defineDetail : all) {
			PointDefineVO vo = new PointDefineVO(defineDetail);
			result.add(vo);
		}
		return success(new PageImpl(result, getPage(), all.getTotalElements()));
	}
	
	@RequestMapping("/delete")
	@Transactional
	public ResponseEntity<Object>  delete(TblPointsEntity entity) throws MyDefineException { 
		TblPointsEntity companyEntity = (TblPointsEntity)crudService.get(entity.getPointId(),TblPointsEntity.class,true);
		if(companyEntity == null) {
			throw new MyDefineException(PoinErrorType.POINT_NOT_EXIST);
		}
		Boolean delete = crudService.deleteBySql("DELETE FROM TBL_POINTS where POINT_ID="+companyEntity.getPointId(), true);
		return success(delete); 
	}
	
}
