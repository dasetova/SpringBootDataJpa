package com.dasetova.springstudy.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.dasetova.springstudy.models.service.ICustomerService;
import com.dasetova.springstudy.models.entity.Customer;

@Controller
@SessionAttributes("customer") // Good practice to unused the hidden id
public class CustomerController {
	
	@Autowired
	private ICustomerService customerService;

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("title", "Customers list");
		model.addAttribute("customers", customerService.findAll());
		return "list";
	}
	
	@RequestMapping(value="/form")
	public String newCustomer(Map<String, Object> model) {
		Customer customer = new Customer();
		model.put("title", "Customer Form");
		model.put("customer", customer);
		return "form";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@Valid Customer customer, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("title", "Customer Form");
			return "form";
		}
		customerService.save(customer);
		return "redirect:list";
	}
	
	@RequestMapping(value="/form/{id}")
	public String editCustomer(@PathVariable(value="id") Long id,Map<String, Object> model, SessionStatus status) {
		Customer customer = null;
		if (id > 0) {
			customer = customerService.findOne(id);
		}else {
			return "redirect:/list";
		}
		model.put("title", "Customer Edit Form");
		model.put("customer", customer);
		status.setComplete(); //Good Practice to unused the hidden id
		return "form";
	}
	
	@RequestMapping(value="/delete/{id}")
	public String delete(@PathVariable(value="id") Long id) {
		if (id > 0) {
			customerService.delete(id);
		}
		return "redirect:/list";
	}
}
