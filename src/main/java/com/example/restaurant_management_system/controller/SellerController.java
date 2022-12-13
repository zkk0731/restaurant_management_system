package com.example.restaurant_management_system.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.restaurant_management_system.constants.RtnCode;
import com.example.restaurant_management_system.entity.Points;
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

	//判斷店家是否登入
	private SellerRes checkSellerLogin(HttpSession session) {
		if(session.getAttribute("sellerAccount") == null) {
			return new SellerRes(RtnCode.NOT_LOGIN.getMessage());
		}
		return null;
	}
	
	
	//搜尋指定時間內的銷售資訊
	@PostMapping(value = "/search_sales_volume")
	public SellerRes searchSalesVolume(@RequestBody SellerReq req, HttpSession session) {
		SellerRes checkLogin = checkSellerLogin(session);
		
		//判斷店家是否登入
		if(checkLogin != null) {
			return checkLogin;
		}
		
		SellerRes res = new SellerRes();
		
		//判斷輸入資料是否完整
		if (req.getStartDateTime() == null || req.getEndDateTime() == null) {
			res.setMessage(RtnCode.PARAMETER_REQUIRED.getMessage());
			return res;
		}

		//結束時間不得比開始時間早
		if (req.getEndDateTime().isBefore(req.getStartDateTime())) {
			res.setMessage(RtnCode.PARAMETER_ERROR.getMessage());
			return res;
		}

		return sellerService.searchSalesVolume(req.getStartDateTime(), req.getEndDateTime());
	}

	// 創建點數兌換
	@PostMapping(value = "/createPointsExchange")
	public SellerRes createPointsExchange(@RequestBody SellerReq req, HttpSession session) {

		SellerRes checkLogin = checkSellerLogin(session);
		
		//判斷店家是否登入
		if(checkLogin != null) {
			return checkLogin;
		}
		
		// 點數兌換名稱是否存在
		if (!StringUtils.hasText(req.getPointName())) {
			return new SellerRes(RtnCode.PARAMETER_REQUIRED.getMessage());
		}

		// 折扣範圍1折~99折
		if (req.getDiscount() < 1 || req.getDiscount() > 99) {
			return new SellerRes(RtnCode.DISCOUNT_ERROR.getMessage());
		}

		return sellerService.createPointsExchange(req);
	}

	// 讀取點數兌換
	@PostMapping(value = "/readPointsExchange")
	public List<Points> readPointsExchange() {
		return sellerService.readPointsExchange();
	}
	
	// 未確認訂單查詢
	@PostMapping(value = "/searchUncheckedOrder")
	public ProcessOrderRes searchUncheckedOrder(@RequestBody ProcessOrderReq req, HttpSession session) {
		
		if(session.getAttribute("sellerAccount") == null) {
			return new ProcessOrderRes(RtnCode.NOT_LOGIN.getMessage());
		}
		
		return sellerService.searchUncheckedOrder(req);
	}
	
	//推播功能
	@PostMapping(value = "/send_email")
	public SellerRes sendEmail(@RequestBody SellerReq req, HttpSession session) {
		SellerRes checkLogin = checkSellerLogin(session);
		
		//判斷登入
		if(checkLogin != null) {
			return checkLogin;
		}
		
		//判斷所需資料
		if(!StringUtils.hasText(req.getEmailTitle()) ||
				!StringUtils.hasText(req.getEmailMessage())) {
			return new SellerRes(RtnCode.PARAMETER_REQUIRED.getMessage());
		}
		
		return sellerService.sendMessage(req);	
	}

}
