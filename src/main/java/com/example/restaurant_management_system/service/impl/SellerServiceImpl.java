package com.example.restaurant_management_system.service.impl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.restaurant_management_system.entity.Orders;
import com.example.restaurant_management_system.repository.OrdersDao;
import com.example.restaurant_management_system.service.ifs.SellerService;
import com.example.restaurant_management_system.vo.SellerRes;

public class SellerServiceImpl implements SellerService{

	@Autowired
	private OrdersDao ordersDao;
	
	@Override
	public SellerRes searchSalesVolume(LocalDateTime startDate, LocalDateTime endDate) {
		List<Orders> resultList = ordersDao.doQueryOrdersByDate(startDate, endDate);
		
		int totalPrice = 0;
		for(Orders item : resultList) {
			totalPrice += item.getTotalPrice();
		}
		
		SellerRes res = new SellerRes();
		
		res.setTotalPrice(totalPrice);
		return null;
	}

}
