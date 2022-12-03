package com.example.restaurant_management_system.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private int orderId;

	@Column(name = "order_info")
	private String orderInfo;

	@Column(name = "total_price")
	private int totalPrice;

	@Column(name = "member_id")
	private String memberId;

	@Column(name = "order_datetime")
	private LocalDateTime orderDatetime;

	@Column(name = "order_state")
	private String orderState;

	@Column(name = "points_get")
	private int pointsGet;
	
	@Column(name = "points_cost")
	private int pointsCost;

	public Orders() {
		
	}
	
	public Orders(String orderInfo, LocalDateTime orderDatetime, String orderState) {
		this.orderInfo = orderInfo;
		this.orderDatetime = orderDatetime;
		this.orderState = orderState;
	}
	
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

	

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
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

	public int getPointsGet() {
		return pointsGet;
	}

	public void setPointsGet(int pointsGet) {
		this.pointsGet = pointsGet;
	}

	public int getPointsCost() {
		return pointsCost;
	}

	public void setPointsCost(int pointsCost) {
		this.pointsCost = pointsCost;
	}

	

}
