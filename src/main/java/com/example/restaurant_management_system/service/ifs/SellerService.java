package com.example.restaurant_management_system.service.ifs;

import java.time.LocalDateTime;
import java.util.List;

import com.example.restaurant_management_system.entity.Points;
import com.example.restaurant_management_system.vo.CheckOrderReq;
import com.example.restaurant_management_system.vo.CheckOrderRes;
import com.example.restaurant_management_system.vo.ProcessOrderReq;
import com.example.restaurant_management_system.vo.ProcessOrderRes;
import com.example.restaurant_management_system.vo.ReadCommodtityRes;
import com.example.restaurant_management_system.vo.SellerReq;
import com.example.restaurant_management_system.vo.SellerRes;

public interface SellerService {

	// 銷售額銷售品項查詢(可指定時間範圍)
	public SellerRes searchSalesVolume(LocalDateTime startDateTime, LocalDateTime endDateTime);

	// 創建點數兌換
	public SellerRes createPointsExchange(SellerReq req);

	// 讀取點數兌換
	public List<Points> readPointsExchange();

	// 未確認訂單查詢
	public ProcessOrderRes searchUncheckedOrder(ProcessOrderReq req);

	// 取消訂單
	public ProcessOrderRes cancelOrder(ProcessOrderReq req);

	// 建立餐點品項
	public SellerRes createCommodity(SellerReq req);

	// 顯示餐點品項
	public ReadCommodtityRes readCommodtity(SellerReq req);

	// 更新點數兌換
	public SellerRes updatePointsExchange(SellerReq req);

	// 刪除點數兌換
	public SellerRes deletePointsExchange(SellerReq req);

	// 確認訂單
	public CheckOrderRes checkOrder(CheckOrderReq req);
}
