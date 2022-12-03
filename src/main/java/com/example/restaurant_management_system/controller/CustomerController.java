package com.example.restaurant_management_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
		if(CollectionUtils.isEmpty(req.getOrderInfoMap())) {
			res.setMessage(RtnCode.PARAMETER_REQUIRED.getMessage());
			return res;
		}
		
		return customerService.customerOrder(req);
		
	}
}
