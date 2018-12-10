package com.dasetova.springstudy.view.xls;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.dasetova.springstudy.models.entity.Bill;
import com.dasetova.springstudy.models.entity.BillItem;

@Component("bill/show.xlsx")
public class BillXlsView extends AbstractXlsxView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		response.setHeader("Content-Disposition", "attachment; filename=\"billView.xlsx\"");
		Bill bill = (Bill) model.get("bill");
		Sheet sheet = workbook.createSheet("Spring Bill Export");
		
		Row row = sheet.createRow(0);
		
		Cell cell = row.createCell(0);
		cell.setCellValue("Customer Information");
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue(bill.getCustomer().getName() + " " + bill.getCustomer().getLastname());
		
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue(bill.getCustomer().getEmail());
		
		sheet.createRow(4).createCell(0).setCellValue("Bill Information");
		sheet.createRow(5).createCell(0).setCellValue("ID: " + bill.getId());
		sheet.createRow(6).createCell(0).setCellValue("Description:" + bill.getDescription());
		sheet.createRow(7).createCell(0).setCellValue("Create At: " + bill.getCreateAt());
		
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setBorderBottom(BorderStyle.MEDIUM);
		headerStyle.setBorderTop(BorderStyle.MEDIUM);
		headerStyle.setBorderRight(BorderStyle.MEDIUM);
		headerStyle.setBorderLeft(BorderStyle.MEDIUM);
		headerStyle.setFillBackgroundColor(IndexedColors.GOLD.index);
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		CellStyle bodyStyle = workbook.createCellStyle();
		bodyStyle.setBorderBottom(BorderStyle.THIN);
		bodyStyle.setBorderTop(BorderStyle.THIN);
		bodyStyle.setBorderRight(BorderStyle.THIN);
		bodyStyle.setBorderLeft(BorderStyle.THIN);
		
		Row  header = sheet.createRow(9);
		header.createCell(0).setCellValue("Product");
		header.createCell(1).setCellValue("Price");
		header.createCell(2).setCellValue("Quantity");
		header.createCell(3).setCellValue("Total");
		
		header.getCell(0).setCellStyle(headerStyle);
		header.getCell(1).setCellStyle(headerStyle);
		header.getCell(2).setCellStyle(headerStyle);
		header.getCell(3).setCellStyle(headerStyle);
		
		int rownum = 10;
		for(BillItem item : bill.getBillItems()) {
			Row rowItem = sheet.createRow(rownum++);
			cell = rowItem.createCell(0);
			cell.setCellValue(item.getProduct().getName());
			cell.setCellStyle(bodyStyle);
			
			cell = rowItem.createCell(1);
			cell.setCellValue(item.getProduct().getPrice());
			cell.setCellStyle(bodyStyle);
			
			cell = rowItem.createCell(2);
			cell.setCellValue(item.getQuantity());
			cell.setCellStyle(bodyStyle);
			
			cell = rowItem.createCell(3);
			cell.setCellValue(item.calculateValue());
			cell.setCellStyle(bodyStyle);
		}
		Row total = sheet.createRow(rownum);
		total.createCell(2).setCellValue("Total:");
		total.createCell(3).setCellValue(bill.getTotal());
	}

}
