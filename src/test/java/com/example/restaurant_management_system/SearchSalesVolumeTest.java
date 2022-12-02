package com.example.restaurant_management_system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.restaurant_management_system.entity.Orders;
import com.example.restaurant_management_system.repository.OrdersDao;
import com.example.restaurant_management_system.service.ifs.SellerService;
import com.example.restaurant_management_system.vo.SellerRes;

@SpringBootTest
public class SearchSalesVolumeTest {

	@Autowired
	private OrdersDao ordersDao;
	
	@Autowired
	private SellerService sellerSerivice;
	
	@Test
	public void queryByDateTest() throws ParseException {
		String dateStr1 = "2022-10-11 00:00:00";
		String dateStr2 = "2022-11-30 00:00:00";
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		LocalDateTime date1 = LocalDateTime.parse(dateStr1,format);
		LocalDateTime date2 = LocalDateTime.parse(dateStr2,format);
		
		List<Orders> result = ordersDao.findByOrderDatetimeBetween(date1, date2);
		for(Orders item:result) {
			System.out.println("--> "+item.getOrderId());
		}
	}
	
	@Test
	public void searchSalesVolumeTest() {
		String dateStr1 = "2022-10-11 00:00:00";
		String dateStr2 = "2022-11-30 00:00:00";
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		LocalDateTime date1 = LocalDateTime.parse(dateStr1,format);
		LocalDateTime date2 = LocalDateTime.parse(dateStr2,format);
		
		SellerRes result = sellerSerivice.searchSalesVolume(date1, date2);
		System.out.println(result.getTotalPrice());
		System.out.println(result.getOrderInfoMap());
		
	}
}
