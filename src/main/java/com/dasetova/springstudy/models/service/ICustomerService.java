package com.dasetova.springstudy.models.service;

import java.util.List;

import com.dasetova.springstudy.models.entity.Customer;

public interface ICustomerService {
	public List<Customer> findAll();
	
	public void save(Customer customer);
	
	public Customer findOne(Long id);
	
	public void delete(Long id);
}
