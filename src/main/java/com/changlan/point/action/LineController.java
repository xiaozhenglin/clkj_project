package com.changlan.point.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.changlan.common.action.BaseController;
import com.changlan.common.configuration.UploadConfiguration;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblCompanyChannelEntity;
import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.entity.TblDvdEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblMonitorSystemEntity;
import com.changlan.common.entity.TblPointAlamDataEntity;
import com.changlan.common.entity.TblPointCategoryEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.entity.TblSystemVarEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.common.util.UUIDUtil;
import com.changlan.point.pojo.CompanyDetail;
import com.changlan.point.pojo.LineDetail;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.point.pojo.PointInfoDetail;
import com.changlan.point.service.ILineService;
import com.changlan.point.service.IMonitorSystemService;
import com.changlan.point.service.IPointDefineService;
import com.changlan.user.pojo.UserErrorType;

@RestController
@RequestMapping("/admin/line")
public class LineController extends BaseController{
	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private ILineService lineService;
	
	@Autowired
	private IPointDefineService pointDefineService;
	
	
	
	//修改或者保存
	@RequestMapping("/save")
	@Transactional
	public ResponseEntity<Object>  lineSave(TblLinesEntity entity ) throws Exception { 
		Boolean exist = lineService.existName(entity);
		if(exist) {
			throw new MyDefineException(PoinErrorType.NAME_EXIST);
		}
//		if(StringUtil.isNotEmpty(entity.getPicture_address())){
//			uploadImg(entity.getLineId(),entity.getPicture_address());
//		}
		//TblMonitorSystemEntity tblMonitorSystem = (TblMonitorSystemEntity)crudService.get(entity.getMonitorId(), TblMonitorSystemEntity.class, true);
		if(entity.getChannelId()!=null)	{	
	    	TblCompanyChannelEntity TblCompanyChannel  =  (TblCompanyChannelEntity) crudService.get(entity.getChannelId(),TblCompanyChannelEntity.class,true);
	    	entity.setChannelName(TblCompanyChannel.getName()); //得到通道名称
		}
    	Map centerMap = new HashMap();
		centerMap.clear();	    	
		centerMap.put("systemCode", new ParamMatcher("longLatiDefault"));
    	TblSystemVarEntity TblSystemVar  =  (TblSystemVarEntity) crudService.findOneByMoreFiled(TblSystemVarEntity.class,centerMap,true);//系统变量得到longLatiDefault
    	//String port = TblSystemVar.getSystemValue();
    	entity.setCenterAddress(TblSystemVar.getSystemValue());
    	
//		if(tblMonitorSystem.getName().indexOf("本体")>-1) {					
			entity.setAddTime(new Date());
			TblLinesEntity update = (TblLinesEntity)crudService.update(entity, true); 			
			if(update == null) {
				throw new MyDefineException(PoinErrorType.SAVE_EROOR);
			}
			return success(update);
//		}else {
//			throw new MyDefineException(PoinErrorType.LINE_CANNOT_CREATE);
//		}
	}
	
//	public ResponseEntity<Object> uploadImg(Integer lineId,String newpath) throws Exception{
//		//MultipartFile file = null ;
//		//logger.info(file.getOriginalFilename()); 
//		String path = "C:/Tulip.jpg";
//		File newFile = new File(newpath);
//		//File newFile = new File(path);
//		MultipartFile file = getMulFileByPath(newFile);
//		
//		String newImageName = UUIDUtil.getUUID() + file.getOriginalFilename();
//		String newRealpath = UploadConfiguration.getUploadPath() + "/" + newImageName;
//		File newRealFile = new File(newRealpath);
//		
//		TblLinesEntity tblLines  = (TblLinesEntity)crudService.get(lineId, TblLinesEntity.class, true);
//		tblLines.setPicture_address(newRealpath);
//		TblLinesEntity update = (TblLinesEntity)crudService.update(tblLines, true);
//		
//		System.out.println(file.getOriginalFilename());
//		//File newFile = new File(newRealpath);								
//		try {
//			file.transferTo(newRealFile);
//		} catch (Exception e) {
//			throw new MyDefineException(UserErrorType.UPLOAD_ERROR);
//		}
//
//		return success("create");
//	}
//	
//	private  MultipartFile getMulFileByPath(File file) {  
//        FileItem fileItem = createFileItem(file.getPath(),file.getName());  
//        MultipartFile mfile = new CommonsMultipartFile(fileItem);  
//        return mfile;  
//    }  
//  
//    private  FileItem createFileItem(String filePath,String fileName)  
//    {  
//        FileItemFactory factory = new DiskFileItemFactory(16, null);  
//        String textFieldName = "textField";  
//        int num = filePath.lastIndexOf(".");  
//       // String extFile = filePath.substring(num);  
//        FileItem item = factory.createItem(textFieldName, "text/plain", true,  fileName);  
//        File newfile = new File(filePath);  
//        int bytesRead = 0;  
//        byte[] buffer = new byte[8192];  
//        try  
//        {  
//            FileInputStream fis = new FileInputStream(newfile);  
//            OutputStream os = item.getOutputStream();  
//            while ((bytesRead = fis.read(buffer, 0, 8192))  
//                != -1)  
//            {  
//                os.write(buffer, 0, bytesRead);  
//            }  
//            os.close();  
//            fis.close();  
//        }  
//        catch (IOException e)  
//        {  
//            e.printStackTrace();  
//        }  
//        return (FileItem) item;  
//    }  
	
	@RequestMapping("/list") 
	public ResponseEntity<Object>  lineList(TblLinesEntity line) throws Exception {
		List<LineDetail> list = lineService.getAll(line); 
		return success(list);
	}
	
	
	@RequestMapping("/delete")
	@Transactional
	public ResponseEntity<Object>  delete(TblLinesEntity entity) throws Exception { 
		TblLinesEntity find = (TblLinesEntity)crudService.get(entity.getLineId(),TblLinesEntity.class,true);
		if(find == null) {
			throw new MyDefineException(PoinErrorType.LINE_NOT_EXITS);
		}
		TblPointsEntity point = new TblPointsEntity();
		point.setLineId(find.getLineId()); 
		List<PointInfoDetail> all = pointDefineService.getAll(point); 
		if(!ListUtil.isEmpty(all)) {
			throw new Exception("线路包含了监控点,不能删除");    
		}
		Boolean delete = crudService.deleteBySql("DELETE FROM TBL_LINES WHERE LINE_ID = " +entity.getLineId() , true);
		return success(delete);
	}
							
	
}
