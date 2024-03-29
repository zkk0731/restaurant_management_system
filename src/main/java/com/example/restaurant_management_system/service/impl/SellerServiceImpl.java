package com.example.restaurant_management_system.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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
import com.example.restaurant_management_system.vo.CheckOrderReq;
import com.example.restaurant_management_system.vo.CheckOrderRes;
import com.example.restaurant_management_system.vo.ProcessOrderReq;
import com.example.restaurant_management_system.vo.ProcessOrderRes;
import com.example.restaurant_management_system.vo.ReadCommodtityRes;
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
	private JavaMailSender mailSender;

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
		Points pointsByPointsCost = pointDao.findByPointsCost(req.getPointsCost());

		// 點數兌換名稱是否存在
		if (!StringUtils.hasText(req.getPointName())) {
			return new SellerRes(RtnCode.PARAMETER_REQUIRED.getMessage());
		}

		// 扣除所需點數不得為小於等於零
		if (req.getPointsCost() <= 0) {
			return new SellerRes(RtnCode.POINTCOST_ERROR.getMessage());
		}

		// 折扣範圍0~99(0為兌換物品，其他數字為折扣數)
		if (req.getDiscount() < 0 || req.getDiscount() > 99) {
			return new SellerRes(RtnCode.DISCOUNT_ERROR.getMessage());
		}

		// 點數兌換名稱不可重複
		if (points != null) {
			return new SellerRes(RtnCode.POINTNAME_EXIST.getMessage());
		}

		// 扣除所需點數不可重複
		if (pointsByPointsCost != null) {
			return new SellerRes(RtnCode.POINTCOST_EXIST.getMessage());
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
		// 取出資料庫中所有未確認的訂單資料
		List<Orders> orders = ordersDao.findAllByOrderByOrderDatetimeDesc();
		
		if(orders.isEmpty()) {
			return new ProcessOrderRes(RtnCode.ORDER_NOT_EXIST.getMessage());
		}
		
		return new ProcessOrderRes(orders, RtnCode.SUCCESS.getMessage());
	}

	// 推播功能
	@Override
	public SellerRes sendMessage(SellerReq req) {

		SimpleMailMessage message = new SimpleMailMessage();
		List<Members> members = membersDao.findAll();

		if (members == null) {
			return new SellerRes("無會員資訊");
		}

		List<String> emailList = new ArrayList<>();
		for (Members item : members) {
			if (StringUtils.hasText(item.getEmail())) {
				emailList.add(item.getEmail());
			}
		}

		if (CollectionUtils.isEmpty(emailList)) {
			return new SellerRes("無email資訊");
		}

		String[] emailArr = emailList.toArray(new String[0]);

		message.setFrom("arashi11031228@gmail.com");
		message.setTo(emailArr);
		message.setSubject(req.getEmailTitle());
		message.setText(req.getEmailMessage());

		mailSender.send(message);

		return new SellerRes(RtnCode.SUCCESS.getMessage());
	}

	// 取消訂單
	@Override
	public ProcessOrderRes cancelOrder(ProcessOrderReq req) {

		// 從取出資料庫中取出訂單資料
		Orders order = ordersDao.findByOrderId(req.getOrderId());

		// 判別使用者輸入的訂單 1. 訂單流水號是否為空 2. 訂單是否存在於資料庫 3. 訂單狀態是否為canceled
		if (req.getOrderId() == 0) {
			return new ProcessOrderRes(RtnCode.PARAMETER_ERROR.getMessage());
		} else if (order == null) {
			return new ProcessOrderRes(RtnCode.ORDER_NOT_EXIST.getMessage());
		} else if (order.getOrderState().equalsIgnoreCase("canceled")) {
			return new ProcessOrderRes(RtnCode.ORDER_HAS_CANCELED.getMessage());
		}

		// 將訂單狀態更動為 canceled
		order.setOrderState("canceled");
		order.setPointsCost(0);
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

	// 顯示餐點品項(輸入餐點分類)
	@Override
	public ReadCommodtityRes readCommodtity(SellerReq req) {
		// 判別使用者輸入內容，輸入之餐點分類不得為空
		if (!StringUtils.hasText(req.getCategory())) {
			return new ReadCommodtityRes(RtnCode.PARAMETER_ERROR.getMessage());
		}

		// 從資料庫取出特定類別的所有餐點
		List<Menu> getMenusByCategory = menuDao.findByCategory(req.getCategory());
		
		// 判別使用者輸入內容，輸入之餐點分類需存在於資料庫中
		if (getMenusByCategory.isEmpty() || getMenusByCategory == null) {
			return new ReadCommodtityRes(RtnCode.PARAMETER_ERROR.getMessage());
		}		

		return new ReadCommodtityRes(getMenusByCategory, RtnCode.SUCCESS.getMessage());
	}
	
	// 顯示所有餐點品項
	@Override
	public ReadCommodtityRes readAllCommodtity() {
		List<Menu> getAllMenus = menuDao.findAll();
		if (getAllMenus.isEmpty() || getAllMenus == null) {
			return new ReadCommodtityRes(RtnCode.PARAMETER_ERROR.getMessage());
		}
		
		return new ReadCommodtityRes(getAllMenus, RtnCode.SUCCESS.getMessage());
	}

	// 更新點數兌換
	@Override
	public SellerRes updatePointsExchange(SellerReq req) {
		Points points = pointDao.findByPointName(req.getPointName());

		if (!StringUtils.hasText(req.getPointName()) || req.getDiscount() < 0 || req.getPointsCost() <= 0) {
			return new SellerRes(RtnCode.PARAMETER_ERROR.getMessage());
		}

		if (points == null) {
			return new SellerRes(RtnCode.PARAMETER_ERROR.getMessage());
		}
		points.setPointName(req.getNewPointName());
		points.setDiscount(req.getDiscount());
		points.setPointsCost(req.getPointsCost());
		pointDao.save(points);

		return new SellerRes(RtnCode.SUCCESS.getMessage());
	}

	// 刪除點數兌換
	@Override
	public SellerRes deletePointsExchange(SellerReq req) {
		Points points = pointDao.findByPointName(req.getPointName());

		if (!StringUtils.hasText(req.getPointName())) {
			return new SellerRes(RtnCode.PARAMETER_ERROR.getMessage());
		}

		if (points == null) {
			return new SellerRes(RtnCode.PARAMETER_ERROR.getMessage());
		}

		pointDao.delete(points);

		return new SellerRes(RtnCode.SUCCESS.getMessage());
	}

	// 確認訂單
	@Override
	public CheckOrderRes checkOrder(CheckOrderReq req) {
		// 從資料庫取出訂單資訊
		Optional<Orders> getOrderFromDB = ordersDao.findById(req.getOrderId());

		// 若輸入的訂單不在資料庫內回傳錯誤訊息
		if (getOrderFromDB.isEmpty()) {
			return new CheckOrderRes(RtnCode.ORDER_NOT_EXIST.getMessage());
		}

		Orders order = getOrderFromDB.get();

		// 判別此訂單是否是會員訂單
		if (StringUtils.hasText(order.getMemberAccount())) {
			// 得到折扣後價格
			
//			int afterDiscountTotalPrice = getTotalPriceAfterDiscount(order.getPointsCost(), order.getTotalPrice());

			// 更改 order 的總價格及得到的點數
//			order.setTotalPrice(afterDiscountTotalPrice);
			order.setPointsGet(order.getPriceAfterDiscount());
			
			
			// 更改資料庫會員資訊的集點數
			updateMemberPoint(order.getMemberAccount(), order.getPointsGet(), order.getPointsCost());
			
		}

		// 更改資料庫菜單的銷售量
		updateMenu(order.getOrderInfo());

		// 更改訂單狀態
		order.setOrderState("checked");
		ordersDao.save(order);

		return new CheckOrderRes(order, RtnCode.SUCCESS.getMessage());
	}

	private int getTotalPriceAfterDiscount(int orderPointsCost, int beforDiscountTotalPrice) {
		Points point = pointDao.findByPointsCost(orderPointsCost);

		if(point == null) {
			return beforDiscountTotalPrice;
		}
		int discountFromDB = point.getDiscount();

		// 若折扣數小於等於零，回傳原本的價格
		if (discountFromDB <= 0) {
			return beforDiscountTotalPrice;
		}

		int afterDiscountTotalPrice = 0;

		if (discountFromDB > 10) {
			afterDiscountTotalPrice = beforDiscountTotalPrice * discountFromDB / 100;
		} else if (discountFromDB <= 10) {
			afterDiscountTotalPrice = beforDiscountTotalPrice * discountFromDB / 10;
		}

		return afterDiscountTotalPrice;
	}

	private void updateMemberPoint(String memberAccount, int orderPointGet, int orderPointCost) {
		Members member = membersDao.findByMemberAccount(memberAccount);
		member.setPoints(member.getPoints() + orderPointGet - orderPointCost);
		membersDao.save(member);
	}

	private void updateMenu(String orderInfo) {
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

		// 更改銷售量
		List<Menu> menusFromDB = menuDao.findByCommodityNameIn(allOrderCommodityName);
		for (Menu menu : menusFromDB) {
			menu.setSalesVolume(menu.getSalesVolume() + orderInfoFromDB.get(menu.getCommodityName()));
		}

		menuDao.saveAll(menusFromDB);
	}

}
