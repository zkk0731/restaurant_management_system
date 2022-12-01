package com.example.restaurant_management_system.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Orders {

	@Id
	@Column(name = "order_id")
	private int orderId;

	@Column(name = "order_info")
	private String orderInfo;

	@Column(name = "total_price")
	private int total_price;

	@Column(name = "member_id")
	private String memberId;

	@Column(name = "order_datetime")
	private LocalDateTime orderDatetime;

	@Column(name = "order_state")
	private String orderState;

	@Column(name = "points_state")
	private int pointsState;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}

	public int getTotal_price() {
		return total_price;
	}

	public void setTotal_price(int total_price) {
		this.total_price = total_price;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public LocalDateTime getOrderDatetime() {
		return orderDatetime;
	}

	public void setOrderDatetime(LocalDateTime orderDatetime) {
		this.orderDatetime = orderDatetime;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public int getPointsState() {
		return pointsState;
	}

	public void setPointsState(int pointsState) {
		this.pointsState = pointsState;
	}

}
