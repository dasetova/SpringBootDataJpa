package com.dasetova.springstudy.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dasetova.springstudy.models.entity.Bill;
import com.dasetova.springstudy.models.entity.Customer;
import com.dasetova.springstudy.models.entity.Product;
import com.dasetova.springstudy.models.service.ICustomerService;

@Controller
@RequestMapping("/bill")
@SessionAttributes("bill")
public class BillController {
	
	@Autowired
	private ICustomerService customerService;
	
	@GetMapping("/form/{customerId}")
	public String create(@PathVariable(value="customerId") Long customerId, Map<String, Object> model, RedirectAttributes flash) {
		Customer customer = customerService.findOne(customerId);
		if (customer == null) {
			flash.addFlashAttribute("error", "Customer doesnt exists");
			return "redirect:/list";
		}
		Bill bill = new Bill();
		bill.setCustomer(customer);
		
		model.put("bill", bill);
		model.put("title", "Create Bill");
		
		return "bill/form";
	}
	
	@GetMapping(value="/load-products/{term}", produces= {"application/json"})
	public @ResponseBody List<Product> loadProducts(@PathVariable(value="term") String term){
		return customerService.findByName(term);
	}
}
