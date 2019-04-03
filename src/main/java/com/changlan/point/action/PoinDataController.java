package com.changlan.point.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblAlarmDownRecordEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.DateUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.indicator.pojo.IndiCatorValueDetail;
import com.changlan.indicator.service.IIndicatoryValueService;
import com.changlan.other.service.IPartialDischargeService;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.point.pojo.PointDataDetail;
import com.changlan.point.pojo.PointDataQuery;
import com.changlan.point.pojo.PointInfoDetail;
import com.changlan.point.pojo.PointQuery;
import com.changlan.point.service.IPointCategoryService;
import com.changlan.point.service.IPointDataService;
import com.changlan.point.service.IPointDefineService;
import com.changlan.point.vo.PoinDataTableVO;
import com.changlan.point.vo.PointDataListVo;

@RestController
@RequestMapping("/admin/point/data")
public class PoinDataController extends BaseController{
	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private IPointDataService pointDataService;
	
	@Autowired
	private IIndicatoryValueService indicatorValueService;
	
	@Autowired
	private IPointCategoryService categoryService;
	
	@Autowired
	private IPointDefineService pointDefineService;
	
	@Autowired
	IPartialDischargeService partialDischargeService ;
	
	//非数据图表使用数据
	@RequestMapping("/list") 
	public ResponseEntity<Object>  list(PointQuery query) {
		List<PointDataListVo> result = new ArrayList();
		
		TblPointsEntity point = new TblPointsEntity();
		Integer lineId = query.getLineId(); 
		if(lineId!=null) { 
			point.setLineId(lineId);
		}
		if(query.getCategroryId()!=null) {
			point.setPointCatagoryId(query.getCategroryId()); 
		}
		if(query.getPointId()!=null) {
			point.setPointId(query.getPointId()); 
		}
		//当前线路下的监控系统      找出所有的监控点 可以优化不需要查出详情。
		List<PointInfoDetail> all = pointDefineService.getAll(point);
		
		for(PointInfoDetail detail : all) {
			//每个监控点的数据列表
			query.setPointId(detail.getPoint().getPointId()); 
			Page<PointDataDetail> pointDatas = pointDataService.getAll(query,getPage()); 
			
			
			
			PointDataListVo vo= new PointDataListVo(pointDatas);
			result.add(vo);
		}
		return success(result);
	}
	
	//数据图表
	@RequestMapping("/table") 
	public ResponseEntity<Object>  table(PointDataQuery query) {
		List<PoinDataTableVO> result = new ArrayList<PoinDataTableVO>();
		Date begin = new Date(query.getBegin());
		Date end = new Date(query.getEnd());	
		
		List<Integer> indicators = getIndicatorList(query.getCategroryId(),query.getIndicatorIds());
		//遍历 指标
		for(Integer indicatorId : indicators) {
			//根据指标id,监控点Id 和 时间 筛选得到的数据
			List<PointDataDetail> list = pointDataService.getTable(begin,end,indicatorId,query.getPointId()); 
			PoinDataTableVO vo = new PoinDataTableVO(indicatorId,list);
			result.add(vo);
		}
		
		return success(result);
	}

	private List<Integer> getIndicatorList(Integer categoryId, String indicatorId) {
		List<Integer> list = new ArrayList<Integer>();
		if(StringUtil.isEmpty(indicatorId)) {
			List<IndiCatorValueDetail> all = indicatorValueService.getAll(null, categoryId); 
			for(IndiCatorValueDetail detail : all) {
				list.add(detail.getIndicatorValue().getIndicatorId());
			}
		}else {
			List<String> stringToList = StringUtil.stringToList(indicatorId); 
			for(String s : stringToList ) {
				list.add(Integer.parseInt(s));
			}
		}
		return list;
	}
	
//	未加入权限表@RequestMapping("/down") 
//	public ResponseEntity<Object>  down(TblPoinDataEntity entity) {
//		TblPoinDataEntity update = pointDataService.update(entity); 
//		return success(update);
//	}
//	
//	//监控点数据报警进行处理
//	@RequestMapping("/down/record") 
//	public ResponseEntity<Object>  downRecord(String reason,String downResult) throws Exception { 
//		TblAlarmDownRecordEntity  entity = new TblAlarmDownRecordEntity();
//		entity.setDownResult(downResult);
//		entity.setReason(reason); 
//		entity.setRecordUser(userIsLogin().getAdminUserId()); 
//		entity.setRecordTime(new Date()); 
//		Object update = crudService.update(entity, true);
//		return success(update);
//	}
	
}
