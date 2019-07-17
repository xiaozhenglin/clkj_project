package com.changlan.common.action;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.changlan.alarm.pojo.AlarmDownType;
import com.changlan.alarm.pojo.TblAlarmDataDetail;
import com.changlan.alarm.service.IAlarmDataService;
import com.changlan.alarm.vo.ScreenAlarmMessageBoxVO;
import com.changlan.common.dao.ICrudDao;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblPointAlamDataEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ExcelUtil;
import com.changlan.common.util.SpringUtil;
import com.changlan.netty.service.INettyService;

@RestController
public class ExcelController {
	
	@Autowired
	IAlarmDataService alarmDataService;
	
	@Autowired
	ICrudService crudService;
	
	@RequestMapping(value = "/admin/export/excel")
	public void export(HttpServletRequest request, HttpServletResponse response) throws Exception {

	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    //获取数据
	    INettyService nettyService = SpringUtil.getBean(INettyService.class); 
		//查找报警数据 
		IAlarmDataService alarmdAlarmDataService = SpringUtil.getBean(IAlarmDataService.class);
		TblPointAlamDataEntity query = new TblPointAlamDataEntity();
//		query.setDownStatus(AlarmDownType.UN_DOWN.toString());
		List<TblAlarmDataDetail> list = alarmdAlarmDataService.getList(query);
		
		//封装数据格式
		List<ScreenAlarmMessageBoxVO> result = new ArrayList<ScreenAlarmMessageBoxVO>();
		for(TblAlarmDataDetail  detail :  list) {
			ScreenAlarmMessageBoxVO  vo = new ScreenAlarmMessageBoxVO(detail);
			result.add(vo);
		}
	    
	    
	    

	    //excel标题
	    String[] title = {"设备名称", "指标名称", "指标值", "创建日期"};

	    //excel文件名
	    String fileName = "报警数据表" + System.currentTimeMillis() + ".xls";

	    //sheet名
	    String sheetName = "报警数据页";

	    String content[][] = new String[list.size()][title.length];
	    for (int i = 0; i < result.size(); i++) {


	        ScreenAlarmMessageBoxVO obj = result.get(i);
	        content[i][0] = obj.getPointName();
	        content[i][1] = obj.getIndicatorName();
	        content[i][2] = obj.getIndicatorValue();
	        content[i][3] = sdf.format(obj.getRecordTime());
	        
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
