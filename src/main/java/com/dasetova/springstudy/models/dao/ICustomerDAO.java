package com.dasetova.springstudy.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.dasetova.springstudy.models.entity.Customer;

public interface ICustomerDAO extends CrudRepository<Customer, Long>{

}
