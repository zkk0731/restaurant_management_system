package com.example.restaurant_management_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.restaurant_management_system.constants.RtnCode;
import com.example.restaurant_management_system.entity.Menu;
import com.example.restaurant_management_system.service.ifs.CustomerService;
import com.example.restaurant_management_system.vo.CustomerReq;
import com.example.restaurant_management_system.vo.CustomerRes;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	// API-5.查詢餐點排行榜
	@PostMapping(value = "/searchTop5Commodity")
	public List<Menu> searchTop5Commodity() {
		return customerService.searchTop5Commodity();
	}

	// 點餐
	@PostMapping(value = "/customerOrder")
	public CustomerRes customerOrder(@RequestBody CustomerReq req) {
		CustomerRes res = new CustomerRes();
		if (CollectionUtils.isEmpty(req.getOrderInfoMap())) {
			res.setMessage(RtnCode.PARAMETER_REQUIRED.getMessage());
			return res;
		}

		return customerService.customerOrder(req);
	}

	// API-6.餐點分類查詢
	@PostMapping(value = "/searchCategory")
	public CustomerRes searchCategory(@RequestBody CustomerReq req) {
		if (!StringUtils.hasText(req.getCategory())) {
			return new CustomerRes(RtnCode.CATEGORY_ISNOT_EXIST.getMessage());
		}
		return customerService.searchCategory(req.getCategory());
	}

	// 創建會員
	@PostMapping(value = "/createMember")
	public CustomerRes createMember(@RequestBody CustomerReq req) {
		//判斷必填資料是否存在
		if(!StringUtils.hasText(req.getMemberAccount()) ||
				!StringUtils.hasText(req.getMemberPwd()) ||
				!StringUtils.hasText(req.getMemberName()) ||
				!StringUtils.hasText(req.getMemberPhone())) {
			return new CustomerRes(RtnCode.PARAMETER_REQUIRED.getMessage());
		}
		
		CustomerRes check = phoneAndEmailPatternCheck(req);
		if(check != null) {
			return check;
		}
		
		return customerService.createMember(req);
	}
	
	//判斷手機號碼與email的格式是否正確
	private CustomerRes phoneAndEmailPatternCheck(CustomerReq req) {
		String phonePattern = "09\\d{8}";
		String emailPattern = "[A-za-z0-9]+@[A-za-z0-9]+\\.com";
		
		//手機號碼格式判斷
		if(!req.getMemberPhone().matches(phonePattern)) {
			return new CustomerRes(RtnCode.PARAMETER_ERROR.getMessage());
		}
		
		//email 格式判斷
		if(StringUtils.hasText(req.getMemberEmail()) && 
				!req.getMemberEmail().matches(emailPattern)) {
			return new CustomerRes(RtnCode.PARAMETER_ERROR.getMessage());
		}
		
		return null;
	}

}
