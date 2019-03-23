package com.changlan.point.action;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblFunInfoEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.StringUtil;
import com.changlan.point.pojo.CompanyDetail;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.point.service.ICompanyInfoService;

@RestController
@RequestMapping("/admin/company")
public class CompanyInfoController extends BaseController{
	
	@Autowired
	ICrudService crudService;
	
	@Autowired
	private ICompanyInfoService companyInfoService;
	
	@RequestMapping("/list")
	public ResponseEntity<Object>  lineList(TblCompanyEntity company) throws Exception { 
		List<CompanyDetail> list = companyInfoService.companyList(company);
		//根据用户权限二次筛选
		TblAdminUserEntity user = userIsLogin();
		String companyIdS = user.getCompanyId(); 
		if(StringUtil.isNotEmpty(companyIdS)) {
			List<String> stringToList = StringUtil.stringToList(companyIdS); 
			for(CompanyDetail detail : list) {
				Integer companyId = detail.getCompany().getCompanyId(); 
				if(!stringToList.contains(companyId)) {
					list.remove(list.indexOf(detail)); 
				}
			}
		}
		return success(list);
	}
	
	@RequestMapping("/save")
	@Transactional
	public ResponseEntity<Object>  save(TblCompanyEntity entity) throws MyDefineException { 
		Boolean exist = companyInfoService.existName(entity);
		if(exist) {
			throw new MyDefineException(PoinErrorType.COMPANY_NAME_EXIST.getCode(), PoinErrorType.COMPANY_NAME_EXIST.getName(), false, null);
		}
		TblCompanyEntity update = (TblCompanyEntity)crudService.update(entity, true); 
		if(update ==null) {
			throw new MyDefineException(PoinErrorType.SAVE_EROOR.getCode(), PoinErrorType.SAVE_EROOR.getName(), false, null);
		}
		return success(update);
	}
	
}
