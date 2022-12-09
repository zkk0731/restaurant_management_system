package com.example.restaurant_management_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.restaurant_management_system.constants.RtnCode;
import com.example.restaurant_management_system.service.ifs.SellerService;
import com.example.restaurant_management_system.vo.ProcessOrderReq;
import com.example.restaurant_management_system.vo.ProcessOrderRes;
import com.example.restaurant_management_system.vo.SellerReq;
import com.example.restaurant_management_system.vo.SellerRes;

@CrossOrigin
@RestController
public class SellerController {
	
	@Autowired
	private SellerService sellerService;
	
	@PostMapping(value = "/api/search_sales_volume")
	public SellerRes searchSalesVolume(@RequestBody SellerReq req) {
		SellerRes res = new SellerRes();
		if(req.getStartDateTime() == null || req.getEndDateTime() == null) {
			res.setMessage(RtnCode.PARAMETER_REQUIRED.getMessage());
			return res;
		}
		
		if(req.getEndDateTime().isBefore(req.getStartDateTime())) {
			res.setMessage(RtnCode.PARAMETER_ERROR.getMessage());
			return res;
		}
		
		return sellerService.searchSalesVolume(req.getStartDateTime(), req.getEndDateTime());
	}
	
	
	@PostMapping(value = "/searchUncheckedOrder")
	public ProcessOrderRes searchUncheckedOrder(@RequestBody ProcessOrderReq req) {
		return sellerService.searchUncheckedOrder(req);
	}
	
}
