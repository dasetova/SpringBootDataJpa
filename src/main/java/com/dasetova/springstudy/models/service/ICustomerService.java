package com.dasetova.springstudy.models.service;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.dasetova.springstudy.models.entity.Customer;

public interface ICustomerService {
	public List<Customer> findAll();
	//Added for pagination
	public Page<Customer> findAll(Pageable pageable);
	
	public void save(Customer customer);
	
	public Customer findOne(Long id);
	
	public void delete(Long id);
	
	
}
