package com.example.restaurant_management_system.service.ifs;

import java.util.List;
import com.example.restaurant_management_system.entity.Menu;
import com.example.restaurant_management_system.vo.CustomerReq;
import com.example.restaurant_management_system.vo.CustomerRes;

public interface CustomerService {

	// 查詢熱門餐點排行前五
	public List<Menu> searchTop5Commodity();
	//點餐	
	public CustomerRes customerOrder(CustomerReq req);
}
