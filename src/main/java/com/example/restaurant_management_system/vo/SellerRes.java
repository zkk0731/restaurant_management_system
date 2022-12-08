package com.example.restaurant_management_system.vo;

import java.util.Map;

import com.example.restaurant_management_system.entity.Points;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SellerRes {

	private String commodityName;

	private Integer price;

	private String category;

	private Map<String, Integer> orderInfoMap;

	private Integer totalPrice;

	private Integer salesVolume;

	private String message;

	private Points points;

	public SellerRes() {

	}

	public SellerRes(String message) {
		this.message = message;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(Integer salesVolume) {
		this.salesVolume = salesVolume;
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

	public Points getPoints() {
		return points;
	}

	public void setPoints(Points points) {
		this.points = points;
	}

}
