package com.dasetova.springstudy.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dasetova.springstudy.models.dao.ICustomerDAO;
import com.dasetova.springstudy.models.entity.Customer;

@Controller
public class CustomerController {
	
	@Autowired
	private ICustomerDAO customerDAO;

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("title", "Customers list");
		model.addAttribute("customers", customerDAO.findAll());
		return "list";
	}
	
	@RequestMapping(value="/new")
	public String newCustomer(Map<String, Object> model) {
		Customer customer = new Customer();
		model.put("title", "Customer Form");
		model.put("customer", customer);
		return "new";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(Customer customer) {
		System.out.println("Here...");
		customerDAO.save(customer);
		return "redirect:list";
	}
}
