package com.example.restaurant_management_system.service.impl;

import java.awt.Menu;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restaurant_management_system.repository.MenuDao;
import com.example.restaurant_management_system.service.ifs.CustomerService;


@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private MenuDao menuDao;
	
	@Override
	public List<Menu> searchTopCommodtity() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
