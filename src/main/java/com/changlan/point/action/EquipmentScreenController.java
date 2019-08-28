package com.changlan.point.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.alarm.vo.ScreenAlarmMessageBoxVO;
import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ExcelUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.indicator.pojo.IndiCatorValueDetail;
import com.changlan.indicator.service.IIndicatoryValueService;
import com.changlan.point.dao.IEquipmentScreenDao;
import com.changlan.point.pojo.CommonDataQuery;
import com.changlan.point.pojo.PartDischargeDataDetail;
import com.changlan.point.pojo.PointDataDetail;
import com.changlan.point.pojo.TemperatureDataDetail;
import com.changlan.point.pojo.TemperatureDtsDataDetail;
import com.changlan.point.service.IEquipmentScreenService;
import com.changlan.point.service.IPartDischargeDataService;
import com.changlan.point.service.IPointDataService;
import com.changlan.point.service.ITemperatureDataService;
import com.changlan.point.vo.CommonDataTableVO;
import com.changlan.point.vo.PoinDataTableVO;

import java.io.OutputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;

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
		List<CommonDataTableVO> result = getCurrentPointInfo(query);
		query =null;				
		return success(result);
	}
	
	private List<CommonDataTableVO> getCurrentPointInfo(CommonDataQuery query){
		List<CommonDataTableVO> result = new ArrayList<CommonDataTableVO>();
		result.clear();
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
				TblIndicatorValueEntity find = (TblIndicatorValueEntity)crudService.get(indicatorId,TblIndicatorValueEntity.class,true);
				/*
				 * if(find.getName().indexOf("DST")>-1) { List<TemperatureDtsDataDetail>
				 * listTemperatureData =
				 * temperatureDataService.getDtsTable(begin,end,indicatorId,query.getPointId());
				 * CommonDataTableVO vo = new CommonDataTableVO(); CommonDataTableVO value =
				 * vo.CommonTemperatureDSTDataTableVOSinger(indicatorId, listTemperatureData);
				 * result.add(value);
				 * 
				 * }else {
				 */			
					List<TemperatureDataDetail> listTemperatureData = temperatureDataService.getTable(begin,end,indicatorId,query.getPointId()); 
					CommonDataTableVO vo = new CommonDataTableVO();
					CommonDataTableVO value = vo.CommonTemperatureDataTableVOSinger(indicatorId, listTemperatureData);
					result.add(value);
				//}
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
		return result;
	}
	
	@RequestMapping(value = "/currExcel")
	public void exportCurr(HttpServletRequest request, HttpServletResponse response,CommonDataQuery query) throws Exception {
		List<CommonDataTableVO> result = getCurrentPointInfo(query);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//excel标题
	    String[] title = {"指标名称", "指标值", "创建日期"};

	    //excel文件名
	    String fileName = "实时数据表" + System.currentTimeMillis() + ".xls";

	    //sheet名
	    String sheetName = "实时数据页";

	    String content[][] = new String[result.size()][title.length];
	    for (int i = 0; i < result.size(); i++) {

	    	CommonDataTableVO obj = result.get(i);
	      
	        content[i][0] = obj.getIndicatorName();
	        content[i][1] = obj.getResults().get(0).getValue();
	        System.out.println(obj.getResults().get(0).getRecordTime().toGMTString());
	        //if(obj.getResults().get(0).getRecordTime()!=null) {
	        	content[i][2] = sdf.format( obj.getResults().get(0).getRecordTime());
	        //}
	        
	    }

	    //创建HSSFWorkbook
	    HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
	    //响应到客户端
	    try {
	        this.setResponseHeader(response, fileName);
	        OutputStream os = response.getOutputStream();
	        wb.write(os);
	        os.flush();
	        os.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
	@RequestMapping(value = "/histExcel")
	public void exportHist(HttpServletRequest request, HttpServletResponse response,CommonDataQuery query) throws Exception {
		List<CommonDataTableVO> result = getHistoryPointInfo(query);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//excel标题
	    String[] title = {"指标名称", "指标值", "创建日期"};

	    //excel文件名
	    String fileName = "历史数据表" + System.currentTimeMillis() + ".xls";

	    //sheet名
	    String sheetName = "历史数据页";
	    int valueSize = 0;
	    int totalSize = 0;
	    for (int i = 0; i < result.size(); i++) {
	    	CommonDataTableVO obj = result.get(i);
	    	totalSize += obj.getResults().size();
	    }
	    
	     
	    String content[][] = new String[totalSize][title.length];
	    for (int i = 0; i < result.size(); i++) {

	    	CommonDataTableVO obj = result.get(i);
	    	
	    	//valueSize += obj.getResults().size();
	        for(int j = 0; j < obj.getResults().size() ; j++) {
		        content[valueSize][0] = obj.getIndicatorName();
		        content[valueSize][1] = obj.getResults().get(j).getValue();
		        if(obj.getResults().get(j).getRecordTime()!=null) {
		        	content[valueSize][2] = sdf.format(obj.getResults().get(j).getRecordTime());
		        }
		        valueSize++;
	        }
	        
	    }

	    //创建HSSFWorkbook
	    HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
	    //响应到客户端
	    try {
	        this.setResponseHeader(response, fileName);
	        OutputStream os = response.getOutputStream();
	        wb.write(os);
	        os.flush();
	        os.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
    
	//历史数据展示 
	@RequestMapping("/historyPointInfo") 
	public ResponseEntity<Object>  historyPointInfoDisplay(CommonDataQuery query) {
		List<CommonDataTableVO> result = getHistoryPointInfo(query);
		query =null;		
		return success(result);
	}
	
	private List<CommonDataTableVO> getHistoryPointInfo(CommonDataQuery query){
		List<CommonDataTableVO> result = new ArrayList<CommonDataTableVO>();
		result.clear();
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
				TblIndicatorValueEntity find = (TblIndicatorValueEntity)crudService.get(indicatorId,TblIndicatorValueEntity.class,true);
				if(find.getName().indexOf("DST")>-1) {
					List<TemperatureDtsDataDetail> listTemperatureData = temperatureDataService.getDtsTable(begin,end,indicatorId,query.getPointId(),null); 
					CommonDataTableVO vo = new CommonDataTableVO();
					CommonDataTableVO value = vo.CommonTemperatureDSTDataTableVO(indicatorId, listTemperatureData);
					result.add(value);
					
				}else {				
					List<TemperatureDataDetail> listTemperatureData = temperatureDataService.getTable(begin,end,indicatorId,query.getPointId()); 
					CommonDataTableVO vo = new CommonDataTableVO();
					CommonDataTableVO value = vo.CommonTemperatureDataTableVOSinger(indicatorId, listTemperatureData);
					result.add(value);
				}
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
		return result;
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
	
	private void setResponseHeader(HttpServletResponse response, String fileName) {
		try {
	        try {
	            fileName = new String(fileName.getBytes(), "ISO8859-1");
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        response.setContentType("application/octet-stream;charset=ISO8859-1");
	        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
	        response.addHeader("Pargam", "no-cache");
	        response.addHeader("Cache-Control", "no-cache");
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
	
}
