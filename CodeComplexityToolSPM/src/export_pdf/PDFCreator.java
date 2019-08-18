/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package export_pdf;

/**
 *
 * @author melan
 */

import codecomplexitytoolspm.ProgramStatement;
import java.util.Date;
import java.util.List;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.util.ArrayList;
/**
 * This is to create a PDF file.
 */
public class PDFCreator {
    private final static String[] HEADER_ARRAY = {"Line Number", "Program Statement", "Cs Value","CtC Value", "CnC Value", "Ci Value","TW Value","Cps Value","Cr Value"};
    public final static Font SMALL_BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.BOLD);
    public final static Font NORMAL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.NORMAL);
    public static void addMetaData(Document document, String sqlXMLFileName) {
        document.addTitle("PDF Report");
        document.addSubject("Code Complexity");
        document.addAuthor("Melan Rashitha");
    }
    public static void addContent(Document document, ArrayList<ProgramStatement> dataObjList) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        paragraph.setFont(NORMAL_FONT);
        createReportTable(paragraph, dataObjList);
        document.add(paragraph);
    }
    private static void createReportTable(Paragraph paragraph, ArrayList<ProgramStatement> dataObjList)
    throws BadElementException {
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        paragraph.add(new Chunk("Report Table :- ", SMALL_BOLD));
        if(null == dataObjList){
            paragraph.add(new Chunk("No data to display."));
            return;
        }
        addHeaderInTable(HEADER_ARRAY, table);
        int count = 1;
        for(ProgramStatement dataObject : dataObjList){
            addToTable(table, String.valueOf(count)+".");
            addToTable(table, String.valueOf(dataObject.getLineNumber()));
            addToTable(table, dataObject.getLineContent());
            addToTable(table, String.valueOf(dataObject.getCncValue()));
            count++;
        }
        paragraph.add(table);
    }
    /** Helper methods start here **/  
    public static void addTitlePage(Document document, String title) throws DocumentException {
        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 3);
        preface.add(new Phrase("Test Report: ", NORMAL_FONT));
        preface.add(new Phrase(title, PDFCreator.NORMAL_FONT));
        addEmptyLine(preface, 1);
        preface.add(new Phrase("Date: ", PDFCreator.SMALL_BOLD));
        preface.add(new Phrase(new Date().toString(), PDFCreator.NORMAL_FONT));
        addEmptyLine(preface, 1);
        preface.add(new Phrase("Report generated by: ", PDFCreator.SMALL_BOLD));
        preface.add(new Phrase("Melan Rashitha Dias", PDFCreator.NORMAL_FONT));
        addEmptyLine(preface, 2);
        preface.add(new Phrase("This is basically a sample report.", PDFCreator.NORMAL_FONT));
        document.addSubject("PDF : " + title);
        preface.setAlignment(Element.ALIGN_CENTER);
        document.add(preface);
        document.newPage();
    }
    public static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    public static void addHeaderInTable(String[] headerArray, PdfPTable table){
        PdfPCell c1 = null;
        for(String header : headerArray) {
            c1 = new PdfPCell(new Phrase(header, PDFCreator.SMALL_BOLD));
            c1.setBackgroundColor(BaseColor.GREEN);
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
        }
        table.setHeaderRows(1);
    }
    public static void addToTable(PdfPTable table, String data){        
        table.addCell(new Phrase(data, PDFCreator.NORMAL_FONT));
    }
    public static Paragraph getParagraph(){        
        Paragraph paragraph = new Paragraph();
        paragraph.setFont(PDFCreator.NORMAL_FONT);
        addEmptyLine(paragraph, 1);
        return paragraph;
    }
}