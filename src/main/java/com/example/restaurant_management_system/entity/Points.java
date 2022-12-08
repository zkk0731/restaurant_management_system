package com.example.restaurant_management_system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "points_exchange")
public class Points {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "point_id")
	private int pointId;

	@Column(name = "point_name")
	private String pointName;

	@Column(name = "discount")
	private int discount;

	@Column(name = "points_cost")
	private int pointsCost;

	public Points() {

	}

	public Points(String pointName, int discount, int pointsCost) {
		this.pointName = pointName;
		this.discount = discount;
		this.pointsCost = pointsCost;
	}

	public int getPointId() {
		return pointId;
	}

	public void setPointId(int pointId) {
		this.pointId = pointId;
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

}
