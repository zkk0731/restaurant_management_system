package com.example.restaurant_management_system;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.restaurant_management_system.entity.Menu;
import com.example.restaurant_management_system.repository.MenuDao;
import com.example.restaurant_management_system.service.ifs.SellerService;
import com.example.restaurant_management_system.vo.SellerReq;
import com.example.restaurant_management_system.vo.SellerRes;

@SpringBootTest
public class CreateCommodityTest {
	@Autowired
	private SellerService sellerService;

	@Autowired
	private MenuDao menuDao;

	@Test
	public void createCommodityTest() {
		SellerReq req = new SellerReq("fish", 120, "meal");
		SellerRes res = sellerService.createCommodity(req);
		Optional<Menu> getFromDao = menuDao.findById("fish");
		System.out.println("--> getMessage: " + res.getMessage());
		System.out.println("--> FromDB getCommodityName: " + getFromDao.get().getCommodityName());

	}
}
