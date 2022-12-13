package com.example.restaurant_management_system.vo;

import com.example.restaurant_management_system.entity.Orders;

public class CheckOrderRes {

	private Orders orders;

	private String message;

	public CheckOrderRes() {

	}

	public CheckOrderRes(String message) {
		this.message = message;
	}

	public CheckOrderRes(Orders orders, String message) {
		this.orders = orders;
		this.message = message;
	}

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
