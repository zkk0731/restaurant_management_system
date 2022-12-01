package com.example.restaurant_management_system.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.restaurant_management_system.entity.Orders;

public class OrdersDaoImpl extends BaseDao{

	public List<Orders> doQueryOrdersByDate(LocalDateTime startDate, LocalDateTime endDate){
		
//			StringBuffer 方便自串串接 可用直接用String相加
			StringBuffer sb = new StringBuffer();
//			sql 語法
			sb.append(" select O from Orders O ").
			append(" where O.orderDatetime between :startDate and :endDate");
			
			Map<String, Object> params = new HashMap<>();
//			帶值給自訂參數
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			
			return doQuery(sb.toString(), params, Orders.class);
		
	}
}
