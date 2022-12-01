package com.example.restaurant_management_system.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BaseDao {
	
	@PersistenceContext // Jpa±M¦³µùÄÀ
	private EntityManager entityManager;

}
