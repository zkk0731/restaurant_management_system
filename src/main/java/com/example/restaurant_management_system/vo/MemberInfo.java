package com.example.restaurant_management_system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberInfo {

	private Integer memberId;

	private String memberAccount;

	private String memberName;

	private String phone;

	private Integer ageRange;

	private String lineId;

	private String email;

	private Integer points;

	public MemberInfo() {

	}

	public MemberInfo(Integer memberId, String memberAccount, String memberName, String phone, Integer ageRange,
			String lineId, String email, Integer points) {
		this.memberId = memberId;
		this.memberAccount = memberAccount;
		this.memberName = memberName;
		this.phone = phone;
		this.ageRange = ageRange;
		this.lineId = lineId;
		this.email = email;
		this.points = points;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getMemberAccount() {
		return memberAccount;
	}

	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getAgeRange() {
		return ageRange;
	}

	public void setAgeRange(Integer ageRange) {
		this.ageRange = ageRange;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

}
