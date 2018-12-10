package com.dasetova.springstudy.view.csv;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.dasetova.springstudy.models.entity.Customer;

@Component("list")
public class CustomerCsvView extends AbstractView{

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		response.setHeader("Content-Disposition", "attachment; filename=\"customers.csv\"");
		response.setContentType(getContentType());
		
		@SuppressWarnings("unchecked")
		Page<Customer> customers = (Page<Customer>) model.get("customers");
		ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		String [] header = {"id", "name", "lastname", "email", "createAt"};
		beanWriter.writeHeader(header);
		
		for(Customer customer : customers) {
			beanWriter.write(customer, header);
		}
		beanWriter.close();
	}

	public CustomerCsvView() {
		setContentType("text/csv");
	}

	@Override
	protected boolean generatesDownloadContent() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
