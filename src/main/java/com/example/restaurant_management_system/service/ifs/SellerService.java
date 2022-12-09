package com.example.restaurant_management_system.service.ifs;

import java.time.LocalDateTime;

import com.example.restaurant_management_system.vo.SellerRes;
import com.example.restaurant_management_system.vo.ProcessOrderReq;
import com.example.restaurant_management_system.vo.ProcessOrderRes;
import com.example.restaurant_management_system.vo.SellerReq;

public interface SellerService {

//	銷售額銷售品項查詢(可指定時間範圍)
	public SellerRes searchSalesVolume(LocalDateTime startDateTime, LocalDateTime endDateTime);

	// 未確認訂單查詢
	public ProcessOrderRes searchUncheckedOrder(ProcessOrderReq req);
	
	// 取消訂單
	public ProcessOrderRes cancelOrder(ProcessOrderReq req);
	
	// 建立餐點品項
	public SellerRes createCommodtity(SellerReq req);
}
