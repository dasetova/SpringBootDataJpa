package com.dasetova.springstudy.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.dasetova.springstudy.models.entity.Customer;

@Repository
public class CustomerDAOImpl implements ICustomerDAO{

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("from Customer").getResultList();
	}

	@Override
	public void save(Customer customer) {
		if (customer.getId() != null && customer.getId() != 0) {
			em.merge(customer);
		}else {
			em.persist(customer);
		}
	}

	@Override
	public Customer findOne(Long id) {
		// TODO Auto-generated method stub
		return em.find(Customer.class, id);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		em.remove(findOne(id));
	}

}
