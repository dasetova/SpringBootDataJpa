package com.dasetova.springstudy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dasetova.springstudy.models.entity.Customer;
import com.dasetova.springstudy.models.service.ICustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {
	
	@Autowired
	private ICustomerService customerService;
	
	@GetMapping(value = {"/"})
	public List<Customer> index() {
		return customerService.findAll();
	}
}
