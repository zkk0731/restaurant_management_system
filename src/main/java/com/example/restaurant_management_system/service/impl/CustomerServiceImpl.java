package com.example.restaurant_management_system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restaurant_management_system.entity.Menu;
import com.example.restaurant_management_system.repository.MenuDao;
import com.example.restaurant_management_system.service.ifs.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private MenuDao menuDao;

	// API-5.查詢餐點排行榜
	@Override
	public List<Menu> searchTop5Commodity() {
		List<Menu> commodityLeaderBoard = new ArrayList<>();
		commodityLeaderBoard = menuDao.findTop5ByOrderBySalesVolumeDesc();
		return commodityLeaderBoard;
	}

}
