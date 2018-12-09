package com.dasetova.springstudy.models.dao;

import org.springframework.data.jpa.repository.Query;
//Replacing CrudRepository -> PagingAndSortingRepository = CrudRepository + Pagination
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.dasetova.springstudy.models.entity.Customer;

public interface ICustomerDAO extends PagingAndSortingRepository<Customer, Long>{

	@Query("select c "
			+ "from Customer c "
			+ "left join fetch c.bills b "
			+ "where c.id = ?1")
	public Customer fetchByIdWithBills(Long id);
}
