package com.example.restaurant_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restaurant_management_system.entity.Menu;

@Repository
public interface MenuDao extends JpaRepository<Menu, String> {
	
	
}
