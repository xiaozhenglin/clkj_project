package com.changlan.other.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblIndicatorCategoriesEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblTemperatureDataEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.FastjsonUtil;
import com.changlan.indicator.service.IIndicatorCategoryService;
import com.changlan.other.entity.PartialDischargeEntity;
import com.changlan.other.pojo.PartialDischargeQuery;
import com.changlan.other.vo.AcceptExtenalDataVO;
import com.changlan.point.service.IPointDataService;
import com.fasterxml.jackson.core.type.TypeReference;

@RestController
@RequestMapping("/admin/accept")
public class AcceptExtenalDataController extends BaseController{
	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private IPointDataService pointDataService;
	
	@Autowired
	private IIndicatorCategoryService categoryService;
	
	@RequestMapping("/extenal/data") 
	public ResponseEntity<Object>  list(String dataVO) {
		List<AcceptExtenalDataVO> accept = FastjsonUtil.toObject(dataVO, new TypeReference<List<AcceptExtenalDataVO>>() {});
		for(int i = 0 ; i< accept.size() ; i++) {
			AcceptExtenalDataVO vo = accept.get(i);
			if(vo.getIndicatorCategoryId() !=null) {
				//根据类别保存数据
//				TblIndicatorCategoriesEntity indicatorCategory = categoryService.getAll(vo.getIndicatorCategoryId()).get(0); 
//				if( indicatorCategory.getName().indexOf("温度") >-1 ) {
//					//温度数据保存
//					TblTemperatureDataEntity data = vo.toTemeratureDataEntity();
//					crudService.update(data, true);
//				}else {
//					//非温度数据保存
//					TblPoinDataEntity data = vo.toEntity();
//					pointDataService.update(data);
//				}
			}
		}
		return success(accept);
	} 
	
}
