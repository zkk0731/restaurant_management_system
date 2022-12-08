package com.example.restaurant_management_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.restaurant_management_system.constants.RtnCode;
import com.example.restaurant_management_system.service.ifs.SellerService;
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
		if (req.getStartDateTime() == null || req.getEndDateTime() == null) {
			res.setMessage(RtnCode.PARAMETER_REQUIRED.getMessage());
			return res;
		}

		if (req.getEndDateTime().isBefore(req.getStartDateTime())) {
			res.setMessage(RtnCode.PARAMETER_ERROR.getMessage());
			return res;
		}

		return sellerService.searchSalesVolume(req.getStartDateTime(), req.getEndDateTime());
	}

	// 創建點數兌換
	@PostMapping(value = "/createPointsExchange")
	public SellerRes createPointsExchange(@RequestBody SellerReq req) {

		// 點數兌換名稱是否存在
		if (!StringUtils.hasText(req.getPointName())) {
			return new SellerRes(RtnCode.PARAMETER_REQUIRED.getMessage());
		}
		// 折扣不可小於0
		if (req.getDiscount() < 0) {
//		if (req.getDiscount() < 1 || req.getDiscount() > 99) {
			return new SellerRes(RtnCode.DISCOUNT_ERROR.getMessage());
		}

		return sellerService.createPointsExchange(req);
	}

}
