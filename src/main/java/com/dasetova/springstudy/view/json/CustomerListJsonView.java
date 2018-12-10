package com.dasetova.springstudy.view.json;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.dasetova.springstudy.models.entity.Customer;

@Component("list.json")
public class CustomerListJsonView extends MappingJackson2JsonView{

	@Override
	protected Object filterModel(Map<String, Object> model) {
		// TODO Auto-generated method stub
		model.remove("title");
		model.remove("page");
		@SuppressWarnings("unchecked")
		Page<Customer> customers = (Page<Customer>) model.get("customers");
		model.remove("customers");
		model.put("customers", customers.getContent());
		return super.filterModel(model);
	}
 
}
