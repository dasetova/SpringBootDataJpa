package com.dasetova.springstudy.models.dao;

//Replacing CrudRepository -> PagingAndSortingRepository = CrudRepository + Pagination
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.dasetova.springstudy.models.entity.Customer;

public interface ICustomerDAO extends PagingAndSortingRepository<Customer, Long>{

}
