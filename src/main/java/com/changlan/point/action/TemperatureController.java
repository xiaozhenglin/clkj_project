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

import com.changlan.common.entity.TblTemperatureDTSDataEntity;

import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.StringUtil;
import com.changlan.indicator.pojo.IndiCatorValueDetail;
import com.changlan.indicator.service.IIndicatoryValueService;


import com.changlan.point.pojo.TemperatureDataDetail;
import com.changlan.point.pojo.TemperatureDataQuery;
import com.changlan.point.pojo.TemperatureDtsDataDetail;
import com.changlan.point.pojo.TemperatureDtsQuery;
import com.changlan.point.pojo.TemperatureQuery;
import com.changlan.point.service.IPointCategoryService;
import com.changlan.point.service.ITemperatureDataService;
import com.changlan.point.vo.PoinDataTableVO;
import com.changlan.point.vo.PoinDataVo;
import com.changlan.point.vo.TemperatureDataDtsTableVO;
import com.changlan.point.vo.TemperatureDataTableVO;
import com.changlan.point.vo.TemperatureDataVo;
import com.changlan.point.vo.TemperatureDtsDataVo;
import com.changlan.user.pojo.LoginUser;


@RestController
@RequestMapping("/admin/temperature/data")
public class TemperatureController extends BaseController{
	
	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private ITemperatureDataService temperatureDataService;
	
	@Autowired
	private IIndicatoryValueService indicatorValueService;

	@Autowired
	private IPointCategoryService categoryService;
	
	public ICrudService getCrudService() {
		return crudService;
	}

	public void setCrudService(ICrudService crudService) {
		this.crudService = crudService;
	}

	public ITemperatureDataService getTemperatureDataService() {
		return temperatureDataService;
	}

	public void setTemperatureDataService(ITemperatureDataService temperatureDataService) {
		this.temperatureDataService = temperatureDataService;
	}

	public IIndicatoryValueService getIndicatorValueService() {
		return indicatorValueService;
	}

	public void setIndicatorValueService(IIndicatoryValueService indicatorValueService) {
		this.indicatorValueService = indicatorValueService;
	}

	public IPointCategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(IPointCategoryService categoryService) {
		this.categoryService = categoryService;
	}

	//非数据图表使用数据
	@RequestMapping("/list") 
	public ResponseEntity<Object>  list(TemperatureQuery query) {
		List<TemperatureDataVo> result = new ArrayList<TemperatureDataVo>();
		Page<TemperatureDataDetail> pointDatas = temperatureDataService.getAll(query,getPageAndOrderBy("RECORD_TIME"));
		for(TemperatureDataDetail detail : pointDatas) {
			TemperatureDataVo  vo = new TemperatureDataVo(detail);
			result.add(vo);
		}
		return success(new PageImpl(result, getPage(), pointDatas.getTotalElements()));
	}
	
	@RequestMapping("/listTableDts") 
	public ResponseEntity<Object>  listTableDts(TemperatureDtsQuery query) {		
		List<TblTemperatureDTSDataEntity> list = temperatureDataService.table(query); 
		return success(list);
	}
	
	
	//数据图表
		@RequestMapping("/table") 
		public ResponseEntity<Object>  table(TemperatureDataQuery query) {
			List<TemperatureDataTableVO> result = new ArrayList<TemperatureDataTableVO>();
			Date begin = null  ;
			Date end = null;
			if(query.getBegin()!=null && query.getEnd()!=null) {
				begin = new Date(query.getBegin());
				end = new Date(query.getEnd());	
			}
			List<Integer> indicators = getIndicatorList(query.getCategroryId(),query.getIndicatorIds());
			//遍历 指标
			for(Integer indicatorId : indicators) {
				//根据指标id,监控点Id 和 时间 筛选得到的数据
				List<TemperatureDataDetail> list = temperatureDataService.getTable(begin,end,indicatorId,query.getPointId()); 
				TemperatureDataTableVO vo = new TemperatureDataTableVO(indicatorId,list);
				result.add(vo);
			}
			
			return success(result);
		}
		
		@RequestMapping("/dtsTable") 
		public ResponseEntity<Object>  dtsTable(TemperatureDataQuery query) {
			List<TemperatureDataDtsTableVO> result = new ArrayList<TemperatureDataDtsTableVO>();
			Date begin = null  ;
			Date end = null;
			if(query.getBegin()!=null && query.getEnd()!=null) {
				begin = new Date(query.getBegin());
				end = new Date(query.getEnd());	
			}
			List<Integer> indicators = getIndicatorList(query.getCategroryId(),query.getIndicatorIds());
			//遍历 指标
			for(Integer indicatorId : indicators) {
				//根据指标id,监控点Id 和 时间 筛选得到的数据
				List<TemperatureDtsDataDetail> list = temperatureDataService.getDtsTable(begin,end,indicatorId,query.getPointId(),query.getRefPointDataId()); 
				TemperatureDataDtsTableVO vo = new TemperatureDataDtsTableVO(indicatorId,list);
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
//		
//		//监控点数据报警进行处理
//		@RequestMapping("/down/record") 
//		@Transactional
//		public ResponseEntity<Object>  downRecord(Integer pointDataId,String reason,String downResult) throws Exception { 
//			TblAlarmDownRecordEntity  entity = new TblAlarmDownRecordEntity();
//			entity.setDownResult(downResult);
//			entity.setReason(reason); 
//			if(LoginUser.getCurrentUser()!=null) {
//				entity.setRecordUser(userIsLogin().getAdminUserId()); 
//			}
//			entity.setRecordTime(new Date()); 
//			entity.setPointDataId(pointDataId); 
//			entity = (TblAlarmDownRecordEntity)crudService.update(entity, true);
//			
//			TblTemperatureDataEntity pointData = (TblTemperatureDataEntity)crudService.get(pointDataId, TblTemperatureDataEntity.class, true); 
//			pointData.setAlarmDown("报警已处理");
//			pointData.setAlarmDownRecord(entity.getAlamDownRecordId());
//			crudService.update(pointData, true);
//			return success(true);
//		}
//		
//		//监控点数据报警进行处理 记录列表
//		@RequestMapping("/down/record/list") 
//		public ResponseEntity<Object>  downRecordList(TblAlarmDownRecordEntity entity) throws Exception { 
//			Map map = new HashMap();
//			if(entity.getAlamDownRecordId()!=null) {
//				map.put("alamDownRecordId", new ParamMatcher(entity.getAlamDownRecordId())); 
//			}
//			if(entity.getPointDataId()!=null) {
//				map.put("pointDataId",new ParamMatcher(entity.getPointDataId()));
//			}
//			Object result= crudService.findByMoreFiledAndPage(TblAlarmDownRecordEntity.class, map, true, getPage());
//			return success(result);
//		}
	
	
}