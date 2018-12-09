package com.dasetova.springstudy.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dasetova.springstudy.models.entity.Bill;
import com.dasetova.springstudy.models.entity.BillItem;
import com.dasetova.springstudy.models.entity.Customer;
import com.dasetova.springstudy.models.entity.Product;
import com.dasetova.springstudy.models.service.ICustomerService;

@Controller
@RequestMapping("/bill")
@SessionAttributes("bill")
public class BillController {
	
	@Autowired
	private ICustomerService customerService;
	
	@GetMapping("/show/{id}")
	public String show(@PathVariable(value="id") Long id, Model model, RedirectAttributes flash) {
		Bill bill = customerService.findBillById(id);
		if(bill == null) {
			flash.addFlashAttribute("error", "The bill doesnt exists in database");
			return "redirect:/list";
		}
		model.addAttribute("bill", bill);
		model.addAttribute("title", "Bill: " .concat(bill.getDescription()));
		return "bill/show";
	}
	
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
	
	@PostMapping("/form")
	public String save(@Valid Bill bill, BindingResult result, @RequestParam(name="item_id[]", required=false) Long[] itemId,
			@RequestParam(name="quantity[]", required=false) Integer[] quantity,
			RedirectAttributes flash, SessionStatus status,
			
			Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute("title", "Create Bill");
			return "bill/form";
		}
		
		if(itemId == null || itemId.length == 0) {
			model.addAttribute("title", "Create Bill");
			model.addAttribute("error", "Must add at least one line to the bill");
			return "bill/form";
		}
		
		for(int i = 0; i < itemId.length; i++) {
			Product product = this.customerService.findProductById(itemId[i]);
			BillItem row = new BillItem();
			row.setQuantity(quantity[i]);
			row.setProduct(product);
			bill.addBillItem(row);
		}
		
		customerService.saveBill(bill);
		status.setComplete();
		flash.addFlashAttribute("success", "Bill created!");
		return "redirect:/show/" + bill.getCustomer().getId();
	}
	
	@GetMapping("delete/{id}")
	public String delete(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		Bill bill = this.customerService.findBillById(id);
		if (bill != null) {
			this.customerService.deleteBill(id);
			flash.addFlashAttribute("success", "Bill deleted");
			return "redirect:/show/" + bill.getCustomer().getId();
		}
		flash.addFlashAttribute("error", "Bill does not exists");
		return "redirect:/list";
	}
}
