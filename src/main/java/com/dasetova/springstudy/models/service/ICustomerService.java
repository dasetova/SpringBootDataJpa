package com.dasetova.springstudy.models.service;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.dasetova.springstudy.models.entity.Bill;
import com.dasetova.springstudy.models.entity.Customer;
import com.dasetova.springstudy.models.entity.Product;

public interface ICustomerService {
	public List<Customer> findAll();
	//Added for pagination
	public Page<Customer> findAll(Pageable pageable);
	
	public void save(Customer customer);
	
	public Customer findOne(Long id);
	
	public void delete(Long id);
	
	public List<Product> findByName(String term);
	
	public void saveBill(Bill bill);
	
	public Product findProductById(Long id);
	
	public Bill findBillById(Long id);
	
	public void deleteBill(Long id);
}
