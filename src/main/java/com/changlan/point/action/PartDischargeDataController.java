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
import com.changlan.point.pojo.PartDischargeDataDetail;
import com.changlan.point.pojo.PartDischargeDataQuery;
import com.changlan.point.pojo.PartDischargeQuery;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.point.pojo.PointDataDetail;
import com.changlan.point.pojo.PointDataQuery;
import com.changlan.point.pojo.PointInfoDetail;
import com.changlan.point.pojo.PointQuery;
import com.changlan.point.service.IPartDischargeDataService;
import com.changlan.point.service.IPointCategoryService;
import com.changlan.point.service.IPointDataService;
import com.changlan.point.service.IPointDefineService;
import com.changlan.point.vo.PartDischargeDataTableVO;
import com.changlan.point.vo.PartDischargeDataVo;
import com.changlan.point.vo.PoinDataTableVO;
import com.changlan.point.vo.PoinDataVo;
import com.changlan.point.vo.PointDataListVo;
import com.changlan.user.pojo.LoginUser;

@RestController
@RequestMapping("/admin/partdischarge/data")
public class PartDischargeDataController extends BaseController{
	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private IPartDischargeDataService partDischargeDataService;
	
	@Autowired
	private IIndicatoryValueService indicatorValueService;
	
	@Autowired
	private IPointCategoryService categoryService;
	

	

	
	//非数据图表使用数据
	@RequestMapping("/list") 
	public ResponseEntity<Object>  list(PartDischargeQuery query) {

		List<PartDischargeDataVo> result = new ArrayList<PartDischargeDataVo>();
		Page<PartDischargeDataDetail> partDischargeDatas = partDischargeDataService.getAll(query,getPageAndOrderBy("RECORD_TIME"));
		for(PartDischargeDataDetail detail : partDischargeDatas) {
			PartDischargeDataVo  vo = new PartDischargeDataVo(detail);
			result.add(vo);
		}
		return success(new PageImpl(result, getPage(), partDischargeDatas.getTotalElements()));
	}
	
	//数据图表
	@RequestMapping("/table") 
	public ResponseEntity<Object>  table(PartDischargeDataQuery query) {
		List<PartDischargeDataTableVO> result = new ArrayList<PartDischargeDataTableVO>();
		Date begin = null  ;
		Date end = null;
		if(query.getBegin()!=null && query.getEnd()!=null) {
			begin = new Date(query.getBegin());
			end =  new Date(query.getEnd());	
		}
		List<Integer> indicators = getIndicatorList(query.getCategroryId(),query.getIndicatorIds());
		//List<Integer> indicators = getIndicatorList(6,query.getIndicatorIds());
		//遍历 指标
		for(Integer indicatorId : indicators) {
			//根据指标id,监控点Id 和 时间 筛选得到的数据
			List<PartDischargeDataDetail> list = partDischargeDataService.getTable(begin,end,indicatorId,query.getPointId()); 
			PartDischargeDataTableVO vo = new PartDischargeDataTableVO(indicatorId,list);
			result.add(vo);
		}
		
		return success(result);
	}

	private List<Integer> getIndicatorList(Integer categoryId, String indicatorId) {
		List<Integer> list = new ArrayList<Integer>();
		//传入的都为空
		if(StringUtil.isEmpty(indicatorId) && categoryId ==null) {
			List<IndiCatorValueDetail> all = indicatorValueService.getAll(null, null);
			for(IndiCatorValueDetail detail : all) {
				list.add(detail.getIndicatorValue().getIndicatorId());
			}
			return list;
		}
		
		// 指标值id不为空
		if(StringUtil.isNotEmpty(indicatorId)) {
			List<String> stringToList = StringUtil.stringToList(indicatorId); 
			for(String s : stringToList ) {
				list.add(Integer.parseInt(s));
			}
			
			return list;
		}
		
		//指标类别不为空
		if(categoryId!=null) {
			List<IndiCatorValueDetail> all = indicatorValueService.getAll(null, categoryId); 
			for(IndiCatorValueDetail detail : all) {
				list.add(detail.getIndicatorValue().getIndicatorId());
			}
			return list;
		}
		
		return list;
	}
	

	
}
