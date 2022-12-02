package com.example.restaurant_management_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restaurant_management_system.entity.Menu;
import com.example.restaurant_management_system.service.ifs.CustomerService;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	// API-4.查詢餐點排行榜
	@PostMapping(value = "/searchTop5Commodity")
	public List<Menu> searchTop5Commodity() {
		return customerService.searchTop5Commodity();
	}
}
