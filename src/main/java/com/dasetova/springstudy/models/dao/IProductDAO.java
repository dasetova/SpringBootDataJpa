package com.dasetova.springstudy.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.dasetova.springstudy.models.entity.Product;

public interface IProductDAO extends CrudRepository<Product, Long> {

	public List<Product> findByNameLikeIgnoreCase(String term);
}
