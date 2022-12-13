package com.example.restaurant_management_system;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.restaurant_management_system.entity.Menu;
import com.example.restaurant_management_system.repository.MenuDao;
import com.example.restaurant_management_system.service.ifs.SellerService;
import com.example.restaurant_management_system.vo.ReadCommodtityRes;
import com.example.restaurant_management_system.vo.SellerReq;

@SpringBootTest
public class ReadCommodtityTest {

	@Autowired
	private SellerService sellerService;

	@Autowired
	private MenuDao menuDao;

	@Test
	public void readCommodtityTest() {
		SellerReq req = new SellerReq();
		req.setCategory("drink");
		ReadCommodtityRes res = sellerService.readCommodtity(req);
		System.out.println("--> size: " + res.getMenus().size());
	}

	@Test
	public void createDefaultCommodity() {
		SellerReq req = new SellerReq("pork", 80, "meal");
		SellerReq req2 = new SellerReq("chicken", 90, "meal");
		SellerReq req3 = new SellerReq("beef", 100, "meal");
		SellerReq req4 = new SellerReq("water", 10, "drink");
		SellerReq req5 = new SellerReq("tea", 30, "drink");

//		sellerService.createCommodity(req);
//		sellerService.createCommodity(req2);
//		sellerService.createCommodity(req3);
//		sellerService.createCommodity(req4);
//		sellerService.createCommodity(req5);

	}
}
