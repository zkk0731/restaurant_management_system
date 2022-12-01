package com.example.restaurant_management_system.service.ifs;

import java.util.Date;

import com.example.restaurant_management_system.vo.SellerRes;

public interface SellerService {

//	銷售額銷售品項查詢
	public  SellerRes searchSalesVolume(Date startDate,Date endDate); 
}
