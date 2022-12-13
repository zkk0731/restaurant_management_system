package com.example.restaurant_management_system;

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
import com.example.restaurant_management_system.service.ifs.SellerService;
import com.example.restaurant_management_system.vo.CheckOrderReq;
import com.example.restaurant_management_system.vo.CheckOrderRes;

@SpringBootTest
public class CheckOrderTest {
	@Autowired
	private SellerService sellerService;

	@Autowired
	private MembersDao membersDao;

	@Autowired
	private MenuDao menuDao;

	@Autowired
	private OrdersDao ordersDao;

	@Autowired
	private PointsDao pointDao;

	@Test
	public void checkOrderTest() {
		int orderId = 1;
		CheckOrderReq req = new CheckOrderReq();
		req.setOrderId(orderId);

		CheckOrderRes res = sellerService.checkOrder(req);
		System.out.println("res.getMessage(): " + res.getMessage());
		System.out.println("res.getOrders().getOrderState(): " + res.getOrders().getOrderState());

	}

	@Test
	public void updateMembersAndOrdersDBTest() {
		String memberAccount = "M01";
		int orderId = 1;
		String pointName = "PointName1";

		// update table orders

		Optional<Orders> order = ordersDao.findById(orderId);
		if (order.isEmpty()) {
			System.out.println("Error. Order is not in DB");
		}

		Orders resOrder = order.get();
		int beforDiscountPrice = resOrder.getTotalPrice();
		int afterDiscountPrice = 0;

		Points point = pointDao.findByPointName(pointName);
		int discountFromDB = point.getDiscount();
		if (discountFromDB > 10) {
			afterDiscountPrice = beforDiscountPrice * discountFromDB / 100;
		} else if (discountFromDB < 10) {
			afterDiscountPrice = beforDiscountPrice * discountFromDB / 10;
		}
		resOrder.setOrderState("checked");
		resOrder.setPointsGet(afterDiscountPrice);
		resOrder.setPointsCost(point.getPointsCost());
		resOrder.setTotalPrice(afterDiscountPrice);
		ordersDao.save(resOrder);

		Orders res = ordersDao.findByOrderId(orderId);
//		System.out.println("Success. res.getOrderState(): " + res.getOrderState());
//		System.out.println("Success. res.getTotalPrice(): " + res.getTotalPrice());
//		System.out.println("Success. res.setPointsGet(): " + res.setPointsGet());

		// update table members 更改會員的集點數
		Members member = membersDao.findByMemberAccount(memberAccount);
		member.setPoints(member.getPoints() + resOrder.getPointsGet() - resOrder.getPointsCost());
		membersDao.save(member);
		Members res2 = membersDao.findByMemberAccount(memberAccount);
//		System.out.println("Success. res2.getPoints(): " + res2.getPoints());

	}

	@Test
	public void getDiscountTest() {
		String pointName = "PointName1";
		Points point = pointDao.findByPointName(pointName);
		System.out.println("--> point.getDiscount(): " + point.getDiscount());

		int discountFromDB = point.getDiscount();
		int price = 0;
		if (discountFromDB > 10) {
			price = 1000 * discountFromDB / 100;
		} else if (discountFromDB <= 10) {
			price = 1000 * discountFromDB / 10;
		}
		System.out.println("--> price: " + price);
	}

	@Test
	public void updateMenuDBTest() {
		// update table menu 更改菜單的銷售量
		// 取出訂單資料 菜單名稱與數量
		// 更改銷售量

		int orderId = 1;
		Optional<Orders> order = ordersDao.findById(orderId);
		if (order.isEmpty()) {
			System.out.println("Error. Order is not in DB");
		}
		Orders resOrder = order.get();
		// update table menu 更改菜單的銷售量
		// 取出訂單資料 菜單名稱與數量
		String orderInfo = resOrder.getOrderInfo();
		// 將 orderInfo 的型態由 String 轉成 Map
		// 以逗號分隔，去除空白。以等於分隔前後餐點及餐點數量。將資料放入Map中
		Map<String, Integer> orderInfoFromDB = new HashMap<>();
		List<String> allOrderCommodityName = new ArrayList<>();
		String[] removeComma = orderInfo.split(",");
		String resOrderInfo = "";

		for (String item : removeComma) {
			// 去除空白
			String itemRemoveSpace = item.trim();

			// 以等號分隔前後字串
			String[] itemSplitByEqualSymbol = itemRemoveSpace.split("=");

			// 將餐點名稱及餐點數量放入Map中
			String itemCommodityName = itemSplitByEqualSymbol[0];
			Integer itemCommodityQuantity = Integer.valueOf(itemSplitByEqualSymbol[1]);
			orderInfoFromDB.put(itemCommodityName, itemCommodityQuantity);

			// 將餐點名稱放入List
			allOrderCommodityName.add(itemCommodityName);
		}

//		System.out.println(allOrderCommodityName.size());
//		System.out.println(allOrderCommodityName.get(0));
//		System.out.println(allOrderCommodityName.get(1));

//		System.out.println(orderInfoFromDB.size());
//		System.out.println(orderInfoFromDB.get("chicken"));
//		System.out.println(orderInfoFromDB.get("beef"));

		// 更改銷售量
		List<Menu> menusFromDB = menuDao.findByCommodityNameIn(allOrderCommodityName);
		for (Menu menu : menusFromDB) {
			System.out
					.println("before add menu salesVolume: " + menu.getCommodityName() + " = " + menu.getSalesVolume());
			menu.setSalesVolume(menu.getSalesVolume() + orderInfoFromDB.get(menu.getCommodityName()));
			System.out
					.println("after add menu salesVolume: " + menu.getCommodityName() + " = " + menu.getSalesVolume());
		}

		List<Menu> afterSaveMenus = menuDao.saveAll(menusFromDB);
		for (int i = 0; i < afterSaveMenus.size(); i++) {
			System.out.println("SalesVolume: " + afterSaveMenus.get(i).getCommodityName() + " = "
					+ afterSaveMenus.get(i).getSalesVolume());
		}
	}

	@Test
	public void getMenuListTest() {
		List<String> allOrderCommodityName = new ArrayList<>();
		allOrderCommodityName.add("chicken");
		allOrderCommodityName.add("beef");

		List<Menu> res = menuDao.findByCommodityNameIn(allOrderCommodityName);
		System.out.println(res.get(0).getCommodityName() + " = " + res.get(0).getPrice());
		System.out.println(res.get(1).getCommodityName() + " = " + res.get(1).getPrice());

	}

	@Test
	public void getPointsCostFromOrdersAndGetDiscount() {
		int orderId = 1;
		Optional<Orders> order = ordersDao.findById(orderId);

		Orders resOrder = order.get();
		int orderPointsCost = resOrder.getPointsCost();
		Points point = pointDao.findByPointsCost(orderPointsCost);
		System.out.println("Discount: " + point.getDiscount());

		int discountFromDB = point.getDiscount();
		int beforDiscountTotalPrice = order.get().getTotalPrice();
		int afterDiscountTotalPrice = 0;
		if (discountFromDB > 10) {
			afterDiscountTotalPrice = beforDiscountTotalPrice * discountFromDB / 100;
		} else if (discountFromDB <= 10) {
			afterDiscountTotalPrice = beforDiscountTotalPrice * discountFromDB / 10;
		}
		System.out.println("beforDiscountTotalPrice: " + beforDiscountTotalPrice);
		System.out.println("afterDiscountTotalPrice: " + afterDiscountTotalPrice);

	}
}
