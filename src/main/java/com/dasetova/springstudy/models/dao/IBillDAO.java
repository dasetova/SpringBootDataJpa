package com.dasetova.springstudy.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dasetova.springstudy.models.entity.Bill;

public interface IBillDAO extends CrudRepository<Bill, Long>{

	@Query("select b "
			+ "from Bill  b  "
			+ "join fetch b.customer c "
			+ "join fetch b.billItems bi "
			+ "join fetch bi.product "
			+ "where b.id=?1")
	public Bill fetchByIdWithCustomerWithBillItemWithProduct(Long id);
}
