package com.dasetova.springstudy.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.dasetova.springstudy.models.entity.User;

public interface IUserDAO extends CrudRepository<User, Long>{

	public User findByUsername(String username);
}
