package com.efo.forms;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.efo.entity.MarketPlaceVendors;
import com.efo.entity.ShoppingCart;
import com.efo.entity.ShoppingCartItems;
import com.efo.pdf.PdfUtilities;
import com.efo.service.UserService;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

@Component
public class PrintMarketPlaceSalesPDF {
	@Value("${efo.documentsPath}")
	private String targetFile;
	
	private PdfFont font = null;
	private PdfFont boldFont = null;
		
	@Autowired
	PdfUtilities pdfUtilities;
		
	public String print(MarketPlaceVendors vendor, String name) throws IOException {

		font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
		boldFont = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
		final String pattern = "yyyyMMddHHmmssSSS";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		final float[] widths = {60f, 220f,20f,50f,50f, 50f};
		final String[] labels = {"prodcut ID","Product Name","Qty","Total Sales","EFO Commission", "Tax"};
		final String labelColor = "F7F9B9";
		
		PdfDocument pdfDoc = pdfUtilities.createForm(targetFile + "sr" + date + ".pdf");
		Document doc = pdfUtilities.createLayout(pdfDoc, "A4");
		pdfUtilities.addImage(doc, 0, 770, "efologo.png");
		doc.add(new Paragraph("\n\n"));

		pdfUtilities.centerText(doc, "MarketPlace Sales", 18, boldFont);
		pdfUtilities.centerText(doc, "Vendor: " + name, 12, boldFont);

		doc.add(new Paragraph("\n\n"));
		
		Table table = pdfUtilities.addTable(widths, 0);
		
		
		pdfUtilities.addLabels(table, labels, labelColor, 8, boldFont);
		
		populateTable(doc, table, vendor);
		
		
		doc.add(table);
		doc.close();
		
		return "sr" + date + ".pdf";
	}
	
	private Table populateTable(Document doc, Table table, MarketPlaceVendors data) {
		double total_price = 0.0;
		double total_tax = 0.0;
		String[] row = new String[7];
		final String[] justify = {"L", "L", "C", "R", "R", "R", "R"};
		final String oddRowColor = "D4FFFC";
		final String evenRowColor = "FFFFFF";
		final String footerColor = "CED4F6";
		int rowNum = 1;
		
			
			if (rowNum % 2 == 1) {
				pdfUtilities.addRow(table, row, oddRowColor, justify, 8, font);
			}else{
				pdfUtilities.addRow(table, row, evenRowColor, justify, 8, font);
			}
			
		row[0] = "Total Price---->";
		row[1] = " ";
		row[2] = " ";
		row[3] = " ";
		row[4] = " ";
		row[5] = String.format("%.2f", total_price);
		row[6] = " ";
		pdfUtilities.addRow(table, row, footerColor, justify, 8, font);
		
		row[0] = "Total Tax------>";
		row[1] = " ";
		row[2] = " ";
		row[3] = " ";
		row[4] = " ";
		row[5] = " ";
		row[6] = String.format("%.2f", total_tax);
		pdfUtilities.addRow(table, row, footerColor, justify, 8, font);

		return table;
	}

}
