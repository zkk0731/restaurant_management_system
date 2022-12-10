package com.example.restaurant_management_system.constants;

public enum RtnCode {
	SUCCESS("200","Success"),
	ACCOUNT_EXIST("403", "帳號已存在"),
	PARAMETER_ERROR("403", "資料格式錯誤"),
	PARAMETER_REQUIRED("403", "所需資料缺失"),
	CATEGORY_ISNOT_EXIST("403", "分類不存在"),
	POINTNAME_EXIST("403", "名稱已存在"),
	DISCOUNT_ERROR("403", "折扣輸入錯誤"),
	ORDER_NOT_EXIST("403", "訂單不存在"),
	ORDER_HAS_CANCELED("403", "訂單已是取消狀態"),
	COMMODITY_EXIST("403", "餐點已存在");

	
	private String code;
	
	private String message;

	private RtnCode(String code, String message) {
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
