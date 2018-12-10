package com.dasetova.springstudy.view.pdf;

import java.awt.Color;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.dasetova.springstudy.models.entity.Bill;
import com.dasetova.springstudy.models.entity.BillItem;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("bill/show")
public class BillPdfView extends AbstractPdfView{

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Bill bill = (Bill) model.get("bill");
		
		PdfPTable table = new PdfPTable(1);
		table.setSpacingAfter(20);
		
		PdfPCell cell = new PdfPCell(new Phrase("Customer Information"));
		cell.setBackgroundColor(new Color(184, 218, 255));
		cell.setPadding(8f);
		table.addCell(cell);
		
		
		table.addCell(bill.getCustomer().getName() + " " + bill.getCustomer().getLastname());
		table.addCell(bill.getCustomer().getEmail());
		
		PdfPTable table2 = new PdfPTable(1);
		table2.setSpacingAfter(20);
		
		cell = new PdfPCell(new Phrase("Bill Information"));
		cell.setBackgroundColor(new Color(195, 230, 203));
		cell.setPadding(8f);
		
		table2.addCell(cell);
		table2.addCell("ID:" + bill.getId());
		table2.addCell("Description:" + bill.getDescription());
		table2.addCell("Create at:" + bill.getCreateAt());
		
		PdfPTable table3 = new PdfPTable(4);
		table3.setWidths(new float[] {3.5f, 1, 1, 1});
		table3.addCell("Product");
		table3.addCell("Price");
		table3.addCell("Quantity");
		table3.addCell("Total");
		
		for(BillItem item: bill.getBillItems()) {
			table3.addCell(item.getProduct().getName());
			table3.addCell(item.getProduct().getPrice().toString());
			
			cell = new PdfPCell(new Phrase(item.getQuantity().toString()));
			
			
			table3.addCell(cell);
			table3.addCell(item.calculateValue().toString());
		}
		
		cell = new PdfPCell(new Phrase("Total: "));
		cell.setColspan(3);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		
		table3.addCell(cell);
		table3.addCell(bill.getTotal().toString());
		
		document.add(table);
		document.add(table2);
		document.add(table3);
	}
	
}
