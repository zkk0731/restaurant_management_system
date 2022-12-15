package com.example.restaurant_management_system.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SellerReq {

	private String commodityName;

	private int price;

	private String category;

	private String customerEmail;

	@JsonProperty("point_name")
	private String pointName;

	private int discount;

	@JsonProperty("points_cost")
	private int pointsCost;

	@JsonProperty("start_date_time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime startDateTime;

	@JsonProperty("end_date_time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime endDateTime;

	private String emailMessage;

	private String emailTitle;

	public SellerReq() {

	}

	public SellerReq(String commodityName, int price, String category) {
		this.commodityName = commodityName;
		this.price = price;
		this.category = category;
	}

	public SellerReq(String pointName, int discount, int pointsCost) {
		this.pointName = pointName;
		this.discount = discount;
		this.pointsCost = pointsCost;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getPointsCost() {
		return pointsCost;
	}

	public void setPointsCost(int pointsCost) {
		this.pointsCost = pointsCost;
	}

	public String getEmailMessage() {
		return emailMessage;
	}

	public void setEmailMessage(String emailMessage) {
		this.emailMessage = emailMessage;
	}

	public String getEmailTitle() {
		return emailTitle;
	}

	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
	}

}
