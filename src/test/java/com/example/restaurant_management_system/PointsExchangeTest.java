package com.example.restaurant_management_system;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.restaurant_management_system.entity.Points;
import com.example.restaurant_management_system.repository.PointsDao;
import com.example.restaurant_management_system.service.ifs.SellerService;
import com.example.restaurant_management_system.vo.SellerReq;
import com.example.restaurant_management_system.vo.SellerRes;

@SpringBootTest
public class PointsExchangeTest {

	@Autowired
	private SellerService sellerService;

	@Autowired
	private PointsDao pointsDao;

	@SuppressWarnings("unused")
	@Test
	public void readTest() {
		List<Points> pointsList = new ArrayList<>();
		pointsList = sellerService.readPointsExchange();
		System.out.println(pointsList);
	}

	@Test
	public void addDefaultPointData() {
		List<Points> pointsList = new ArrayList<>();
		pointsList.add(new Points("九折", 9, 900));
		pointsList.add(new Points("七折", 7, 700));
		pointsList.add(new Points("八八折", 88, 880));
		pointsList.add(new Points("六五折", 65, 650));

		pointsList.add(new Points("hinata", 0, 3000));
		pointsList.add(new Points("kageyama", 0, 2000));
		pointsList.add(new Points("nishinoya", 0, 5000));
		pointsList.add(new Points("tsukishima", 0, 1000));
		pointsList.add(new Points("tanaka", 0, 1500));

		pointsDao.saveAll(pointsList);

	}

	@Test
	public void errorPointDataTest() {

		SellerReq samePointName = new SellerReq("hinata", 0, 10000);
		SellerReq samePointCost = new SellerReq("newPointName", 0, 3000);
		SellerReq pointCostEqualToZero = new SellerReq("newPointName2", 0, 0);
		SellerReq discountLessThanZero = new SellerReq("newPointName3", -1, 12000);
		SellerReq discountLargerThanNinetyNine = new SellerReq("newPointName4", 100, 15000);

		SellerRes res1 = sellerService.createPointsExchange(samePointName);
		System.out.println("--> samePointName: " + res1.getMessage());
		
		SellerRes res2 = sellerService.createPointsExchange(samePointCost);
		System.out.println("--> samePointCost: " + res2.getMessage());
		
		SellerRes res3 = sellerService.createPointsExchange(pointCostEqualToZero);
		System.out.println("--> pointCostEqualToZero: " + res3.getMessage());
		
		SellerRes res4 = sellerService.createPointsExchange(discountLessThanZero);
		System.out.println("--> discountLessThanZero: " + res4.getMessage());
		
		SellerRes res5 = sellerService.createPointsExchange(discountLargerThanNinetyNine);
		System.out.println("--> discountLargerThanNinetyNine: " + res5.getMessage());

	}

}
