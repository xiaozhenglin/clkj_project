package com.changlan.point.pojo;

import java.util.List;

import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.entity.TblIndicatorCategoriesEntity;
import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblPointCategoryEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.SpringUtil;
import com.changlan.indicator.pojo.IndiCatorValueDetail;
import com.changlan.indicator.service.IIndicatoryValueService;

public class PointDataDetail {
//	extends PointInfoDetail
	private TblPoinDataEntity pointData; //一个监控点的数据
	private IndiCatorValueDetail valueDetail; //对应一个指标信息
	private TblPointsEntity point; //监控点信息

	public PointDataDetail(TblPoinDataEntity pointData,TblPointsEntity point,TblLinesEntity line) {
//		super(point,line);
		this.point = point;
		this.pointData = pointData;
		this.valueDetail = getIndivator(pointData.getIndicatorId());
	}

	private IndiCatorValueDetail getIndivator(Integer indicatorId) {
		IIndicatoryValueService service = SpringUtil.getBean(IIndicatoryValueService.class);
		List<IndiCatorValueDetail> all = service.getAll(indicatorId, null); 
		if(!ListUtil.isEmpty(all)) {
			return all.get(0);
		}
		return null;
	}

	public TblPoinDataEntity getPointData() {
		return pointData;
	}

	public void setPointData(TblPoinDataEntity pointData) {
		this.pointData = pointData;
	}

	public IndiCatorValueDetail getValueDetail() {
		return valueDetail;
	}

	public void setValueDetail(IndiCatorValueDetail valueDetail) {
		this.valueDetail = valueDetail;
	}

	public TblPointsEntity getPoint() {
		return point;
	}

	public void setPoint(TblPointsEntity point) {
		this.point = point;
	}
	
	
	

}