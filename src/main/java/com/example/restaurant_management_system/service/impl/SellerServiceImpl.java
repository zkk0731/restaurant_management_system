package com.example.restaurant_management_system.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restaurant_management_system.constants.RtnCode;
import com.example.restaurant_management_system.entity.Orders;
import com.example.restaurant_management_system.repository.OrdersDao;
import com.example.restaurant_management_system.service.ifs.SellerService;
import com.example.restaurant_management_system.vo.SellerRes;

@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	private OrdersDao ordersDao;

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

}
