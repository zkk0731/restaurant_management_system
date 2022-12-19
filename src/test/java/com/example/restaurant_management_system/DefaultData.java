package com.example.restaurant_management_system;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.restaurant_management_system.entity.Members;
import com.example.restaurant_management_system.entity.Menu;
import com.example.restaurant_management_system.entity.Orders;
import com.example.restaurant_management_system.entity.Points;
import com.example.restaurant_management_system.repository.MembersDao;
import com.example.restaurant_management_system.repository.MenuDao;
import com.example.restaurant_management_system.repository.OrdersDao;
import com.example.restaurant_management_system.repository.PointsDao;

@SpringBootTest
public class DefaultData {	
	@Autowired
	private PointsDao pointsDao;
	
	@Autowired
	private MenuDao menuDao;
	
	@Autowired
	private MembersDao membersDao;
	
	@Autowired
	private OrdersDao ordersDao;
	
	@Test
	public void membersData() {
		Members seller = new Members();
		seller.setMemberAccount("S01");
		seller.setPwd("s01");
		seller.setAuthority(true);
		seller.setMemberName("Seller");
		seller.setPhone("0912345678");
		seller.setAgeRange(1);
		membersDao.save(seller);
				
		Members customer1 = new Members();
		customer1.setMemberAccount("C01");
		customer1.setPwd("c01");
		customer1.setAuthority(false);
		customer1.setMemberName("Customer1");
		customer1.setPhone("0912345677");
		customer1.setAgeRange(2);
		customer1.setPoints(5000);
		membersDao.save(customer1);
		
		Members customer2 = new Members();
		customer2.setMemberAccount("C02");
		customer2.setPwd("c02");
		customer2.setAuthority(false);
		customer2.setMemberName("Customer2");
		customer2.setPhone("0912345676");
		customer2.setAgeRange(3);
		customer2.setPoints(100);
		membersDao.save(customer2);

		Members customer3 = new Members();
		customer3.setMemberAccount("C03");
		customer3.setPwd("c03");
		customer3.setAuthority(false);
		customer3.setMemberName("Customer3");
		customer3.setPhone("0912345675");
		customer3.setAgeRange(4);
		customer3.setPoints(200);
		membersDao.save(customer3);
	}

	@Test
	public void menuData() {
		List<Menu> menuList = new ArrayList<>();
		menuList.add(new Menu("fish", 120, "meal", 20));
		menuList.add(new Menu("beef", 100, "meal", 15));
		menuList.add(new Menu("chicken", 90, "meal", 10));
		menuList.add(new Menu("pork", 80, "meal", 5));
		menuList.add(new Menu("tea", 30, "drink", 3));
		menuList.add(new Menu("water", 10, "drink", 1));

		menuDao.saveAll(menuList);
	}
	
	@Test
	public void pointsData() {
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
	public void ordersData() {
		Orders order1 = new Orders();
		order1.setOrderInfo("chicken=2, beef=3");
		order1.setTotalPrice(480);
		order1.setMemberAccount("C01");
		order1.setOrderDatetime(LocalDateTime.now());
		order1.setOrderState("unchecked");
		order1.setPointsGet(0);
		order1.setPointsCost(700);
		ordersDao.save(order1);
		
		
		Orders order2 = new Orders();
		order2.setOrderInfo("chicken=1, pork=5");
		order2.setTotalPrice(490);
		order2.setMemberAccount("C01");
		order2.setOrderDatetime(LocalDateTime.now());
		order2.setOrderState("unchecked");
		order2.setPointsGet(0);
		order2.setPointsCost(1500);
		ordersDao.save(order2);
		
		Orders order3 = new Orders();
		order3.setOrderInfo("chicken=2, beef=5, pork=1");
		order3.setTotalPrice(760);
		order3.setMemberAccount("C02");
		order3.setOrderDatetime(LocalDateTime.now());
		order3.setOrderState("unchecked");
		order3.setPointsGet(0);
		order3.setPointsCost(0);
		ordersDao.save(order3);
		
		Orders order4 = new Orders();
		order4.setOrderInfo("chicken=1, beef=1");
		order4.setTotalPrice(190);
		order4.setMemberAccount("");
		order4.setOrderDatetime(LocalDateTime.now());
		order4.setOrderState("unchecked");
		order4.setPointsGet(0);
		order4.setPointsCost(0);
		ordersDao.save(order4);
		
		Orders order5 = new Orders();
		order5.setOrderInfo("chicken=3, pork=1, tea=4");
		order5.setTotalPrice(470);
		order5.setMemberAccount("");
		order5.setOrderDatetime(LocalDateTime.now());
		order5.setOrderState("checked");
		order5.setPointsGet(0);
		order5.setPointsCost(0);
		ordersDao.save(order5);
	}
}
