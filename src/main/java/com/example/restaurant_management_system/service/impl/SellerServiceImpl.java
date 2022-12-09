package com.example.restaurant_management_system.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.example.restaurant_management_system.service.ifs.SellerService;
import com.example.restaurant_management_system.vo.SellerRes;
import com.example.restaurant_management_system.vo.ProcessOrderReq;
import com.example.restaurant_management_system.vo.ProcessOrderRes;
import com.example.restaurant_management_system.vo.SellerReq;

@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	private OrdersDao ordersDao;

	@Autowired
	private MenuDao menuDao;

	@Autowired
	private MembersDao membersDao;

//	訂單資訊字串轉Map型態
	public Map<String, Integer> orderInfoStrToMap(Orders orders) {
		String orderInfoStr = orders.getOrderInfo();
//		將訂單資訊字串以","區隔開 會得到 "品項=銷售量" 為一筆資料的陣列
		String[] orderInfoArray = orderInfoStr.split(",");
		Map<String, Integer> orderInfoMap = new HashMap<>();
		for (String item2 : orderInfoArray) {
//			再用"="將陣列中的單筆資料隔開 分別得出品項與銷售量 再放入Map中
			String[] itemAry = item2.trim().split("=");
//			以"="區隔開的陣列中 index位置0的位置是品項名稱 1的位置是銷售量 此銷售量目前是String 再將其轉成Integer
			orderInfoMap.put(itemAry[0], Integer.valueOf(itemAry[1]));
		}
		return orderInfoMap;
	}

//	所有要查詢的訂單資訊放進在同一Map裡
	public Map<String, Integer> putOrderInfoInSameMap(Map<String, Integer> ordersInfoMap,
			Entry<String, Integer> entryMap) {

//		避免同Key值的Value被後面覆蓋
		if (ordersInfoMap.containsKey(entryMap.getKey())) {
//			若要放進Map的物件已存在重複的Key值 則將他的Value值取出並和要放進去的物件的Value相加後再放回
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
//		藉由時間範圍找出對應的訂單結果
		List<Orders> resultList = ordersDao.findByOrderDatetimeBetween(startDate, endDate);
		Map<String, Integer> ordersInfoMap = new HashMap<>();
		int totalPrice = 0;

		for (Orders item : resultList) {
//			計算總金額
			totalPrice += item.getTotalPrice();
//			取出所有訂單的餐點名稱與數量			
			for (Entry<String, Integer> item2 : orderInfoStrToMap(item).entrySet()) {
				ordersInfoMap.putAll(putOrderInfoInSameMap(ordersInfoMap, item2));
			}
		}
//		回傳訊息 總金額 餐點品項及銷售量資訊	
		SellerRes res = new SellerRes();
		res.setOrderInfoMap(ordersInfoMap);
		res.setTotalPrice(totalPrice);
		res.setMessage(RtnCode.SUCCESS.getMessage());

		return res;
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
		// 判別使用者輸入的訂單流水號 1. 是否為空 2. 是否存在於資料庫

		// 將訂單狀態更動為 canceled

		return null;
	}

	// 建立餐點品項
	@Override
	public SellerRes createCommodtity(SellerReq req) {
		// 判別使用者輸入內容 1. 品項名稱不為空 2. 價格不得小於零 3. 分類不為空 4.品項名稱不重複 （重複直接覆蓋掉？）
		if (!StringUtils.hasText(req.getCommodityName()) || req.getPrice() <= 0
				|| !StringUtils.hasText(req.getCategory())) {
			return new SellerRes(RtnCode.PARAMETER_ERROR.getMessage());
		}

		// 4.品項名稱不重複 （重複直接覆蓋掉？當作update）（待寫）

		// 將輸入的餐點品項儲存至資料庫
		Menu menu = new Menu(req.getCommodityName(), req.getPrice(), req.getCategory());
		menuDao.save(menu);

		return new SellerRes(RtnCode.PARAMETER_REQUIRED.getMessage());
	}

}
