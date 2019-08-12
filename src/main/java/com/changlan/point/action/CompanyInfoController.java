package com.changlan.point.action;

import java.util.ArrayList;
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
import com.changlan.indicator.pojo.IndiCatorValueDetail;
import com.changlan.point.pojo.CompanyDetail;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.point.service.ICompanyInfoService;
import com.changlan.point.vo.CompanyVo;

@RestController
@RequestMapping("/admin/company")
public class CompanyInfoController extends BaseController{
	
	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private ICompanyInfoService companyInfoService;
	
	@RequestMapping("/listTree")
	public ResponseEntity<Object>  lineList(TblCompanyEntity company) throws Exception { 
		//List<CompanyDetail> list = companyInfoService.companyList(company);
		List<TblCompanyEntity> list = companyInfoService.getAll(company.getCompanyId());
		List<CompanyVo> result = new ArrayList<CompanyVo>();
		//for(CompanyDetail detail : list) {
		for(TblCompanyEntity detail : list) {
			//CompanyVo vo = new CompanyVo(detail.getCompany());
			CompanyVo vo = new CompanyVo(detail);
			result.add(vo);
		}
		return success(result);
	}
	
	@RequestMapping("/list")
	public ResponseEntity<Object>  lineList(Integer companyId) throws Exception { 
		//List<CompanyDetail> list = companyInfoService.companyList(company);
		List<TblCompanyEntity> list = companyInfoService.getAll(companyId);
		return success(list);
	}
	
	
	
	
	@RequestMapping("/save")
	@Transactional
	public ResponseEntity<Object>  save(TblCompanyEntity entity) throws Exception { 
		Boolean exist = companyInfoService.existName(entity);
		if(exist) {
			throw new MyDefineException(PoinErrorType.COMPANY_NAME_EXIST.getCode(), PoinErrorType.COMPANY_NAME_EXIST.getName(), false, null);
		}
		/*
		 * if(entity.getGroupId()==null) { entity.setGroupId(1); }
		 */
		TblCompanyEntity update = (TblCompanyEntity)crudService.update(entity, true); 
		if(update ==null) {
			throw new MyDefineException(PoinErrorType.SAVE_EROOR.getCode(), PoinErrorType.SAVE_EROOR.getName(), false, null);
		}
		return success(update);
	}
	
	@RequestMapping("/delete")
	@Transactional
	public ResponseEntity<Object>  delete(TblCompanyEntity entity) throws Exception { 
		TblCompanyEntity find = (TblCompanyEntity)crudService.get(entity.getCompanyId(),TblCompanyEntity.class,true);
		if(find == null) {
			throw new MyDefineException(PoinErrorType.COMPANY_NOT_EXIST);
		}
		Boolean delete = crudService.deleteBySql("DELETE FROM TBL_COMPANY WHERE COMPANY_ID = " +entity.getCompanyId() , true);
		return success(delete);
	}
	
}
