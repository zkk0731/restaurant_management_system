package com.example.restaurant_management_system.vo;

import java.util.Map;

public class ShoppingCart {

	private Map<String, Integer>orderInfoMap;
	
	private int totalPrice;
	
	public ShoppingCart() {
		
	}

	public ShoppingCart(Map<String, Integer>orderInfoMap, int totalPrice) {
		this.orderInfoMap = orderInfoMap;
		this.totalPrice = totalPrice;
	}
	
	public Map<String, Integer> getOrderInfoMap() {
		return orderInfoMap;
	}

	public void setOrderInfoMap(Map<String, Integer> orderInfoMap) {
		this.orderInfoMap = orderInfoMap;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
}
