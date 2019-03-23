package com.changlan.point.action;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblPointCategoryEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.StringUtil;
import com.changlan.point.pojo.CompanyDetail;
import com.changlan.point.pojo.LineDetail;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.point.service.ILineService;

@RestController
@RequestMapping("/admin/line")
public class CompanyLineController extends BaseController{
	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private ILineService lineService;
	
	//修改或者保存
	@RequestMapping("/save")
	@Transactional
	public ResponseEntity<Object>  lineSave(TblLinesEntity entity ) throws Exception { 
		Boolean exist = lineService.existGroupName(entity);
		if(exist) {
			throw new MyDefineException(PoinErrorType.NAME_EXIST.getCode(), PoinErrorType.NAME_EXIST.getName(), false, null);
		}
		TblLinesEntity update = (TblLinesEntity)crudService.update(entity, true); 
		if(update == null) {
			throw new MyDefineException(PoinErrorType.SAVE_EROOR.getCode(), PoinErrorType.SAVE_EROOR.getName(), false, null);
		}
		return success(update);
	}
	
	@RequestMapping("/list") 
	public ResponseEntity<Object>  lineList(TblLinesEntity line) throws Exception {
		List<LineDetail> list = lineService.getAll(line); 
		//根据用户权限二次筛选
		TblAdminUserEntity user = userIsLogin();
		String lineIdS = user.getLineId(); 
		if(StringUtil.isNotEmpty(lineIdS)) {
			List<String> stringToList = StringUtil.stringToList(lineIdS); 
			for(LineDetail detail : list) {
				Integer lineId = detail.getLine().getLineId();
				//包含可查看范围
				if(!stringToList.contains(lineId)) {
					list.remove(list.indexOf(detail)); 
				}
			}
		}
		return success(list);
	}
	
	
}
