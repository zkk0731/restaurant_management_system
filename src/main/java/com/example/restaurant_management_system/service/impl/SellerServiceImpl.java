package com.example.restaurant_management_system.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

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
import com.example.restaurant_management_system.service.ifs.SellerService;
import com.example.restaurant_management_system.vo.ProcessOrderReq;
import com.example.restaurant_management_system.vo.ProcessOrderRes;
import com.example.restaurant_management_system.vo.SellerReq;
import com.example.restaurant_management_system.vo.SellerRes;

@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	private OrdersDao ordersDao;

	@Autowired
	private PointsDao pointDao;

	@Autowired
	private MembersDao membersDao;

	@Autowired
	private MenuDao menuDao;

	// 訂單資訊字串轉Map型態
	public Map<String, Integer> orderInfoStrToMap(Orders orders) {
		String orderInfoStr = orders.getOrderInfo();
		// 將訂單資訊字串以","區隔開 會得到 "品項=銷售量" 為一筆資料的陣列
		String[] orderInfoArray = orderInfoStr.split(",");
		Map<String, Integer> orderInfoMap = new HashMap<>();
		for (String item2 : orderInfoArray) {
			// 再用"="將陣列中的單筆資料隔開 分別得出品項與銷售量 再放入Map中
			String[] itemAry = item2.trim().split("=");
			// 以"="區隔開的陣列中 index位置0的位置是品項名稱 1的位置是銷售量 此銷售量目前是String 再將其轉成Integer
			orderInfoMap.put(itemAry[0], Integer.valueOf(itemAry[1]));
		}
		return orderInfoMap;
	}

	// 所有要查詢的訂單資訊放進在同一Map裡
	public Map<String, Integer> putOrderInfoInSameMap(Map<String, Integer> ordersInfoMap,
			Entry<String, Integer> entryMap) {

		// 避免同Key值的Value被後面覆蓋
		if (ordersInfoMap.containsKey(entryMap.getKey())) {
			// 若要放進Map的物件已存在重複的Key值 則將他的Value值取出並和要放進去的物件的Value相加後再放回
			Integer volume = ordersInfoMap.get(entryMap.getKey());
			volume += entryMap.getValue();
			ordersInfoMap.put(entryMap.getKey(), volume);
		} else {
			ordersInfoMap.put(entryMap.getKey(), entryMap.getValue());
		}
		return ordersInfoMap;
	}

	@Override
	public SellerRes searchSalesVolume(LocalDateTime startDate, LocalDateTime endDate) {
		// 藉由時間範圍找出對應的訂單結果
		List<Orders> resultList = ordersDao.findByOrderDatetimeBetween(startDate, endDate);
		Map<String, Integer> ordersInfoMap = new HashMap<>();
		int totalPrice = 0;

		for (Orders item : resultList) {
			// 計算總金額
			totalPrice += item.getTotalPrice();
			// 取出所有訂單的餐點名稱與數量
			for (Entry<String, Integer> item2 : orderInfoStrToMap(item).entrySet()) {
				ordersInfoMap.putAll(putOrderInfoInSameMap(ordersInfoMap, item2));
			}
		}
		// 回傳訊息 總金額 餐點品項及銷售量資訊
		SellerRes res = new SellerRes();
		res.setOrderInfoMap(ordersInfoMap);
		res.setTotalPrice(totalPrice);
		res.setMessage(RtnCode.SUCCESS.getMessage());

		return res;
	}

	// 創建點數兌換
	@Override
	public SellerRes createPointsExchange(SellerReq req) {
		SellerRes res = new SellerRes();
		Points points = pointDao.findByPointName(req.getPointName());

		// 點數兌換名稱不可重複
		if (points != null) {
			res.setMessage(RtnCode.POINTNAME_EXIST.getMessage());
			return res;
		}
		points = new Points(req.getPointName(), req.getDiscount(), req.getPointsCost());
		pointDao.save(points);

		// 回傳點數兌換名稱、折扣、扣除所需點數
		res.setPoints(points);
		res.setMessage(RtnCode.SUCCESS.getMessage());
		return res;
	}

	// 讀取點數兌換
	@Override
	public List<Points> readPointsExchange() {
		List<Points> pointsList = pointDao.findAll();
		return pointsList;
	}

	// 未確認訂單查詢
	@Override
	public ProcessOrderRes searchUncheckedOrder(ProcessOrderReq req) {
		// 取出資料庫中會員資料
		Members getMemberAccount = membersDao.findByMemberAccount(req.getMemberAccount());

		// 判別使用者輸入的帳號是否存在
		if (!StringUtils.hasText(getMemberAccount.getMemberAccount())) {
			return new ProcessOrderRes(RtnCode.PARAMETER_ERROR.getMessage());
		}

		// 取出資料庫中所有未確認的訂單資料
		List<Orders> uncheckedOrders = ordersDao.findByOrderState("unchecked");

		// 判別使用者權限
		if (!getMemberAccount.isAuthority()) {
			// If條件判別權限為會員
			List<Orders> memberUncheckOrders = new ArrayList<Orders>();

			// 從uncheckedOrders取出特定會員所有未確認的訂單資料
			for (Orders item : uncheckedOrders) {
				if (item.getMemberAccount().equalsIgnoreCase(req.getMemberAccount())) {
					memberUncheckOrders.add(item);
				}
			}
			return new ProcessOrderRes(memberUncheckOrders, RtnCode.SUCCESS.getMessage());

		} else if (getMemberAccount.isAuthority()) {
			// If條件判別權限為店家，設定回傳資料
			return new ProcessOrderRes(uncheckedOrders, RtnCode.SUCCESS.getMessage());
		}

		return new ProcessOrderRes(RtnCode.PARAMETER_ERROR.getMessage());
	}

	// 取消訂單
	@Override
	public ProcessOrderRes cancelOrder(ProcessOrderReq req) {

		// 從取出資料庫中取出訂單資料
		Orders order = ordersDao.findByOrderId(req.getOrderId());

		// 判別使用者輸入的訂單 1. 訂單流水好是否為空 2. 訂單是否存在於資料庫 3. 訂單狀態是否為canceled
		if (req.getOrderId() == 0) {
			return new ProcessOrderRes(RtnCode.PARAMETER_ERROR.getMessage());
		} else if (order == null) {
			return new ProcessOrderRes(RtnCode.ORDER_NOT_EXIST.getMessage());
		} else if (order.getOrderState().equalsIgnoreCase("canceled")) {
			return new ProcessOrderRes(RtnCode.ORDER_HAS_CANCELED.getMessage());
		}

		// 將訂單狀態更動為 canceled
		order.setOrderState("canceled");
		ordersDao.save(order);

		// 設定res
		List<Orders> orderInfo = new ArrayList<>();
		orderInfo.add(order);

		return new ProcessOrderRes(orderInfo, RtnCode.SUCCESS.getMessage());
	}

	// 建立餐點品項
	@Override
	public SellerRes createCommodity(SellerReq req) {
		// 判別使用者輸入內容 1. 價格不得小於零 2. 品項名稱不得為空 3. 品項分類不得為空 4. 品項名稱不重複
		if (req.getPrice() <= 0 || !StringUtils.hasText(req.getCommodityName())
				|| !StringUtils.hasText(req.getCategory())) {
			return new SellerRes(RtnCode.PARAMETER_ERROR.getMessage());
		}

		Optional<Menu> menuFromDB = menuDao.findById(req.getCommodityName());
		if (!menuFromDB.isEmpty()) {
			return new SellerRes(RtnCode.COMMODITY_EXIST.getMessage());
		}

		// 回傳餐點資訊
		Menu menu = new Menu(req.getCommodityName(), req.getPrice(), req.getCategory());
		menuDao.save(menu);

		return new SellerRes(RtnCode.SUCCESS.getMessage());
	}

}
