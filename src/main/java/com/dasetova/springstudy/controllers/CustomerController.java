package com.dasetova.springstudy.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dasetova.springstudy.models.service.ICustomerService;
import com.dasetova.springstudy.models.service.IUploadFileService;
import com.dasetova.springstudy.util.paginator.PageRender;
import com.dasetova.springstudy.models.entity.Customer;

@Controller
@SessionAttributes("customer") // Good practice to unused the hidden id
public class CustomerController {

	@Autowired
	private ICustomerService customerService;

	@Autowired
	private IUploadFileService uploadFileService;

	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> showPhoto(@PathVariable String filename) {
		Resource resource = null;
		try {
			resource = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = new PageRequest(page, 5);

		Page<Customer> customers = customerService.findAll(pageRequest);
		PageRender<Customer> pageRender = new PageRender<>("/list", customers);
		model.addAttribute("title", "Customers list");
		model.addAttribute("customers", customers);
		model.addAttribute("page", pageRender);
		return "list";
	}

	@GetMapping(value = "show/{id}")
	public String showCustomer(@PathVariable("id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Customer customer = this.customerService.findOne(id);
		if (customer == null) {
			flash.addFlashAttribute("error", "Customer doesnt exists");
			return "redirect:/list";
		}
		model.put("customer", customer);
		model.put("title", "Show Customer " + customer.getName());
		return "show";
	}

	@RequestMapping(value = "/form")
	public String newCustomer(Map<String, Object> model) {
		Customer customer = new Customer();
		model.put("title", "Customer Form");
		model.put("customer", customer);
		return "form";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Valid Customer customer, BindingResult result, Model model,
			@RequestParam("file") MultipartFile photo, RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("title", "Customer Form");
			return "form";
		}

		if (!photo.isEmpty()) {
			if (customer.getId() != null && customer.getId() > 0 && customer.getPhoto() != null
					&& customer.getPhoto().length() > 0) {
				this.uploadFileService.delete(customer.getPhoto());
			}
			String uniqueFilename = null;
			try {
				uniqueFilename = this.uploadFileService.copy(photo);
				flash.addFlashAttribute("info", "Photo upload success " + uniqueFilename);
				customer.setPhoto(uniqueFilename);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String msg = (customer.getId() != null ? "Customer updated" : "Customer created successfully");

		customerService.save(customer);
		status.setComplete(); // Good Practice to unused the hidden id
		flash.addFlashAttribute("success", msg);
		return "redirect:list";
	}

	@RequestMapping(value = "/form/{id}")
	public String editCustomer(@PathVariable(value = "id") Long id, Map<String, Object> model,
			RedirectAttributes flash) {
		Customer customer = null;
		if (id > 0) {
			customer = customerService.findOne(id);
			if (customer == null) {
				flash.addFlashAttribute("error", "Customer ID doesnt exists");
				return "redirect:/list";
			}
		} else {
			flash.addFlashAttribute("error", "Customer ID cant be zero");

		}
		model.put("title", "Customer Edit Form");
		model.put("customer", customer);

		return "form";
	}

	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			Customer customer = this.customerService.findOne(id);
			customerService.delete(id);
			flash.addFlashAttribute("success", "Customer deleted successfully");

			if (this.uploadFileService.delete(customer.getPhoto())) {
				flash.addFlashAttribute("info", "Photo " + customer.getPhoto() + " deleted");
			}
		}
		return "redirect:/list";
	}
}
