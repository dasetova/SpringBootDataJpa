package com.dasetova.springstudy.models.dao;

import java.util.List;

import com.dasetova.springstudy.models.entity.Customer;

public interface ICustomerDAO {
	public List<Customer> findAll();
}
