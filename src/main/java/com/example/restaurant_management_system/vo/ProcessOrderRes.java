package com.example.restaurant_management_system.vo;

import java.util.List;

import com.example.restaurant_management_system.entity.Orders;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessOrderRes {

	@JsonProperty("order_info")
	private List<Orders> orderInfo;

	private String message;

	public ProcessOrderRes() {

	}

	public ProcessOrderRes(String message) {
		this.message = message;
	}

	public ProcessOrderRes(List<Orders> orderInfo, String message) {
		this.orderInfo = orderInfo;
		this.message = message;
	}

	public List<Orders> getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(List<Orders> orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
