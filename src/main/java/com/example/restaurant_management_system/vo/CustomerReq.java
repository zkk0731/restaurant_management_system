package com.example.restaurant_management_system.vo;

import java.util.Map;

public class CustomerReq {

	private String memberAccount;

	private String pwd;

	private String category;

	private int costPoints;

	private Map<String, Integer> orderInfoMap;

	public CustomerReq() {

	}

	public String getMemberAccount() {
		return memberAccount;
	}

	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getCostPoints() {
		return costPoints;
	}

	public void setCostPoints(int costPoints) {
		this.costPoints = costPoints;
	}

	public Map<String, Integer> getOrderInfoMap() {
		return orderInfoMap;
	}

	public void setOrderInfoMap(Map<String, Integer> orderInfoMap) {
		this.orderInfoMap = orderInfoMap;
	}

}
