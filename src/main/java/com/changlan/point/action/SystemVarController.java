package com.changlan.point.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.StandardServletEnvironment;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.yaml.snakeyaml.Yaml;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblPointCategoryEntity;
import com.changlan.common.entity.TblPointSendCommandEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.entity.TblSystemVarEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.BigDecimalUtil;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.MessageUtils;
import com.changlan.common.util.StringUtil;
import com.changlan.netty.pojo.NettyConfiguration;
import com.changlan.point.pojo.CompanyDetail;
import com.changlan.point.pojo.LanguageType;
import com.changlan.point.pojo.LineDetail;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.point.pojo.PointInfoDetail;
import com.changlan.point.pojo.RedirectType;
import com.changlan.point.pojo.TemperatureQuery;
import com.changlan.point.service.ILineService;
import com.changlan.point.service.IPointDefineService;

@RestController
@RequestMapping("/admin/systemvar")
public class SystemVarController extends BaseController{
	@Autowired
	private ICrudService crudService;
										
	@RequestMapping("/list") 
	public ResponseEntity<Object>  list(Integer systemId) throws FileNotFoundException, UnsupportedEncodingException {
		Map map = new HashMap();
		String  key = MessageUtils.get("user.welcome");
		if(systemId!=null) {
			map.put("systemId", new ParamMatcher(systemId));
		}
		List<TblSystemVarEntity> list = (List<TblSystemVarEntity>)crudService.findByMoreFiled(TblSystemVarEntity.class, map, true);
	    return success(list);
	}
	
	@RequestMapping("/update") 
	public ResponseEntity<Object>  update(TblSystemVarEntity tblSystemVar) throws IOException, MyDefineException {
		//tblSystemVar.setSystemId(4);
		//tblSystemVar.setSystemCode("redirect_url");
		//tblSystemVar.setSystemValue("管理后台");
		
		//tblSystemVar.get
		TblSystemVarEntity systemVar = (TblSystemVarEntity) crudService.get(tblSystemVar.getSystemId(), TblSystemVarEntity.class, true);
		systemVar.setSystemValue(tblSystemVar.getSystemValue());
		TblSystemVarEntity update = (TblSystemVarEntity) crudService.update(systemVar,true); 
		if(update.getSystemValue().equals("ENGLISH")) {
			SessionLocaleResolver localeResolver = new SessionLocaleResolver();
	        localeResolver.setDefaultLocale(Locale.US);
		}
		if(update ==null) {
			throw new MyDefineException(PoinErrorType.SAVE_EROOR);
		}
		return success(update);
		

	}
	
}
