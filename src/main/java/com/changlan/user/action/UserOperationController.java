package com.changlan.user.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblUserOperationEntity;
import com.changlan.user.pojo.UserOperationDetail;
import com.changlan.user.pojo.UserOperationQuery;
import com.changlan.user.service.IUserOpertaionService;
import com.changlan.user.vo.UserOperationVO;

import io.netty.handler.codec.http2.Http2FrameLogger.Direction;

@RestController
public class UserOperationController extends BaseController{
	
	@Autowired
	private IUserOpertaionService userOperationService;
	
	@RequestMapping("/admin/user/operation/list")
	public ResponseEntity<Object>  loginError(UserOperationQuery query) throws Exception {
		TblAdminUserEntity userIsLogin = userIsLogin(); 
		if(!isSuperAdminUser(userIsLogin.getAdminUserId())) {
			query.setUserId(userIsLogin.getAdminUserId()); 
		}
		Pageable page = getPageAndOrderBy("RECORD_TIME");
		Page<UserOperationDetail> result =  userOperationService.findByPage(query,page);
		//封装数据
		List<UserOperationVO> list = new ArrayList<UserOperationVO>();
		for(UserOperationDetail object : result) {
			UserOperationVO vo = new UserOperationVO(object);
			list.add(vo);
		}
		return success(new PageImpl(list, page, result.getTotalElements()) );
	}
	
	
}
