package com.example.restaurant_management_system.vo;

import java.util.List;

import com.example.restaurant_management_system.entity.Menu;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReadCommodtityRes {

	private List<Menu> menus;

	private String message;

	public ReadCommodtityRes() {

	}

	public ReadCommodtityRes(String message) {
		this.message = message;
	}

	public ReadCommodtityRes(List<Menu> menus, String message) {
		this.menus = menus;
		this.message = message;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
