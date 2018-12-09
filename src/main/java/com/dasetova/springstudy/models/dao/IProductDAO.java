package com.dasetova.springstudy.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dasetova.springstudy.models.entity.Product;

public interface IProductDAO extends CrudRepository<Product, Long> {

	@Query("select p from Product p where p.name like %?1%")
	public List<Product> findByName(String term);
}
