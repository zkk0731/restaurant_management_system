package com.example.restaurant_management_system.vo;

import java.util.List;
import java.util.Map;

import com.example.restaurant_management_system.entity.Menu;

public class CustomerRes {

	private Integer points;

	private Integer totalPrice;

	private String message;

	private Map<String, Integer> orderInfoMap;

	private List<Menu> menuList;

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Integer> getOrderInfoMap() {
		return orderInfoMap;
	}

	public void setOrderInfoMap(Map<String, Integer> orderInfoMap) {
		this.orderInfoMap = orderInfoMap;
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

}
