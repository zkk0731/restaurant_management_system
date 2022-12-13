package com.example.restaurant_management_system;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.restaurant_management_system.entity.Points;
import com.example.restaurant_management_system.repository.PointsDao;
import com.example.restaurant_management_system.service.ifs.SellerService;
import com.example.restaurant_management_system.vo.SellerReq;
import com.example.restaurant_management_system.vo.SellerRes;

@SpringBootTest
public class UpdateAndDeletePointsExchangeTest {
	@Autowired
	private SellerService sellerService;

	@Autowired
	private PointsDao pointsDao;

	@Test
	public void deletePointTest() {
		String pointName = "PointName1";

		SellerReq req = new SellerReq();
		req.setPointName(pointName);
		SellerRes res = sellerService.deletePointsExchange(req);
		Points pointFromDB = pointsDao.findByPointName(pointName);
		System.out.println("--> res.getMessage(): " + res.getMessage());
	}
	
	@Test
	public void updatePointTest() {
		String pointName = "PointName1";

		SellerReq req = new SellerReq();
		req.setPointName(pointName);
		req.setDiscount(98);
		req.setPointsCost(988);
		SellerRes res = sellerService.updatePointsExchange(req);
		Points pointFromDB = pointsDao.findByPointName(pointName);
		System.out.println("--> res.getMessage(): " + res.getMessage());
		System.out.println("--> pointFromDB.getDiscount(): " + pointFromDB.getDiscount());
		System.out.println("--> pointFromDB.getPointsCost(): " + pointFromDB.getPointsCost());
	}

	@Test
	public void createDefaultPoint() {
		SellerReq req = new SellerReq();
		req.setPointName("PointName1");
		req.setDiscount(85);
		req.setPointsCost(500);

		SellerReq req2 = new SellerReq();
		req2.setPointName("PointName2");
		req2.setDiscount(88);
		req2.setPointsCost(600);

		SellerReq req3 = new SellerReq();
		req3.setPointName("PointName3");
		req3.setDiscount(79);
		req3.setPointsCost(700);

		SellerReq req4 = new SellerReq();
		req4.setPointName("PointName4");
		req4.setDiscount(66);
		req4.setPointsCost(800);

		SellerReq req5 = new SellerReq();
		req5.setPointName("PointName5");
		req5.setDiscount(61);
		req5.setPointsCost(900);

//		sellerService.createPointsExchange(req);
//		sellerService.createPointsExchange(req2);
//		sellerService.createPointsExchange(req3);
//		sellerService.createPointsExchange(req4);
//		sellerService.createPointsExchange(req5);

	}
}
