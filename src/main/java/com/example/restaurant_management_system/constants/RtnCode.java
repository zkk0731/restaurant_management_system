package com.example.restaurant_management_system.constants;

public enum RtnCode {
	SUCCESS("200","Success"),
	ACCOUNT_EXIST("403", "帳號已存在"),
	PARAMETER_ERROR("403", "資料格式錯誤"),
	PARAMETER_REQUIRED("403", "所需資料缺失"),
	CATEGORY_ISNOT_EXIST("403", "分類不存在");
	
	
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
