package com.example.restaurant_management_system.service.ifs;

import java.time.LocalDateTime;
import com.example.restaurant_management_system.vo.SellerReq;
import com.example.restaurant_management_system.vo.SellerRes;

public interface SellerService {

	// 銷售額銷售品項查詢(可指定時間範圍)
	public SellerRes searchSalesVolume(LocalDateTime startDateTime, LocalDateTime endDateTime);

	// 創建點數兌換
	public SellerRes createPointsExchange(SellerReq req);
}
