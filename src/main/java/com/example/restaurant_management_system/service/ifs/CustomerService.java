package com.example.restaurant_management_system.service.ifs;

import java.util.List;

import com.example.restaurant_management_system.entity.Menu;
import com.example.restaurant_management_system.vo.CustomerReq;
import com.example.restaurant_management_system.vo.CustomerRes;

public interface CustomerService {

	// 查詢熱門餐點排行前五
	public List<Menu> searchTop5Commodity();

	// 點餐
	public CustomerRes customerOrder(CustomerReq req);

	// 餐點分類查詢
	public CustomerRes searchCategory(String category);

	// 創建會員
	public CustomerRes createMember(CustomerReq req);

}
