package com.example.restaurant_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restaurant_management_system.entity.Points;

@Repository
public interface PointsDao extends JpaRepository<Points, Integer> {

	public Points findByPointName(String pointName);

	public Points findByPointsCost(int pointCost);
}
