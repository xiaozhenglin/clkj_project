package com.changlan.point.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.StringUtil;
import com.changlan.indicator.pojo.IndiCatorValueDetail;
import com.changlan.indicator.service.IIndicatoryValueService;
import com.changlan.point.dao.IEquipmentScreenDao;
import com.changlan.point.pojo.CommonDataQuery;
import com.changlan.point.pojo.PartDischargeDataDetail;
import com.changlan.point.pojo.PointDataDetail;
import com.changlan.point.pojo.TemperatureDataDetail;
import com.changlan.point.service.IEquipmentScreenService;
import com.changlan.point.service.IPartDischargeDataService;
import com.changlan.point.service.IPointDataService;
import com.changlan.point.service.ITemperatureDataService;
import com.changlan.point.vo.CommonDataTableVO;
import com.changlan.point.vo.PoinDataTableVO;

import java.math.BigInteger;

@RestController
@RequestMapping("/admin/equipment")
public class EquipmentScreenController extends BaseController {
	private static final int List = 0;

	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private IEquipmentScreenDao equipmentScreenDao; 
	
	@Autowired
	private IEquipmentScreenService equipmentScreenService;
	
	@Autowired
	private IIndicatoryValueService indicatorValueService;
	
	@Autowired
	private IPointDataService pointDataService;
	
	@Autowired
	private IPartDischargeDataService partDischargeDataService;
	
	@Autowired
	private ITemperatureDataService temperatureDataService;
	
	//实时数据展示
	@RequestMapping("/currentPointInfo") 
	public ResponseEntity<Object>  currentPointInfoDisplay(CommonDataQuery query) {
		List<CommonDataTableVO> result = new ArrayList<CommonDataTableVO>();
		Date begin = null  ;
		Date end = null;
		if(query.getBegin()!=null && query.getEnd()!=null) {
			begin = new Date(query.getBegin());
			end =  new Date(query.getEnd());	
		}
		//query.setPointId(1);  //测试数据
		
		List<Object> listPointIndicators = (List<Object>)equipmentScreenDao.queryPointIndicatorList(query);
		List<Integer> indicatorsPointList = getIndicatorList(query.getCategroryId(),query.getIndicatorIds(),listPointIndicators);
		if(indicatorsPointList.size()!= 0 ) {
		//遍历 指标
			for(Integer indicatorId : indicatorsPointList) {
				//根据指标id,监控点Id 和 时间 筛选得到的数据
				List<PointDataDetail> listPointData = pointDataService.getTable(begin,end,indicatorId,query.getPointId()); 
				CommonDataTableVO vo = new CommonDataTableVO();
				CommonDataTableVO value = vo.CommonPoinDataTableVOSinger(indicatorId, listPointData);
				result.add(value);
			}
		}
		
		List<Object> listTemperatureIndicators = (List<Object>)equipmentScreenDao.queryTemperatureIndicatorList(query);
		List<Integer> indicatorsTemperatureList = getIndicatorList(query.getCategroryId(),query.getIndicatorIds(),listTemperatureIndicators);
		if(indicatorsTemperatureList.size()!=0) {
			//遍历 指标
			for(Integer indicatorId : indicatorsTemperatureList) {
				//根据指标id,监控点Id 和 时间 筛选得到的数据
				List<TemperatureDataDetail> listTemperatureData = temperatureDataService.getTable(begin,end,indicatorId,query.getPointId()); 
				CommonDataTableVO vo = new CommonDataTableVO();
				CommonDataTableVO value = vo.CommonTemperatureDataTableVOSinger(indicatorId, listTemperatureData);
				result.add(value);
			}
		}
		
		List<Object> listPartDischargeIndicators = (List<Object>)equipmentScreenDao.queryPartDischargeIndicatorList(query);
		List<Integer> indicatorsPartDischargeList = getIndicatorList(query.getCategroryId(),query.getIndicatorIds(),listPartDischargeIndicators);
		if(indicatorsPartDischargeList.size()!=0) {
			//遍历 指标
			for(Integer indicatorId : indicatorsPartDischargeList) {
				//根据指标id,监控点Id 和 时间 筛选得到的数据
				List<PartDischargeDataDetail> listPartDischargeData = partDischargeDataService.getTable(begin,end,indicatorId,query.getPointId()); 
				CommonDataTableVO vo = new CommonDataTableVO();
				CommonDataTableVO value = vo.CommonPartDischargeDataTableVOSinger(indicatorId, listPartDischargeData);
				result.add(value);
			}
		}
				
		return success(result);
	}
	
    
	//历史数据展示 
	@RequestMapping("/historyPointInfo") 
	public ResponseEntity<Object>  historyPointInfoDisplay(CommonDataQuery query) {
		List<CommonDataTableVO> result = new ArrayList<CommonDataTableVO>();
		Date begin = null  ;
		Date end = null;
		if(query.getBegin()!=null && query.getEnd()!=null) {
			begin = new Date(query.getBegin());
			end =  new Date(query.getEnd());	
		}
		//query.setPointId(1);  //测试数据
		
		List<Object> listPointIndicators = (List<Object>)equipmentScreenDao.queryPointIndicatorList(query);
		List<Integer> indicatorsPointList = getIndicatorList(query.getCategroryId(),query.getIndicatorIds(),listPointIndicators);
		if(indicatorsPointList.size()!= 0 ) {
		//遍历 指标
			for(Integer indicatorId : indicatorsPointList) {
				//根据指标id,监控点Id 和 时间 筛选得到的数据
				List<PointDataDetail> listPointData = pointDataService.getTable(begin,end,indicatorId,query.getPointId()); 
				CommonDataTableVO vo = new CommonDataTableVO();
				CommonDataTableVO value = vo.CommonPoinDataTableVO(indicatorId, listPointData);
				result.add(value);
			}
		}
		
		List<Object> listTemperatureIndicators = (List<Object>)equipmentScreenDao.queryTemperatureIndicatorList(query);
		List<Integer> indicatorsTemperatureList = getIndicatorList(query.getCategroryId(),query.getIndicatorIds(),listTemperatureIndicators);
		if(indicatorsTemperatureList.size()!=0) {
			//遍历 指标
			for(Integer indicatorId : indicatorsTemperatureList) {
				//根据指标id,监控点Id 和 时间 筛选得到的数据
				List<TemperatureDataDetail> listTemperatureData = temperatureDataService.getTable(begin,end,indicatorId,query.getPointId()); 
				CommonDataTableVO vo = new CommonDataTableVO();
				CommonDataTableVO value = vo.CommonTemperatureDataTableVO(indicatorId, listTemperatureData);
				result.add(value);
			}
		}
		
		List<Object> listPartDischargeIndicators = (List<Object>)equipmentScreenDao.queryPartDischargeIndicatorList(query);
		List<Integer> indicatorsPartDischargeList = getIndicatorList(query.getCategroryId(),query.getIndicatorIds(),listPartDischargeIndicators);
		if(indicatorsPartDischargeList.size()!=0) {
			//遍历 指标
			for(Integer indicatorId : indicatorsPartDischargeList) {
				//根据指标id,监控点Id 和 时间 筛选得到的数据
				List<PartDischargeDataDetail> listPartDischargeData = partDischargeDataService.getTable(begin,end,indicatorId,query.getPointId()); 
				CommonDataTableVO vo = new CommonDataTableVO();
				CommonDataTableVO value = vo.CommonPartDischargeDataTableVO(indicatorId, listPartDischargeData);
				result.add(value);
			}
		}
		
		
		return success(result);
	}
	
	private List<Integer> getIndicatorList(Integer categoryId, String indicatorId ,List<Object> listPointIndicators) {
		List<Integer> indicatorsList = new ArrayList<Integer>();
		for(Object o : listPointIndicators ) {
			Object [] obj = (Object[]) o;
			indicatorsList.add(Integer.parseInt(obj[1].toString()));
		}
		
		List<Integer> list = new ArrayList<Integer>();
		//只有符合条件的indicatorId 才传入 计算
		if(StringUtil.isEmpty(indicatorId) && categoryId ==null) {
			
			return indicatorsList;
			
		}
		
		// 指标值id不为空
		if(StringUtil.isNotEmpty(indicatorId)) {
			for(Integer o : indicatorsList ) {
				if(o==Integer.parseInt(indicatorId)) {  //只有符合条件的indicatorId 才传入 计算
					List<IndiCatorValueDetail> all = indicatorValueService.getAll(Integer.parseInt(indicatorId), null);
					for(IndiCatorValueDetail detail : all) {
						list.add(detail.getIndicatorValue().getIndicatorId());
					}
				}
				return list;
			}						
		}
		
		//指标类别不为空  	
		if(categoryId!=null) {
			List<IndiCatorValueDetail> all = indicatorValueService.getAll(null, categoryId); 
			for(IndiCatorValueDetail detail : all) {
				
				Integer IndicatorIdTmp =  detail.getIndicatorValue().getIndicatorId();
				for(Integer o : indicatorsList ) {
					if(o==IndicatorIdTmp) {  //只有符合条件的indicatorId 才传入 计算
						list.add(detail.getIndicatorValue().getIndicatorId());
					}
				}
			}
			return list;
		}
		
		return list;
	}
	
}
