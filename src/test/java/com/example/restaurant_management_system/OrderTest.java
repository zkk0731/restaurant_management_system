package com.example.restaurant_management_system;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.restaurant_management_system.service.ifs.CustomerService;
import com.example.restaurant_management_system.vo.CustomerReq;
import com.example.restaurant_management_system.vo.CustomerRes;

@SpringBootTest
public class OrderTest {

	@Autowired CustomerService customerService;
	
	@Test
	public void mapToStringTest() {
		Map<String, Integer> orderInfoMap = new HashMap<>();
		orderInfoMap.put("beef", 1);
		orderInfoMap.put("chicken", 2);
		
		String orderInfoString = orderInfoMap.toString().substring(1, orderInfoMap.toString().length() - 1);
		System.out.println(orderInfoString);
	}
	
	@Test
	public void orderAndPointsExchangeTest() {
		CustomerReq req = new CustomerReq();
		Map<String, Integer> orderInfoMap = new HashMap<>();
		orderInfoMap.put("beef", 3);
		orderInfoMap.put("chicken", 2);
		req.setOrderInfoMap(orderInfoMap);
//		req.setMemberAccount("AA");
//		req.setCostPoints(1);
		
		CustomerRes res = customerService.customerOrder(req);
		System.out.println(res.getMessage());
		System.out.println(res.getPointsExchangeMessage());

	}
	
	@Test
	public void patternTest() {
		String phonePattern = "09\\d{8}";
		String emailPattern = "[A-za-z0-9]+@[A-za-z0-9]+\\.com";
		
		String phone = "0912345678";
		String email = "abcd@gmail.com";
		
		System.out.println(phone.matches(phonePattern));
		System.out.println(email.matches(emailPattern));
		
	}
	
}
