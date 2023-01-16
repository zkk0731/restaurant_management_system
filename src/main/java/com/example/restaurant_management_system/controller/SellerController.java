package com.example.restaurant_management_system.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

//@CrossOrigin
@RestController
public class SellerController {

	@Autowired
	private SellerService sellerService;

	// 判斷店家是否登入
	private SellerRes checkSellerLogin(HttpSession session) {
		if (session.getAttribute("sellerAccount") == null) {
			return new SellerRes(RtnCode.NOT_LOGIN.getMessage());
		}
		return null;
	}

	// 搜尋指定時間內的銷售資訊
	@PostMapping(value = "/search_sales_volume")
	public SellerRes searchSalesVolume(@RequestBody SellerReq req, HttpSession session) {
		SellerRes checkLogin = checkSellerLogin(session);

		// 判斷店家是否登入
		if (checkLogin != null) {
			return checkLogin;
		}

		SellerRes res = new SellerRes();

		// 判斷輸入資料是否完整
		if (req.getStartDateTime() == null || req.getEndDateTime() == null) {
			res.setMessage(RtnCode.PARAMETER_REQUIRED.getMessage());
			return res;
		}

		// 結束時間不得比開始時間早
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

		// 判斷店家是否登入
		if (checkLogin != null) {
			return checkLogin;
		}

		return sellerService.createPointsExchange(req);
	}

	// 讀取點數兌換
	@PostMapping(value = "/readPointsExchange")
	public List<Points> readPointsExchange() {
		return sellerService.readPointsExchange();
	}

	// 未確認訂單查詢(顯示所有訂單 到前端再分類)
	@PostMapping(value = "/search_unchecked_order")
	public ProcessOrderRes searchUncheckedOrder(@RequestBody ProcessOrderReq req, HttpSession session) {

		if (session.getAttribute("sellerAccount") == null) {
			return new ProcessOrderRes(RtnCode.NOT_LOGIN.getMessage());
		}

		return sellerService.searchUncheckedOrder(req);
	}

	// 推播功能
	@PostMapping(value = "/send_email")
	public SellerRes sendEmail(@RequestBody SellerReq req, HttpSession session) {
		SellerRes checkLogin = checkSellerLogin(session);

		// 判斷登入
		if (checkLogin != null) {
			return checkLogin;
		}

		// 判斷所需資料
		if (!StringUtils.hasText(req.getEmailTitle()) || !StringUtils.hasText(req.getEmailMessage())) {
			return new SellerRes(RtnCode.PARAMETER_REQUIRED.getMessage());
		}

		return sellerService.sendMessage(req);
	}

	// 確認店家登入狀態
	@PostMapping(value = "/check_seller_login")
	public SellerRes checkSellerLoginApi(HttpSession session) {
		SellerRes checkLogin = checkSellerLogin(session);

		// 判斷店家是否登入
		if (checkLogin != null) {
			return checkLogin;
		}

		return new SellerRes("管理員: " + session.getAttribute("sellerAccount").toString());
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

	// 顯示餐點品項(輸入餐點分類)
	@PostMapping(value = "/read_commodtity")
	public ReadCommodtityRes readCommodtity(@RequestBody SellerReq req) {
		return sellerService.readCommodtity(req);
	}
	
	// 顯示所有餐點品項
	@PostMapping(value = "/read_all_commodtity")
	public ReadCommodtityRes readAllCommodtity() {
		return sellerService.readAllCommodtity();
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
	
	@PostMapping(value="/upload", produces = MediaType.TEXT_PLAIN_VALUE)
	  public String upload(@RequestParam("file") MultipartFile file) throws IOException {
	    if (!file.getOriginalFilename().isEmpty()) {
	      BufferedOutputStream outputStream = new BufferedOutputStream(
	            new FileOutputStream(
	                  new File("", file.getOriginalFilename()))); // 上傳檔案位置為D:\
	      outputStream.write(file.getBytes());
	      outputStream.flush();
	      outputStream.close();
	    }else{
	      return "fail";
	    }
	    
	    return "success";
	  }
}
