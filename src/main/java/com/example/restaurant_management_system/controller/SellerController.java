package com.example.restaurant_management_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.restaurant_management_system.constants.RtnCode;
import com.example.restaurant_management_system.entity.Points;
import com.example.restaurant_management_system.service.ifs.SellerService;
import com.example.restaurant_management_system.vo.CheckOrderReq;
import com.example.restaurant_management_system.vo.CheckOrderRes;
import com.example.restaurant_management_system.vo.ProcessOrderReq;
import com.example.restaurant_management_system.vo.ProcessOrderRes;
import com.example.restaurant_management_system.vo.ReadCommodtityRes;
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
	public ProcessOrderRes searchUncheckedOrder(@RequestBody ProcessOrderReq req) {
		return sellerService.searchUncheckedOrder(req);
	}

	// 取消訂單
	@PostMapping(value = "/cancel_order")
	public ProcessOrderRes cancelOrder(@RequestBody ProcessOrderReq req) {
		return sellerService.cancelOrder(req);
	}

	// 建立餐點品項
	@PostMapping(value = "/create_commodity")
	public SellerRes createCommodity(@RequestBody SellerReq req) {
		return sellerService.createCommodity(req);
	}

	// 顯示餐點品項
	@PostMapping(value = "/read_commodtity")
	public ReadCommodtityRes readCommodtity(@RequestBody SellerReq req) {
		return sellerService.readCommodtity(req);
	}

	// 更新點數兌換
	@PostMapping(value = "/update_points_exchange")
	public SellerRes updatePointsExchange(@RequestBody SellerReq req) {
		return sellerService.updatePointsExchange(req);
	}

	// 刪除點數兌換
	@PostMapping(value = "/delete_points_exchange")
	public SellerRes deletePointsExchange(@RequestBody SellerReq req) {
		return sellerService.deletePointsExchange(req);
	}

	// 確認訂單
	@PostMapping(value = "/check_order")
	public CheckOrderRes checkOrder(@RequestBody CheckOrderReq req) {
		return sellerService.checkOrder(req);
	}
}
