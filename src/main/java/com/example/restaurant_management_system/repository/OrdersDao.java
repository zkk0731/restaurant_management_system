package com.example.restaurant_management_system.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restaurant_management_system.entity.Orders;

@Repository
public interface OrdersDao extends JpaRepository<Orders, Integer> {

	public List<Orders> doQueryOrdersByDate(LocalDateTime startDate, LocalDateTime endDate);
	
}
