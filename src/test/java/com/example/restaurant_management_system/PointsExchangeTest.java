package com.example.restaurant_management_system;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.restaurant_management_system.entity.Points;
import com.example.restaurant_management_system.service.ifs.SellerService;

@SpringBootTest
public class PointsExchangeTest {

	@Autowired
	private SellerService sellerService;

	@SuppressWarnings("unused")
	@Test
	public void readTest() {
		List<Points> pointsList = new ArrayList<>();
		pointsList = sellerService.readPointsExchange();
		System.out.println(pointsList);
	}

}
