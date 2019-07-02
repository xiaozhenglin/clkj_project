package com.changlan.point.action;

import java.util.ArrayList;
import java.util.Date;
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
import com.changlan.common.entity.TblAlarmDownRecordEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.pojo.ParamMatcher;
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
import com.changlan.point.vo.PoinDataVo;
import com.changlan.point.vo.PointDataListVo;
import com.changlan.user.pojo.LoginUser;

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
//		List<PointDataListVo> result = new ArrayList();
//		
//		TblPointsEntity point = new TblPointsEntity();
//		Integer lineId = query.getLineId(); 
//		if(lineId!=null) { 
//			point.setLineId(lineId);
//		}
//		if(query.getCategroryId()!=null) {
//			point.setPointCatagoryId(query.getCategroryId()); 
//		}
//		if(query.getPointId()!=null) {
//			point.setPointId(query.getPointId()); 
//		}
//		//当前线路下的监控系统      找出所有的监控点 可以优化不需要查出详情。
//		List<PointInfoDetail> all = pointDefineService.getAll(point);
//		
//		for(PointInfoDetail detail : all) {
//			//每个监控点的数据列表
//			query.setPointId(detail.getPoint().getPointId()); 
//			Page<PointDataDetail> pointDatas = pointDataService.getAll(query,getPage()); 
//			PointDataListVo vo= new PointDataListVo(detail.getPoint(),pointDatas);
//			result.add(vo);
//		}
//		return success(result);
		List<PoinDataVo> result = new ArrayList<PoinDataVo>();
		Page<PointDataDetail> pointDatas = pointDataService.getAll(query,getPageAndOrderBy("RECORD_TIME"));
		for(PointDataDetail detail : pointDatas) {
			PoinDataVo  vo = new PoinDataVo(detail);
			result.add(vo);
		}
		return success(new PageImpl(result, getPage(), pointDatas.getTotalElements()));
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
	
	//监控点数据报警进行处理
//	@RequestMapping("/down/record") 
//	@Transactional
//	public ResponseEntity<Object>  downRecord(Integer pointDataId,String reason,String downResult) throws Exception { 
//		TblAlarmDownRecordEntity  entity = new TblAlarmDownRecordEntity();
//		entity.setDownResult(downResult);
//		entity.setReason(reason); 
//		if(LoginUser.getCurrentUser()!=null) {
//			entity.setRecordUser(userIsLogin().getAdminUserId()); 
//		}
//		entity.setRecordTime(new Date()); 
//		entity.setPointDataId(pointDataId); 
//		entity = (TblAlarmDownRecordEntity)crudService.update(entity, true);
//		
//		TblPoinDataEntity pointData = (TblPoinDataEntity)crudService.get(pointDataId, TblPoinDataEntity.class, true); 
//		pointData.setAlarmDown("报警已处理");
//		pointData.setAlarmDownRecord(entity.getAlamDownRecordId());
//		crudService.update(pointData, true);
//		return success(true);
//	}
	
//	//监控点数据报警进行处理 记录列表
//	@RequestMapping("/down/record/list") 
//	public ResponseEntity<Object>  downRecordList(TblAlarmDownRecordEntity entity) throws Exception { 
//		Map map = new HashMap();
//		if(entity.getAlamDownRecordId()!=null) {
//			map.put("alamDownRecordId", new ParamMatcher(entity.getAlamDownRecordId())); 
//		}
//		if(entity.getPointDataId()!=null) {
//			map.put("pointDataId",new ParamMatcher(entity.getPointDataId()));
//		}
//		Object result= crudService.findByMoreFiledAndPage(TblAlarmDownRecordEntity.class, map, true, getPage());
//		return success(result);
//	}
	
}
