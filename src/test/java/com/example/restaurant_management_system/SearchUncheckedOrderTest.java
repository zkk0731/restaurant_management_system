package com.example.restaurant_management_system;


import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.restaurant_management_system.service.ifs.SellerService;
import com.example.restaurant_management_system.vo.CustomerReq;
import com.example.restaurant_management_system.vo.ProcessOrderReq;
import com.example.restaurant_management_system.vo.ProcessOrderRes;

@SpringBootTest
public class SearchUncheckedOrderTest {

	@Autowired
	private SellerService sellerService;

	@Test
	public void searchUncheckedOrderTest() {
		String memberAccount = "S01";
//		String memberAccount = "M01";

		ProcessOrderReq req = new ProcessOrderReq(memberAccount);
		ProcessOrderRes res = sellerService.searchUncheckedOrder(req);
		System.out.println("--> res.getOrderInfo().size(): " + res.getOrderInfo().size());
	}

	@Test
	public void defaultOrders() {
		CustomerReq req = new CustomerReq();
		Map<String, Integer> orderInfoMap = new HashMap<>();
		orderInfoMap.put("beef", 3);
		orderInfoMap.put("chicken", 2);
		req.setOrderInfoMap(orderInfoMap);

		CustomerReq req2 = new CustomerReq();
		Map<String, Integer> orderInfoMap2 = new HashMap<>();
		orderInfoMap2.put("pork", 5);
		orderInfoMap2.put("chicken", 1);
		req2.setOrderInfoMap(orderInfoMap2);

		CustomerReq req3 = new CustomerReq();
		Map<String, Integer> orderInfoMap3 = new HashMap<>();
		orderInfoMap3.put("pork", 1);
		orderInfoMap3.put("chicken", 3);
		req3.setOrderInfoMap(orderInfoMap3);

		CustomerReq req4 = new CustomerReq();
		Map<String, Integer> orderInfoMap4 = new HashMap<>();
		orderInfoMap4.put("beef", 5);
		orderInfoMap4.put("chicken", 2);
		orderInfoMap4.put("pork", 1);
		req4.setOrderInfoMap(orderInfoMap4);

		CustomerReq req5 = new CustomerReq();
		Map<String, Integer> orderInfoMap5 = new HashMap<>();
		orderInfoMap5.put("beef", 1);
		orderInfoMap5.put("chicken", 1);
		req5.setOrderInfoMap(orderInfoMap5);

//		customerService.customerOrder(req);
//		customerService.customerOrder(req2);
//		customerService.customerOrder(req3);
//		customerService.customerOrder(req4);
//		customerService.customerOrder(req5);

	}
}
