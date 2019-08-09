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
import java.util.Iterator;
import java.util.List;
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
import org.yaml.snakeyaml.Yaml;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblPointCategoryEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.BigDecimalUtil;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.netty.pojo.NettyConfiguration;
import com.changlan.point.pojo.CompanyDetail;
import com.changlan.point.pojo.LineDetail;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.point.pojo.PointInfoDetail;
import com.changlan.point.pojo.TemperatureQuery;
import com.changlan.point.service.ILineService;
import com.changlan.point.service.IPointDefineService;

@RestController
@RequestMapping("/admin/var")
public class VarController extends BaseController{
	
	@RequestMapping("/setNettyServerPort") 
	public ResponseEntity<Object>  setNettyServerPort(int nettyServerPort) throws Exception {
		Yaml yml = new Yaml();
		String ymlPath =  VarController.class.getResource("/").getPath().substring(1)  + "application.yml" ;
		InputStreamReader reader = new InputStreamReader(new FileInputStream(ymlPath),"UTF-8");
		Map map = yml.loadAs(reader, Map.class);
		map.put("netty_server_port", nettyServerPort);
		yml.dump(map,new FileWriter(new File(ymlPath)));
		return success("resetNettyServerPort");
	}
	
	@RequestMapping("/setSystemName") 
	public ResponseEntity<Object>  setSystemName(String systemName) throws Exception {
		Yaml yml = new Yaml();
		//String ymlPath =  VarController.class.getResource("/").getPath().substring(1)  + "application.yml" ;
		String ymlPath =  Thread.currentThread().getContextClassLoader().getResource("").getPath().toString()+ "application.yml" ;
		InputStreamReader reader = new InputStreamReader(new FileInputStream(ymlPath),"UTF-8");
		Map map = yml.loadAs(reader, Map.class);
		map.put("systemName", systemName);
		yml.dump(map,new FileWriter(new File(ymlPath)));
		return success("systemName");
	}
							
	@RequestMapping("/list") 
	public ResponseEntity<Object>  list() throws FileNotFoundException, UnsupportedEncodingException {
		Yaml yml = new Yaml();
		//String ymlPath =  VarController.class.getResource("/").getPath().substring(1)  + "application.yml" ;
		String ymlPath =  Thread.currentThread().getContextClassLoader().getResource("").getPath().toString()+ "application.yml" ;
		InputStreamReader reader = new InputStreamReader(new FileInputStream(ymlPath),"UTF-8");
		Map map = yml.loadAs(reader, Map.class);
		return success(map);
	
	}
	
	@RequestMapping("/save") 
	public ResponseEntity<Object>  save(Map<String, Object> currMap) throws IOException {
		Yaml yml = new Yaml();
		String ymlPath =  VarController.class.getResource("/").getPath().substring(1)  + "application.yml" ;
		InputStreamReader reader = new InputStreamReader(new FileInputStream(ymlPath),"UTF-8");
		Map<String, String> map = yml.loadAs(reader, Map.class);
		
		Iterator iter = currMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next(); 
			String key = (String) entry.getKey();
			Object val = entry.getValue();
			//String value = new String("UTF-8");
			map.put(key, new String(val.toString().getBytes("UTF-8")));
		}
		yml.dump(map,new FileWriter(new File(ymlPath)));
		return success(map);
	
	}
	
}
