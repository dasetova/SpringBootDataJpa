package com.dasetova.springstudy.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dasetova.springstudy.models.entity.Customer;

@Repository
public class CustomerDAOImpl implements ICustomerDAO{

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<Customer> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("from Customer").getResultList();
	}

	@Transactional
	@Override
	public void save(Customer customer) {
		em.persist(customer);
	}

}
