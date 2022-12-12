package com.example.restaurant_management_system.constants;

public enum RtnCode {
	SUCCESS("200","Success"),
	ACCOUNT_EXIST("403", "帳號已存在"),
	PARAMETER_ERROR("403", "資料格式錯誤"),
	PARAMETER_REQUIRED("403", "所需資料缺失"),
	CATEGORY_ISNOT_EXIST("403", "分類不存在"),
	POINTNAME_EXIST("403", "名稱已存在"),
	DISCOUNT_ERROR("403", "折扣輸入錯誤"),
	ACCOUNT_OR_PWD_ERROR("403", "帳號或密碼錯誤"),
	LOGIN_MEMBER_SUCCESSFUL("200","登入會員成功"),
	LOGIN_SELLER_SUCCESSFUL("200","登入店家成功"),
	ALREADY_LOGIN("403","已有帳號登入，要登入其他帳號請先登出"),
	NOT_LOGIN("400","尚未登入"),
	SHOPPING_CART_IS_EMPTY("403", "購物車為空"),
	NO_ORDERS_RECORD("200", "尚無購買紀錄");
	
	
	private String code;
	
	private String message;

	private RtnCode(String code,String message) {
		this.code = code;
		this.message = message;
	}
	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
