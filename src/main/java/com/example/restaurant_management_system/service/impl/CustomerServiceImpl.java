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
import com.example.restaurant_management_system.entity.Points;
import com.example.restaurant_management_system.repository.MembersDao;
import com.example.restaurant_management_system.repository.MenuDao;
import com.example.restaurant_management_system.repository.OrdersDao;
import com.example.restaurant_management_system.repository.PointsDao;
import com.example.restaurant_management_system.service.ifs.CustomerService;
import com.example.restaurant_management_system.vo.CustomerReq;
import com.example.restaurant_management_system.vo.CustomerRes;
import com.example.restaurant_management_system.vo.MemberInfo;
import com.example.restaurant_management_system.vo.ReadCommodtityRes;


@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private MenuDao menuDao;

	@Autowired
	private OrdersDao ordersDao;

	@Autowired
	private MembersDao membersDao;
	
	@Autowired
	private PointsDao pointsDao;

	// 查詢餐點排行榜
	@Override
	public List<Menu> searchTop5Commodity() {
		List<Menu> commodityLeaderBoard = new ArrayList<>();
		commodityLeaderBoard = menuDao.findTop5ByOrderBySalesVolumeDesc();
		return commodityLeaderBoard;
	}
	
	// 查詢餐點排行榜(Yu)
	@Override
	public ReadCommodtityRes searchTop5Commodity2() {
		List<Menu> commodityLeaderBoard = new ArrayList<>();
		commodityLeaderBoard = menuDao.findTop5ByOrderBySalesVolumeDesc();
		return new ReadCommodtityRes(commodityLeaderBoard, RtnCode.SUCCESS.getMessage());
	}

	// 點餐
	@Override
	public CustomerRes customerOrder(Map<String, Integer>orderInfoMap, String account, int totalPrice, int pointsCost) {
		CustomerRes res = new CustomerRes();
		
		// 判別orderInfoMap的餐點數量是否大於零
		for(Map.Entry<String, Integer> m:orderInfoMap.entrySet()) {
			if(m.getValue() <= 0) {
				return new CustomerRes(RtnCode.PARAMETER_ERROR.getMessage());
			}			
		}
		
		//將Map轉成String 並去掉前後括號
		String orderInfoString = orderInfoMap.toString().substring(1, orderInfoMap.toString().length() - 1);

		Orders orders = new Orders(orderInfoString, LocalDateTime.now(), "unchecked");
		orders.setTotalPrice(totalPrice);

		// 確認是否為會員
		if (StringUtils.hasText(account)) {
			orders.setMemberAccount(account);
			orders.setPointsCost(pointsCost);
			
				Points points = pointsDao.findByPointsCost(pointsCost);
				
				if(points == null) {
					return new CustomerRes(RtnCode.DISCOUNT_ERROR.getMessage());
				}
				
				int discount = points.getDiscount();
				if (discount > 10) {
					orders.setPriceAfterDiscount(totalPrice * discount / 100);
				} else if (discount <= 10) {
					orders.setPriceAfterDiscount(totalPrice * discount / 10);
				}else {
					orders.setPriceAfterDiscount(totalPrice);
				}
				
			
		}

		//存入DB
		ordersDao.save(orders);

		res.setOrderInfoMap(orderInfoMap);
		res.setMessage(RtnCode.SUCCESS.getMessage());
		res.setTotalPrice(totalPrice);
		return res;

	}

	// 計算總金額
	@Override
	public int calculateTotalPrice(Map<String, Integer> orderInfoMap) {
		List<Menu> menuList = menuDao.findAll();
		int totalPrice = 0;

		for (Entry<String, Integer> item : orderInfoMap.entrySet()) {
			for (Menu menu : menuList) {
				if (menu.getCommodityName().equalsIgnoreCase(item.getKey())) {
					totalPrice += menu.getPrice() * item.getValue();

					// 將購買數量加進菜單中的銷量
//					menu.setSalesVolume(menu.getSalesVolume() + item.getValue());
//					menuDao.save(menu);
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

	// 餐點分類查詢
	@Override
	public CustomerRes searchCategory(String category) {
		// 用逗號切割輸入的類型字串
		String[] categoryArray = category.split(",");
		// 建立Set用來存放餐點分類
		Set<String> categorySet = new HashSet<>();

		for (String commodity : categoryArray) {
			// 去除空格
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

	@Override
	public CustomerRes createMember(CustomerReq req) {
		CustomerRes res = new CustomerRes();
		Members member = membersDao.findByMemberAccount(req.getMemberAccount());

		// 判斷帳號是否被使用
		if (member != null) {
			res.setMessage(RtnCode.ACCOUNT_EXIST.getMessage());
			return res;
		}
		// 設定基本資料
		member = new Members(req.getMemberAccount(), req.getMemberPwd(), req.getMemberName(), req.getMemberPhone());

		// 將非必填的會員資料寫入
		setUnnecessaryMemberInfo(req, member);
		// 存入DB
		membersDao.save(member);

		res.setMember(member);
		res.setMessage(RtnCode.SUCCESS.getMessage());
		return res;
	}

	// 將非必填的會員資料寫入
	private void setUnnecessaryMemberInfo(CustomerReq req, Members member) {
		if (req.getMemberAgeRange() != 0) {
			member.setAgeRange(req.getMemberAgeRange());
		}

		if (req.getMemberEmail() != null) {
			member.setEmail(req.getMemberEmail());
		}

		if (req.getMemberLineId() != null) {
			member.setLineId(req.getMemberLineId());
		}
	}

	@Override
	public Members login(CustomerReq req) {
		
		//判斷帳號密碼是否正確
		return membersDao.findByMemberAccountAndPwd(req.getMemberAccount(), req.getMemberPwd());
		
	}

	//查詢會員資料及訂單
	@Override
	public CustomerRes searchMemberInfo(String account) {
		CustomerRes res = new CustomerRes();
		Members member = membersDao.findByMemberAccount(account);
		MemberInfo memberInfo = new MemberInfo(member.getMemberId(), member.getMemberAccount(), member.getMemberName(), member.getPhone(), member.getAgeRange(),
				member.getLineId(), member.getEmail(), member.getPoints());
		
		List<Orders> orders = ordersDao.findByMemberAccount(account);
		res.setMemberInfo(memberInfo);
		
		//判斷會員是否有消費紀錄
		if(orders == null) {
			res.setMessage(RtnCode.NO_ORDERS_RECORD.getMessage());
			return res;
		}
		
		res.setOrders(orders);
		res.setMessage(RtnCode.SUCCESS.getMessage());
		return res;
	}
}
