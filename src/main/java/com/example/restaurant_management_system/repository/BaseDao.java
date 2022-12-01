package com.example.restaurant_management_system.repository;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.util.CollectionUtils;

public class BaseDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
 
	//無限制返回筆數的查詢
	protected <EntityType> List<EntityType> doQuery(String sql, Map<String, Object> params, Class<EntityType> clazz) {
		Query query = entityManager.createQuery(sql, clazz);

		if (!CollectionUtils.isEmpty(params)) {
				
			for (Parameter p : query.getParameters()) {
				query.setParameter(p, params.get(p.getName()));
			}
		}
		return query.getResultList();
	}
}
