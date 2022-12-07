package com.brs.entities;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.brs.util.BatchStatusInterf;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class UserPDFExporter {
//	private List<Employee> listUsers;

//	public UserPDFExporter(List<Employee> listUsers) {
//		this.listUsers = listUsers;
//	}
	private List<BatchStatusInterf> jobstatuslist;
	public UserPDFExporter(List<BatchStatusInterf> jobstatuslist) {
		this.jobstatuslist=jobstatuslist;
	}
	
	

	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLACK);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("Bank Account Number", font));

		table.addCell(cell);

		cell.setPhrase(new Phrase("Vouchar Date", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Process Flag ", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Status", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Error Mesg", font));
		table.addCell(cell);
	}
	
	private void writeTableData(PdfPTable table) {
		for (BatchStatusInterf user : jobstatuslist) {
			table.addCell(String.valueOf(user.getBank_account_no()));
			table.addCell(user.getVoucherdate());
			table.addCell(user.getProcess_flag());
			table.addCell(user.getStatus());
			table.addCell(user.getError_mesg());
			
		}

//	private void writeTableData(PdfPTable table) {
//		for (Employee user : listUsers) {
//			table.addCell(String.valueOf(user.getId()));
//			table.addCell(user.getName());
//			table.addCell(user.getEmail());
//			table.addCell(user.getDepartment());
//		}
	}

	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLACK);

		Paragraph p = new Paragraph("List of Job Status", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(p);

		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1.5f, 3.5f, 3.0f, 3.0f, 3.5f });
		table.setSpacingBefore(10);

		writeTableHeader(table);
		writeTableData(table);

		document.add(table);

		document.close();

	}
}