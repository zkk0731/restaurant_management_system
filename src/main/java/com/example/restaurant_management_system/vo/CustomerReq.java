package com.example.restaurant_management_system.vo;

import java.util.Map;

public class CustomerReq {

	private String memberAccount;

	private String memberPwd;
	
	private String memberName;
	
	private String memberPhone;
	
	private int memberAgeRange;
	
	private String memberLineId;
	
	private String memberEmail;

	private String category;

	private int costPoints;

	private Map<String, Integer> orderInfoMap;
	
	

	public CustomerReq() {

	}

	public String getMemberPwd() {
		return memberPwd;
	}

	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getMemberLineId() {
		return memberLineId;
	}

	public void setMemberLineId(String memberLineId) {
		this.memberLineId = memberLineId;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public int getMemberAgeRange() {
		return memberAgeRange;
	}



	public void setMemberAgeRange(int memberAgeRange) {
		this.memberAgeRange = memberAgeRange;
	}



	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberAccount() {
		return memberAccount;
	}

	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
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
