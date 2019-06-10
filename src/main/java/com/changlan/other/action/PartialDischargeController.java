package com.changlan.other.action;

import java.io.File;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberInputStream;
import java.io.LineNumberReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblCommandRecordEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.other.dao.IPartialDischargeDao;
import com.changlan.other.entity.DeviceData;
import com.changlan.other.entity.PartialDischargeEntity;
import com.changlan.other.entity.SimpleEntity;
import com.changlan.other.pojo.PartialDischargeQuery;
import com.changlan.other.service.IPartialDischargeService;
import com.changlan.other.vo.DeviceDataVo;
import com.changlan.point.pojo.PointInfoDetail;
import com.changlan.point.pojo.PointQuery;
import com.changlan.point.service.IPointDefineService;
import com.changlan.point.vo.PointDataListVo;


@RestController
@RequestMapping("/admin/Partial/Discharge")
public class PartialDischargeController extends BaseController{
	
	private static String oneString;

	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private IPartialDischargeService partialDischargeService ;
	
	@Autowired
	private IPartialDischargeDao dao;
	
	@Autowired
	private IPointDefineService pointDefineService;
	
	@RequestMapping("/list") 
	public ResponseEntity<Object>  list(PartialDischargeQuery query) {
		
//		TblPointsEntity point = new TblPointsEntity();
//		Integer lineId = query.getLineId(); 
//		if(lineId!=null) { 
//			point.setLineId(lineId);
//		}
//		if(query.getCategoryId()!=null) {
//			point.setPointCatagoryId(query.getCategoryId()); 
//		}
//		if(query.getPointId()!=null) {
//			point.setPointId(query.getPointId());  
//		}
//		//当前线路下的监控系统      找出所有的监控点
//		List<PointInfoDetail> all = pointDefineService.getAll(point);
		
		List<PartialDischargeEntity> list = (List<PartialDischargeEntity>)partialDischargeService.list(query); 
		return success(list);
	}
	 
	@RequestMapping("/table") 
	public ResponseEntity<Object>  table(PartialDischargeQuery query) {
		//找出所有的通道id
		List<SimpleEntity> channelSettingList = (List<SimpleEntity>)partialDischargeService.channelSettingList(query); 
		List<DeviceDataVo> result = new ArrayList<DeviceDataVo>();
		if(!ListUtil.isEmpty(channelSettingList)) {
			for(SimpleEntity simple :channelSettingList ) {
				//根据通道Id来筛选数据
				query.setChannelSettings_id(simple.getId()); 
				List<DeviceData> table = (List<DeviceData>)partialDischargeService.table(query); 
				DeviceDataVo vo = new DeviceDataVo(simple,table);
				result.add(vo);
			}
		}
		return success(result);
	}

	
	public static void main(String[] args) {
		String content = "0114B4B30601AC738C01B6756501B6773F01A4791801AC7AF2019F7CCC01B67EA601AE807E01BE825801AC843201B4860B01B287E401A889BD01A08B9601AA8D6F01B08F4901BA912201B692FB01AC94D501A496AE01A8988701AA9A6101A69C3B01A29E14019F9FEE01A2A1C8019DA3A101B2A57B01A2A755019DA92E01A6AB080190ACE1019BAEBA01ACB093019FB26C01AEB44601A6B62001A8B7F90199B9D201A0BBAB01AABD8501A8BF5F01A6C13801AEC3121AD3";
		String substring = content.substring(10);  
		System.out.println(substring); 
		int i = 0 ;
		while(i<=substring.length()-8) {
			String amplitude = substring.substring(i, i+4);
			amplitude = StringUtil.decimalConvert(amplitude, 16, 10, null); 
			System.out.println("幅值"+amplitude); 
			String phase = substring.substring(i+4, i+8);
			phase = StringUtil.decimalConvert(phase, 16, 10, null); 
			System.out.println("相位"+phase); 
			
			
			BigDecimal fuzhi = new BigDecimal(amplitude); 
			BigDecimal diejiaXiShu = new BigDecimal(1000); 
			BigDecimal xiangwei = new BigDecimal(phase); 
			
			BigDecimal shang = xiangwei.divideToIntegralValue(diejiaXiShu);
			System.out.println("商值"+shang);
			BigDecimal yushu = xiangwei.divideAndRemainder(diejiaXiShu)[1];
			
			System.out.println("余数"+yushu);
			BigDecimal diejiaXiangWei = yushu.divide(diejiaXiShu).multiply(new BigDecimal(360));
			
			System.out.println("叠加相位"+diejiaXiangWei.intValue());
			
			i= i+8;
		}
	}
	
		
}
