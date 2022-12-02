package com.example.restaurant_management_system.service.ifs;

import java.util.List;
import com.example.restaurant_management_system.entity.Menu;

public interface CustomerService {

	// 查詢熱門餐點排行前五
	public List<Menu> searchTop5Commodity();
}
