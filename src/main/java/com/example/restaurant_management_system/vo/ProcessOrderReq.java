package com.example.restaurant_management_system.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProcessOrderReq {
	
	@JsonProperty("member_account")
	private String memberAccount;
	
	@JsonProperty("order_id")
	private int orderId;

	public ProcessOrderReq() {

	}

	public ProcessOrderReq(String memberAccount) {
		this.memberAccount = memberAccount;
	}

	public String getMemberAccount() {
		return memberAccount;
	}

	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	
}
