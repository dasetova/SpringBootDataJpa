package com.dasetova.springstudy.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dasetova.springstudy.models.dao.IBillDAO;
import com.dasetova.springstudy.models.dao.ICustomerDAO;
import com.dasetova.springstudy.models.dao.IProductDAO;
import com.dasetova.springstudy.models.entity.Bill;
import com.dasetova.springstudy.models.entity.Customer;
import com.dasetova.springstudy.models.entity.Product;

@Service
public class CustomerServiceImpl implements ICustomerService {

	@Autowired
	private ICustomerDAO customerDAO;
	
	@Autowired
	private IProductDAO productDAO;
	
	@Autowired
	private IBillDAO billDAO;
	
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

	@Override
	public Page<Customer> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return customerDAO.findAll(pageable);
	}

	@Override
	public List<Product> findByName(String term) {
		// TODO Auto-generated method stub
		return productDAO.findByNameLikeIgnoreCase("%" + term + "%");
	}

	@Override
	@Transactional
	public void saveBill(Bill bill) {
		// TODO Auto-generated method stub
		billDAO.save(bill);
	}

}
