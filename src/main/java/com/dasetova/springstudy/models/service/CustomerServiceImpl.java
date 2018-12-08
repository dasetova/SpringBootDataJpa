package com.dasetova.springstudy.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dasetova.springstudy.models.dao.ICustomerDAO;
import com.dasetova.springstudy.models.entity.Customer;

@Service
public class CustomerServiceImpl implements ICustomerService {

	@Autowired
	private ICustomerDAO customerDAO;
	
	@Override
	@Transactional(readOnly=true)
	public List<Customer> findAll() {
		// TODO Auto-generated method stub
		return (List<Customer>) customerDAO.findAll();
	}

	@Override
	@Transactional
	public void save(Customer customer) {
		// TODO Auto-generated method stub
		customerDAO.save(customer);
	}

	@Override
	@Transactional(readOnly=true)
	public Customer findOne(Long id) {
		// TODO Auto-generated method stub
		return customerDAO.findOne(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		customerDAO.delete(id);
	}

}
