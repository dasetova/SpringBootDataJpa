package com.dasetova.springstudy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dasetova.springstudy.models.dao.ICustomerDAO;

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
}
