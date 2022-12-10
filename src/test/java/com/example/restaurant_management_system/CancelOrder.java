package com.example.restaurant_management_system;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.restaurant_management_system.entity.Orders;
import com.example.restaurant_management_system.repository.OrdersDao;
import com.example.restaurant_management_system.service.ifs.SellerService;
import com.example.restaurant_management_system.vo.ProcessOrderReq;
import com.example.restaurant_management_system.vo.ProcessOrderRes;

@SpringBootTest
public class CancelOrder {
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private OrdersDao ordersDao;

	
	@Test
	public void cancelOrderTest() {
		int orderId = 1;
		
		ProcessOrderReq req = new ProcessOrderReq(orderId);
		ProcessOrderRes res = sellerService.cancelOrder(req);
		System.out.println("--> message: " + res.getMessage());
		System.out.println("--> getOrderInfo: " + res.getOrderInfo().get(0).getOrderInfo());
		System.out.println("--> getOrderState: " + res.getOrderInfo().get(0).getOrderState());

	}
}
