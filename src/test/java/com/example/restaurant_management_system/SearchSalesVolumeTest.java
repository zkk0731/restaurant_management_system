package com.example.restaurant_management_system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.restaurant_management_system.entity.Orders;
import com.example.restaurant_management_system.repository.OrdersDao;

@SpringBootTest
public class SearchSalesVolumeTest {

	@Autowired
	private OrdersDao ordersDao;
	
	@Test
	public void queryByDateTest() throws ParseException {
		String dateStr1 = "2022-11-10";
		String dateStr2 = "2022-11-30";
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr1);
		Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr2);
//		List<Orders> result = ordersDao.doQueryOrdersByDate(date1, date2);
//		for(Orders item:result) {
//			System.out.println("--> "+item.getOrderId());
//		}
	}
}
