package com.example.restaurant_management_system.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.restaurant_management_system.constants.RtnCode;
import com.example.restaurant_management_system.entity.Members;
import com.example.restaurant_management_system.entity.Menu;
import com.example.restaurant_management_system.service.ifs.CustomerService;
import com.example.restaurant_management_system.vo.CustomerReq;
import com.example.restaurant_management_system.vo.CustomerRes;
import com.example.restaurant_management_system.vo.ReadCommodtityRes;
import com.example.restaurant_management_system.vo.ShoppingCart;

//@CrossOrigin
@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	// 查詢餐點排行榜
	@PostMapping(value = "/searchTop5Commodity")
	public List<Menu> searchTop5Commodity() {
		return customerService.searchTop5Commodity();
	}
	
	// 查詢餐點排行榜(Yu)
	@PostMapping(value = "/searchTop5Commodity2")
	public ReadCommodtityRes searchTop5Commodity2() {
		return customerService.searchTop5Commodity2();
	}

	// 點餐
	@PostMapping(value = "/customerOrder")
	public CustomerRes customerOrder(@RequestBody CustomerReq req, HttpSession session) {
		CustomerRes res = new CustomerRes();
		//將購物車的訂單取出來結帳
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		if (shoppingCart == null) {
			res.setMessage(RtnCode.SHOPPING_CART_IS_EMPTY.getMessage());
			return res;
		}
		
		return customerService.customerOrder(shoppingCart.getOrderInfoMap(),
				//判斷是否為會員
				session.getAttribute("account") == null ? null : session.getAttribute("account").toString(),
				shoppingCart.getTotalPrice(),
				req.getCostPoints());
	}

	// 餐點分類查詢
	@PostMapping(value = "/searchCategory")
	public CustomerRes searchCategory(@RequestBody CustomerReq req) {
		if (!StringUtils.hasText(req.getCategory())) {
			return new CustomerRes(RtnCode.CATEGORY_ISNOT_EXIST.getMessage());
		}

		CustomerRes res = customerService.searchCategory(req.getCategory());
		res.setMessage(RtnCode.SUCCESS.getMessage());
		return res;
	}

	// 創建會員
	@PostMapping(value = "/createMember")
	public CustomerRes createMember(@RequestBody CustomerReq req) {
		// 判斷必填資料是否存在
		if (!StringUtils.hasText(req.getMemberAccount()) || !StringUtils.hasText(req.getMemberPwd())
				|| !StringUtils.hasText(req.getMemberName()) || !StringUtils.hasText(req.getMemberPhone())) {
			return new CustomerRes(RtnCode.PARAMETER_REQUIRED.getMessage());
		}

		CustomerRes check = phoneAndEmailPatternCheck(req);
		if (check != null) {
			return check;
		}

		return customerService.createMember(req);
	}

	// 判斷手機號碼與email的格式是否正確
	private CustomerRes phoneAndEmailPatternCheck(CustomerReq req) {
		String phonePattern = "09\\d{8}";
		String emailPattern = "[A-za-z0-9]+@[A-za-z0-9]+\\.com";

		// 手機號碼格式判斷
		if (!req.getMemberPhone().matches(phonePattern)) {
			return new CustomerRes(RtnCode.PARAMETER_ERROR.getMessage());
		}

		// email 格式判斷
		if (StringUtils.hasText(req.getMemberEmail()) && !req.getMemberEmail().matches(emailPattern)) {
			return new CustomerRes(RtnCode.PARAMETER_ERROR.getMessage());
		}

		return null;
	}
	
	//登入
	@PostMapping(value = "/login")
	public CustomerRes login(@RequestBody CustomerReq req, HttpSession session) {
		CustomerRes res = new CustomerRes();
		
		//如果已經登入 需先登出
		if(session.getAttribute("account") != null) {
			res.setMessage(RtnCode.ALREADY_LOGIN.getMessage());
			return res;
		}
				
		//判斷所需資料
		if(!StringUtils.hasText(req.getMemberAccount()) || !StringUtils.hasText(req.getMemberPwd())) {
			res.setMessage(RtnCode.PARAMETER_REQUIRED.getMessage());
			return res;
		}
		
		Members result = customerService.login(req);
		
		//帳號或密碼錯誤
		if(result == null) {
			res.setMessage(RtnCode.ACCOUNT_OR_PWD_ERROR.getMessage());
			return res;
		}
		
		//登入成功將帳號存入暫存
		session.setAttribute("account", req.getMemberAccount());
		
		//判斷登入權限是否為店家
		if(result.isAuthority()) {
			session.setAttribute("sellerAccount", req.getMemberAccount());
			res.setMessage(RtnCode.LOGIN_SELLER_SUCCESSFUL.getMessage());
			return res;
		}
		//登入成功將帳號存入暫存
		session.setAttribute("account", req.getMemberAccount());
		res.setMessage(RtnCode.LOGIN_MEMBER_SUCCESSFUL.getMessage());
		return res;
	}

	//顯示登入帳號
	@PostMapping(value = "/show_login_account")
	public CustomerRes showLoginAccount(HttpSession session) {
		CustomerRes res = new CustomerRes();
		
		//判斷是否為登入狀態
		if(session.getAttribute("account") == null) {
			res.setMessage(RtnCode.NOT_LOGIN.getMessage());
			return res;
		}
		res.setMessage("歡迎" + session.getAttribute("account").toString());
		return res;
	}
	
	//登出
	@PostMapping(value = "/logout")
	public CustomerRes logout(HttpSession session) {
		CustomerRes res = new CustomerRes();
		
		//未登入則無法登出
//		if(session.getAttribute("account") == null) {
//			res.setMessage(RtnCode.NOT_LOGIN.getMessage());
//			return res;
//		}
		session.removeAttribute("sellerAccount");
		session.removeAttribute("account");
		res.setMessage(RtnCode.SUCCESS.getMessage());
		return res;
	}
	
	//購物車
	@PostMapping(value = "/shopping_cart")
	public CustomerRes shoppingCart(@RequestBody CustomerReq req, HttpSession session) {
		CustomerRes res = new CustomerRes();
		
		//判斷是否有品項加入購物車
		if(CollectionUtils.isEmpty(req.getOrderInfoMap())) {
			res.setMessage(RtnCode.SHOPPING_CART_IS_EMPTY.getMessage());
			return res;
		}
		
		int totalPrice = customerService.calculateTotalPrice(req.getOrderInfoMap());
		
		//計算購物車內品項總價
		ShoppingCart shoppingCart = new ShoppingCart(req.getOrderInfoMap(), totalPrice);
		
		res.setOrderInfoMap(req.getOrderInfoMap());
		res.setTotalPrice(totalPrice);
		
		//將購物車資訊放入暫存
		session.setAttribute("shoppingCart", shoppingCart);
		
		return res;
	}
	
	//清除購物車
	@PostMapping(value = "/remove_shopping_cart")
	public CustomerRes removeShoppingCart(HttpSession session) {
		CustomerRes res = new CustomerRes();
		if(session.getAttribute("shoppingCart") != null) {
			session.removeAttribute("shoppingCart");
		}
			
		res.setMessage(RtnCode.SUCCESS.getMessage());
		return res;
	}
	
	//查詢會員資訊及訂單
	@PostMapping(value = "/search_member_info")
	public CustomerRes searchMemberInfo(HttpSession session) {
		CustomerRes res = new CustomerRes();
		
		//判斷是否登入
		if(session.getAttribute("account") == null) {
			res.setMessage(RtnCode.NOT_LOGIN.getMessage());
			return res;
		}
		
		return customerService.searchMemberInfo(session.getAttribute("account").toString());
	}
}
