package com.example.restaurant_management_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restaurant_management_system.entity.Menu;

@Repository
public interface MenuDao extends JpaRepository<Menu, String> {

	// 透過餐點銷售量前五由大到小列出排序
	public List<Menu> findTop5ByOrderBySalesVolumeDesc();

	public List<Menu> findByCategory(String category);

}
