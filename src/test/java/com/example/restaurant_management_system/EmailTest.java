package com.example.restaurant_management_system;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.restaurant_management_system.service.ifs.SellerService;

@SpringBootTest
public class EmailTest {

	@Autowired
	private SellerService sellerService;
	
	@Test
	public void email() {
		sellerService.sendMessage(null);
	}
	
	@Test
	public void listToArray() {
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		
		String[] arr = list.toArray(new String[0]);
		for(String item : arr) {
			System.out.println(item);
		}
		
	}
	
}
