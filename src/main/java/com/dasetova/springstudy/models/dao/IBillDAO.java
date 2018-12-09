package com.dasetova.springstudy.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.dasetova.springstudy.models.entity.Bill;

public interface IBillDAO extends CrudRepository<Bill, Long>{

}
