package com.efo.forms;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.efo.entity.ShoppingCart;
import com.efo.entity.ShoppingCartItems;
import com.efo.pdf.PdfUtilities;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

@Component
public class PrintSalesReceiptPDF {
	@Value("${efo.documentsPath}")
	private String targetFile;
	
	private PdfFont font = null;
	private PdfFont boldFont = null;
		
	@Autowired
	PdfUtilities pdfUtilities;
	
	
	public void print(ShoppingCart shoppingCart) throws IOException {

		font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
		boldFont = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
		final String pattern = "yyyyMMddHHmmssSSS";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		final float[] widths = {80f, 90f,70f,70f,70f, 70f};
		final String[] labels = {"prodcut ID","Product Name","Qty","Original Price","Discount", "Price", "Tax"};
		final String labelColor = "F7F9B9";
		
		PdfDocument pdfDoc = pdfUtilities.createForm(targetFile + "sr" + date + ".pdf");
		Document doc = pdfUtilities.createLayout(pdfDoc, "A4");
		pdfUtilities.centerText(doc, "Sales Receipt", 18, boldFont);
		pdfUtilities.centerText(doc, "Invoce # " + shoppingCart.getReference(), 12, boldFont);

		pdfUtilities.addImage(doc, 0, 800, "efologo.png");
		doc.add(new Paragraph("\n\n"));
		
		Table table = pdfUtilities.addTable(widths, 0);
		
		
		pdfUtilities.addLabels(table, labels, labelColor, 8, boldFont);
		
		populateTable(doc, table, new ArrayList<ShoppingCartItems>(shoppingCart.getShoppingCartItems()));
		
		
		doc.add(table);
		doc.close();
	}
	
	private Table populateTable(Document doc, Table table, List<ShoppingCartItems> data) {
		double total_price = 0.0;
		double total_tax = 0.0;
		String[] row = new String[7];
		final String[] justify = {"L", "L", "R", "R", "R", "R"};
		final String oddRowColor = "D4FFFC";
		final String evenRowColor = "FFFFFF";
		int rowNum = 1;
		
		for (ShoppingCartItems item : data) {
			row[0] = item.getProduct_id();
			row[1] = item.getProduct_name();
			row[2] = String.format("%d", item.getQty());
			row[2] = String.format("%.2f", item.getProduct_price());
			row[3] = String.format("%.2f", item.getProduct_discount());
			row[4] = String.format("%.2f", (item.getProduct_price() - item.getProduct_discount()));
			row[6] = String.format("%.2f", item.getProduct_tax());
			
			if (rowNum % 2 == 1) {
				pdfUtilities.addRow(table, row, oddRowColor, justify, 8, font);
			}else{
				pdfUtilities.addRow(table, row, evenRowColor, justify, 8, font);
			}
			
			rowNum++;
			total_price += (item.getProduct_price() * item.getQty()) - item.getProduct_discount();
			total_tax += item.getProduct_tax();
		}
		row[0] = " ";
		row[1] = " ";
		row[2] = " ";
		row[3] = " ";
		row[4] = "Total Price->";
		row[5] = String.format("%.2f", total_price);
		row[6] = " ";
		pdfUtilities.addRow(table, row, evenRowColor, justify, 8, font);
		
		row[0] = " ";
		row[1] = " ";
		row[2] = " ";
		row[3] = " ";
		row[4] = " ";
		row[5] = "Total Tax->";
		row[6] = String.format("%.2f", total_tax);
		pdfUtilities.addRow(table, row, evenRowColor, justify, 8, font);

		return table;
	}

}
