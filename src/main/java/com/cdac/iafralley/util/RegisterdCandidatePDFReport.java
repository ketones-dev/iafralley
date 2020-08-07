package com.cdac.iafralley.util;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import com.cdac.iafralley.controllers.RalleyRegistrationFormController;
import com.cdac.iafralley.entity.RalleyCandidateDetails;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@PropertySource({"classpath:applicantfilepath.properties"})
public class RegisterdCandidatePDFReport
{
	
	public static String convertDate(Date d)
	{
		Format formatter = new SimpleDateFormat("dd-MM-yyyy");
		String s = formatter.format(d);
		return s;
	}
	
	public static String convertTimeStampDate(Date d)
	{
		Date date=new Date(d.getTime());
		Format formatter = new SimpleDateFormat("HH:mm");
		String s = formatter.format(date);
		return s;
	}
	
	
	
	
public static void createPDF(RalleyCandidateDetails candidate, String FILE_PATH) {
    try
    {
    	Files.createDirectories(Paths.get("/IAFRalleyReport"));
    	System.out.println(FILE_PATH+candidate.getRalleyregistrationNo()+".pdf");
    	
    	Document document = new Document(PageSize.A4);
    	document.setMargins(50, 50, 100, 70); 
       PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE_PATH+candidate.getRalleyregistrationNo()+".pdf"));
       document.open();
       
       Rectangle rect= new Rectangle(577,825,18,15); // you can resize rectangle 
       rect.enableBorderSide(1);
       rect.enableBorderSide(2);
       rect.enableBorderSide(4);
       rect.enableBorderSide(8);
       rect.setBorderColor(BaseColor.BLACK);
       rect.setBorderWidth(1);
       document.add(rect);
       
		//Inserting Image in PDF

	     Image image1 = Image.getInstance ("src/main/resources/images/badge.png");
	     image1.scaleAbsolute(50f, 50f);//image width,height	
	     image1.setAlignment(Image.LEFT);
	     
	     Image image2 = Image.getInstance ("src/main/resources/images/index.png");
	     image2.scaleAbsolute(50f, 50f);//image width,height	
	     image2.setAlignment(image2.RIGHT);
	     
	     float[] columnWidths = {2, 5, 2};
	     PdfPTable table2=new PdfPTable(columnWidths);
	     table2.setWidthPercentage(100f);
	     PdfPCell leftCell = new PdfPCell (image1);
	     leftCell.setHorizontalAlignment(Element.ALIGN_CENTER);
	     
	     table2.addCell(leftCell);

	     Font myFonColor = FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, BaseColor.WHITE);
	     PdfPCell middleCell = new PdfPCell(new Paragraph("IAF Ralley Registration",myFonColor));
	     middleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
	     middleCell.setPaddingTop(15.0f);
	     middleCell.setBackgroundColor(new BaseColor (135,206,250));
	     table2.addCell(middleCell);
	     PdfPCell rightCell = new PdfPCell (image2);
	     rightCell.setHorizontalAlignment(Element.ALIGN_CENTER);
	     
	     table2.addCell(rightCell);
	     
	     

	//Inserting Table in PDF
	     float[] columnWidths2 = {2, 5, 2,5};
	     PdfPTable table=new PdfPTable(columnWidths2);
	     table.setWidthPercentage(100f);
	     
	     
	     PdfPCell cell = new PdfPCell (new Paragraph ("RALLEY REGISTRATION NUMBER:"));
	     PdfPCell regno = new PdfPCell (new Paragraph (candidate.getRalleyregistrationNo()));
	     cell.setColspan (2);
	     regno.setColspan(2);
	      cell.setHorizontalAlignment (Element.ALIGN_LEFT);
	      cell.setPadding (8.0f);
	      regno.setHorizontalAlignment (Element.ALIGN_CENTER);
	      regno.setPadding (8.0f);
	     
	     
               PdfPCell cell1 = new PdfPCell (new Paragraph ("PERSONAL DETAIL"));
		      cell1.setColspan (4);
		      cell1.setHorizontalAlignment (Element.ALIGN_LEFT);
		      cell1.setPadding (8.0f);
		      cell1.setBackgroundColor (new BaseColor (135,206,250));
		      
		      PdfPCell cell2 = new PdfPCell (new Paragraph ("Qualification DETAIL"));

		      cell2.setColspan (4);
		      cell2.setHorizontalAlignment (Element.ALIGN_LEFT);
		      cell2.setPadding (8.0f);
		      cell2.setBackgroundColor (new BaseColor (135,206,250));
		      
		      PdfPCell cell3 = new PdfPCell (new Paragraph ("VENU DETAIL"));

		      cell3.setColspan (4);
		      cell3.setHorizontalAlignment (Element.ALIGN_LEFT);
		      cell3.setPadding (8.0f);
		      cell3.setBackgroundColor (new BaseColor (135,206,250));
		      
		      table.addCell(cell);
		      table.addCell(regno);
		      
		      
		      table.addCell(cell1);
		      





		      						               

			table.addCell("Name:");
			table.addCell(candidate.getName());
			table.addCell("Father Name:");
			table.addCell(candidate.getFathername());
			table.addCell("Email id:");
			table.addCell(candidate.getEmailid());
			table.addCell("Contact no:");
			table.addCell(candidate.getContactno());
			table.addCell("DOB:");
			table.addCell(convertDate(candidate.getDateOfBirth()));
			table.addCell("Maritial Status");
			Boolean status=candidate.isMaritial_status();
			table.addCell(status.toString());
			table.addCell("Height:");
			table.addCell(candidate.getHeight());
			table.addCell("state");
			
			  table.addCell(candidate.getState());
			
			  table.addCell("city");
				
		      table.addCell(candidate.getCity());
		      table.addCell("");table.addCell("");
		      
		      table.addCell(cell2);
		      
			table.addCell("Degree:");
			table.addCell(candidate.getPassed_exam_detail());
			table.addCell("Degree Details:");
			table.addCell(candidate.getOtherDetailPassedDetail());
			table.addCell("Passing Percentage:");
			table.addCell(candidate.getPassed_exam_percentage().toString());
			table.addCell("English Percentage:");
			table.addCell(candidate.getEnglish_percentage().toString());
		      
		      
		      
		      
		      table.addCell(cell3);
		      
		      table.addCell("Date of Reporting");
		      table.addCell(convertDate(candidate.getDateTimeOfReporting()));
		      table.addCell("Time of Reporting");
		      
		      table.addCell(convertTimeStampDate(candidate.getDateTimeOfReporting()));
		      PdfPCell venucell=new PdfPCell(new Phrase("Venu Detail"));
		      venucell.setColspan(4);
		      venucell.setRowspan(2);
		      table.addCell(venucell);
		      
		      
		      
		      
		      
		      
		      
		      
		      
		      table.setSpacingBefore(30.0f);       // Space Before table starts, like margin-top in CSS
		      table.setSpacingAfter(30.0f);        // Space After table starts, like margin-Bottom in CSS								          
		      
		      table2.setSpacingBefore(60.0f);       // Space Before table starts, like margin-top in CSS
		      							          
		      
		      document.add(table2);
		      document.add(table);

	 
       
       document.close();
       writer.close();
    }
    catch(Exception ex){
    	ex.printStackTrace();
    	
    }
}

}


