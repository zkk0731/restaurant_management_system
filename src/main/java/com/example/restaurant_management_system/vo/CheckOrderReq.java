package com.example.restaurant_management_system.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckOrderReq {
	@JsonProperty("order_id")
	private int orderId;

	public CheckOrderReq() {

	}

	public CheckOrderReq(int orderId) {
		this.orderId = orderId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

}
