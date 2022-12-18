package com.example.restaurant_management_system.service.ifs;

import java.util.List;
import java.util.Map;

import com.example.restaurant_management_system.entity.Members;
import com.example.restaurant_management_system.entity.Menu;
import com.example.restaurant_management_system.vo.CustomerReq;
import com.example.restaurant_management_system.vo.CustomerRes;
import com.example.restaurant_management_system.vo.ReadCommodtityRes;

public interface CustomerService {

	// 查詢熱門餐點排行前五
	public List<Menu> searchTop5Commodity();
	
	// 查詢熱門餐點排行前五(Yu)
	public ReadCommodtityRes searchTop5Commodity2(); 

	// 點餐
	public CustomerRes customerOrder(Map<String, Integer>orderInfoMap, String account, int totalPrice, int pointsCost);

	// 餐點分類查詢
	public CustomerRes searchCategory(String category);

	// 創建會員
	public CustomerRes createMember(CustomerReq req);
	
	//登入
	public Members login(CustomerReq req);
	
	//計算訂單總金額
	public int calculateTotalPrice(Map<String, Integer> orderInfoMap);
	
	//查詢會員資訊
	public CustomerRes searchMemberInfo(String account);

}
