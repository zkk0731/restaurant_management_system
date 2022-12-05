package com.example.restaurant_management_system.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.restaurant_management_system.constants.RtnCode;
import com.example.restaurant_management_system.entity.Members;
import com.example.restaurant_management_system.entity.Menu;
import com.example.restaurant_management_system.entity.Orders;
import com.example.restaurant_management_system.repository.MembersDao;
import com.example.restaurant_management_system.repository.MenuDao;
import com.example.restaurant_management_system.repository.OrdersDao;
import com.example.restaurant_management_system.service.ifs.CustomerService;
import com.example.restaurant_management_system.vo.CustomerReq;
import com.example.restaurant_management_system.vo.CustomerRes;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private MenuDao menuDao;

	@Autowired
	private OrdersDao ordersDao;

	@Autowired
	private MembersDao membersDao;

	// API-5.查詢餐點排行榜
	@Override
	public List<Menu> searchTop5Commodity() {
		List<Menu> commodityLeaderBoard = new ArrayList<>();
		commodityLeaderBoard = menuDao.findTop5ByOrderBySalesVolumeDesc();
		return commodityLeaderBoard;
	}

	// 點餐
	@Override
	public CustomerRes customerOrder(CustomerReq req) {
		CustomerRes res = new CustomerRes();
		Map<String, Integer> orderInfoMap = req.getOrderInfoMap();
		String orderInfoString = orderInfoMap.toString().substring(1, orderInfoMap.toString().length() - 1);

		int totalPrice = calculateTotalPrice(orderInfoMap);
		Orders orders = new Orders(orderInfoString, LocalDateTime.now(), "unchecked");
		orders.setTotalPrice(totalPrice);

		// 確認是否為會員
		if (StringUtils.hasText(req.getMemberAccount())) {
			Members memberInfo = membersDao.findByMemberAccount(req.getMemberAccount());
			orders.setMemberAccount(req.getMemberAccount());

			// 點數兌換及獲得
			pointsGetAndExchange(req.getCostPoints(), memberInfo, orders, res);

			membersDao.save(memberInfo);

		}

		ordersDao.save(orders);

		res.setOrderInfoMap(orderInfoMap);
		res.setMessage(RtnCode.SUCCESS.getMessage());
		res.setTotalPrice(totalPrice);
		return res;

	}

	// 計算總金額
	private int calculateTotalPrice(Map<String, Integer> orderInfoMap) {
		List<Menu> menuList = menuDao.findAll();
		int totalPrice = 0;

		for (Entry<String, Integer> item : orderInfoMap.entrySet()) {
			for (Menu menu : menuList) {
				if (menu.getCommodityName().equalsIgnoreCase(item.getKey())) {
					totalPrice += menu.getPrice() * item.getValue();

					// 將購買數量加進菜單中的銷量
					menu.setSalesVolume(menu.getSalesVolume() + item.getValue());
					menuDao.save(menu);
				}
			}
		}

		return totalPrice;
	}

	// 點數兌換及獲得
	private void pointsGetAndExchange(int costPoints, Members memberInfo, Orders orders, CustomerRes res) {
		int pointsCost = 0;
		int pointsGet = 0;
		int totalPrice = orders.getTotalPrice();

		if (costPoints < memberInfo.getPoints()) {
			switch (costPoints) {
			case 1:
				totalPrice *= 0.9;
				res.setPointsExchangeMessage("打9折");
				pointsCost += 1;
				break;
			case 3:
				res.setPointsExchangeMessage("兌換免費甜點一份");
				pointsCost += 3;
				break;
			case 5:
				res.setPointsExchangeMessage("兌換XXX");
				pointsCost += 5;
				break;
			}
		}

		if (totalPrice > 100) {
			pointsGet += totalPrice / 100;
		}
		orders.setTotalPrice(totalPrice);
		orders.setPointsCost(pointsCost);
		orders.setPointsGet(pointsGet);
		memberInfo.setPoints(memberInfo.getPoints() - pointsCost + pointsGet);
	}

	// API-6.餐點分類查詢
	@Override
	public CustomerRes findByCategory(String category) {
		String[] categoryArray = category.split(",");
		Set<String> categorySet = new HashSet<>();

		for (String commodity : categoryArray) {
			String str = commodity.trim();
			categorySet.add(str);
		}

		List<Menu> menuList = menuDao.findAll();
		Set<Menu> menuSet = new HashSet<>();
		for (String categoryStr : categorySet) {
			for (Menu menu : menuList) {
				if (menu.getCategory().contains(categoryStr)) {
					menuSet.add(menu);
				}
			}
		}

		CustomerRes res = new CustomerRes();
		res.setMenuSet(menuSet);
		return res;
	}
}
