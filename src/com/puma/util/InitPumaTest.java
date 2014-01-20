package com.puma.util;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class InitPumaTest {

	
	//for some reason column B aka 1 is not readable??? but if i replace it manually - then it is readable??
	private static SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM");

	private static final String[] titles = {
		"FNAME", " LNAME", "PHONE", "EMAIL", "ZIP", "ADDRESS1", "ADDRESS2", "STREET_NUM", "CITY","COUNTRY" };

	//sample data to fill the sheet.
	private static final String[][] data = {
		//Belgium De Brouckèreplein
		{"fnameBrussel", "regLname", "9255431122", "test@yahoo.com", "1000", "De Brouckereplein", null, "31", "Brussel", "Belgique"},
		//Czech Rep
		{"fnamePrague", "regLname", "420-2-2484-1111", "test@yahoo.com", "186 00", "POBREZNI", null, "1", "PRAGUE", "Czech Republic"},

		//Toldbodgade 24- 28, Copenhagen 1253, Denmark  011 45 33 74 14 14
		{"fnameSCopenhagen", "regLname", "011 45 33 74 14 14", "test@yahoo.com", "1253", "Toldbodgade", null, "24-28", "Copenhagen", "Denmark"},

		//Swissotel Tallinn  Tornimae 3, Tallinn 10145, Estonia
		{"fnameTalinn", "regLname", "442-453-3741", "test@sbcglobal.net", "10145", "Tornimae", null, "3", "Tallinn", "Estonia"},

		//Hotel Espana Carrer Sant Pau 9-11, 08001 Barcelona, Spain
		{"fnameSBarsa", "regLname", "4424533749", "test@hotmail.com", "08001", "Carrer Sant Pau", null, "9-11", "Barcelona", "Espana"},

		//10 Syngrou Avenue, Athens 11742, Greece
		{"fnameAthens", "regLname", "442 453 3741", "test@gmail.com", "11742", "Syngrou Avenue", null, "10", "Athens", "Greece"},

		//Via Tommaso Grossi 1, 20121 Milan, Italy
		{"fnameMilan", "regLname", "442 453 3741", "test@yahoo.com", "20121", "Via Tommaso Grossi", null, "1", "Milan", "Italy"},

		//Veca Ostmala 40, Liepaja 3401, Latvia 
		{"fnameRegLatvia", "regLname", "4424533748", "test@msn.com", "3401", "Veca Ostmala", null, "40", "Liepaja", "Latvia"},

		////Grand Rue, 9, Clervaux 9710, Luxembourg
		{"fnameRegLuxemburg", "regLname", "4424533748", "test@latvia.com", "9710", "Grand Rue", null, "9", "Clervaux", "Luxembourg"},

		//  Erzsebet korut 8, Budapest 1073, Hungary
		{"fnameRegHungary", "regLname", "4424533748", "testHungary@gmail.com", "1073", "Erzsebet korut", null, "8", "Budapest", "Hungary"},

		//	            Amstel 144, Amsterdam 1017 AE, The Netherlands SPECIAL ZIP!
		{"fnameRegNetherlands", "regLname", "4424533738", "test_amsterdam@gmail.com", "1017 AE", "Amstel", null, "144", "Amsterdam", "Netherlands"},

		// Aleje Jerozolimskie 45, Warsaw 00-692 , Poland SPECIAL ZIP!
		{"fnameRegPoland", "regLname", "4424533738", "test_poland@gmail.com", "00-692", "Aleje Jerozolimskie", null, "45", "Warsaw", "Poland"},

		//Rua Padre Manuel da Nobrega 111, Porto 4350-226 , Portugal SPECIAL ZIP!
		{"fnameRegPortugal", "regLname", "4524533738", "test_poRtugal@gmail.com", "4350-226", "Rua Padre Manuel da Nobrega", null, "111", "Porto", "Portugal"},

		//Hlavna 1, Kosice 040 01, Slovakia
		{"fnameRegSlovakia", "regLname", "4524533738", "test_slovakia@gmail.com", "040 01", "Hlavna", null, "1", "Kosice","Slovakia"},

		// Slovenska Cesta 15, Ljubljana 1000, Slovenia 
		{"fnameRegSlovenia", "regLname", "4524533738", "testSlovenia@gmail.com", "1000", "Slovenska Cesta", null, "15", "Ljubljana", "Slovenia"},

		//Pohjoisesplanadi 29, Helsinki 00100, Finland SPECIAL ZIP!
		{"fnameRegFinland", "regLname", "4524533738", "testFinland@hotmail.com", "00100", "Pohjoisesplanadi", null, "29", "Helsinki", "Finland"},

		//Nils Ericssons Plan 4, Stockholm 11164, Sweden
		{"fnameRegSweden", "regLname", "4524532738", "testSweden@hotmail.com", "11164", "Nils Ericssons Plan", null, "4", "Stockholm", "Sweden"},
/*==============BEGINING OF BIG 5 COUNTRIES======================== */
		
		//Hofwisenstrasse 30, Rümlang 8153, Switzerland tel: 0871-423-4917 SPECIAL CHAR IN CITY and special phone!
		{"fnameRegSwitzerland", "regLname", "0871-423-4917", "testSweden@hotmail.com", "8153", "Hofwisenstrasse", null, "30", "Rümlang", "Switzerland"},
		
		//Grüner Weg 6 , 61169 Friedberg  GERMANY SPECIAL CHAR IN STREET NAME       
		{"fnameRegGermany", "regLname", "087423.4917", "testGermany@hotmail.com", "61169", "Grüner Weg", null, "6", "Friedberg", "Germany"},
		
		//Germany PO Box: P.O. Box 45 05 33, 80905 Munich, Germany Tel. +49-89-3548-040		
		{"fnameRegGermany", "regPOBox", "0874234917", "testGermany@hotmail.com", "80905", "P.O. Box ", null, "45 05 33", "Munich", "Germany"},

		//The Soho Hotel	4 Richmond Mews, London W1D 3DH, United Kingdom SPECIAL ZIP
		{"fnameRegUK", "regLname", "0874234917", "testUK@hotmail.com", "W1D 3DH", "Richmond Mews ", null, "4", "London", "United Kingdom "},


	//	PO Box 1970. Liverpool L75 1WX SPECIAL ZIP+ PO BOX
		{"fnameRegUK", "regPOBox", "0874234917", "testUK@hotmail.com", "L75 1WX", "Po Box ", null, "1970", "Liverpool", "United Kingdom "},
		
		//23 Montpelier Row Apt C London SE3 0RL, UK Special zip+apt num
		{"fnameRegUK", "regLname", "0874234917", "testUK@hotmail.com", "SE3 0RL", "Montpelier Row", "C", "23", "London", "United Kingdom "},


		//Am Fleischmarkt 20, Vienna 1010, Austria (Innere Stadt)  tel: 011 43 1 515230
		{"fnameRegAustria", "regLname", "0874234917", "testAustria@hotmail.com", "1010", "Fleischmarkt ", null, "20", "Vienna", "Austria"},
		
		//31 Avenue George V, 75008 Paris, France
		{"fnameRegFrance", "regLname", "0874234917", "testparis@hotmail.com", "75008", "Avenue George V", null, "31", "Paris", "France"},

		
	};

	public static void main(String[] args) throws Exception {
		Workbook wb;

		if(args.length > 0 && args[0].equals("-xls")) wb = new HSSFWorkbook();
		else wb = new XSSFWorkbook();

		Map<String, CellStyle> styles = createStyles(wb);
		Sheet sheet = wb.createSheet("TestData");

		//turn off gridlines
		sheet.setDisplayGridlines(true);
		sheet.setPrintGridlines(true);
		sheet.setFitToPage(true);
		sheet.setHorizontallyCenter(true);
		PrintSetup printSetup = sheet.getPrintSetup();
		printSetup.setLandscape(true);

		//the following three statements are required only for HSSF
		sheet.setAutobreaks(true);
		printSetup.setFitHeight((short)1);
		printSetup.setFitWidth((short)1);

		//the header row: centered text in 48pt font
		Row headerRow = sheet.createRow(0);
		headerRow.setHeightInPoints(12.75f);
		for (int i = 0; i < titles.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(titles[i]);
			cell.setCellStyle(styles.get("header"));
		}

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		/*columns for 11 weeks starting from 9-Jul
		 * 
	        calendar.setTime(fmt.parse("9-Jul"));
	        calendar.set(Calendar.YEAR, year);
	        for (int i = 0; i < 11; i++) {
	            Cell cell = headerRow.createCell(titles.length + i);
	            cell.setCellValue(calendar);
	            cell.setCellStyle(styles.get("header_date"));
	            calendar.roll(Calendar.WEEK_OF_YEAR, true);
	        }
		 */
		//freeze the first row
		//sheet.createFreezePane(0, 1);

		Row row;
		Cell cell;
		int rownum = 1;
		for (int i = 0; i < data.length; i++, rownum++) {
			row = sheet.createRow(rownum);
			if(data[i] == null) continue;

			for (int j = 0; j < data[i].length; j++) {
				cell = row.createCell(j);
				String styleName;
			//	boolean isHeader = i == 0 || data[i-1] == null;
				
								switch(j){
				case 0:
					if(row==headerRow) {
						styleName = "cell_b";
						cell.setCellValue(data[i][j]);
						//     cell.setCellValue(Double.parseDouble(data[i][j]));
					} else {
						styleName = "cell_normal";
						cell.setCellValue(data[i][j]);
					}
					break;

					/*
					 * 
					 * int count = isHere ? getHereCount(index) : getAwayCount(index);
                       it's a shorthand form of
                                 int count;
                                 if (isHere)
                                 count = getHereCount(index);
                                 else          
                                 count = getAwayCount(index);
	                    case 1:
	                        if(isHeader) {
	                            styleName = i == 0 ? "cell_h" : "cell_bb";
	                        } else {
	                            styleName = "cell_indented";
	                        }
	                        cell.setCellValue(data[i][j]);
	                        break;
	                    case 2:
	                        styleName = isHeader ? "cell_b" : "cell_normal";
	                        cell.setCellValue(data[i][j]);
	                        break;


	                    case 3:
	                        styleName = isHeader ? "cell_b_centered" : "cell_normal_centered";
	                        cell.setCellValue(Integer.parseInt(data[i][j]));
	                        break;
	                    case 4: {
	                        calendar.setTime(fmt.parse(data[i][j]));
	                        calendar.set(Calendar.YEAR, year);
	                        cell.setCellValue(calendar);
	                        styleName = isHeader ? "cell_b_date" : "cell_normal_date";
	                        break;
	                    }
	                    case 5: {
	                        int r = rownum + 1;
	                        String fmla = "IF(AND(D"+r+",E"+r+"),E"+r+"+D"+r+",\"\")";
	                        cell.setCellFormula(fmla);
	                        styleName = isHeader ? "cell_bg" : "cell_g";
	                        break;
	                    }
					 */
				default:
					styleName = "cell_normal";
					cell.setCellValue(data[i][j]);

				}

				cell.setCellStyle(styles.get(styleName));
			}
		}

		//group rows for each phase, row numbers are 0-based
		sheet.groupRow(4, 6);
		sheet.groupRow(9, 13);
		sheet.groupRow(16, 18);

		//set column widths, the width is measured in units of 1/256th of a character width
		//sheet.setColumnWidth(0, 256*15);
	//	sheet.setColumnWidth(2, 256*18);
		for (int i = 0; i < titles.length; i++) {
			
			
			if (i==3)
				sheet.setColumnWidth(i, 256*23);			
			else if(i==4)
				sheet.setColumnWidth(i, 256*9);			
			else if(i==6)
				sheet.setColumnWidth(i, 256*9);	
			else if(i==7)
				sheet.setColumnWidth(i, 256*9);
			else 
				sheet.setColumnWidth(i, 256*16);								
			}
				
		sheet.setZoom(3, 2);

		// Write the output to a file
		String file = System.getProperty("user.dir")+"/src/com/puma/config/PumaTest.xls";
		if(wb instanceof XSSFWorkbook) file += "x";
		FileOutputStream out = new FileOutputStream(file);
		wb.write(out);
		out.close();
	}

	/**
	 * create a library of cell styles
	 */
	private static Map<String, CellStyle> createStyles(Workbook wb){
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		DataFormat df = wb.createDataFormat();

		CellStyle style;
		Font headerFont = wb.createFont();
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(headerFont);
		styles.put("header", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(headerFont);
		style.setDataFormat(df.getFormat("d-mmm"));
		styles.put("header_date", style);

		Font font1 = wb.createFont();
		font1.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setFont(font1);
		styles.put("cell_b", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFont(font1);
		styles.put("cell_b_centered", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		style.setFont(font1);
		style.setDataFormat(df.getFormat("d-mmm"));
		styles.put("cell_b_date", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		style.setFont(font1);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setDataFormat(df.getFormat("d-mmm"));
		styles.put("cell_g", style);

		Font font2 = wb.createFont();
		font2.setColor(IndexedColors.BLUE.getIndex());
		font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setFont(font2);
		styles.put("cell_bb", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		style.setFont(font1);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setDataFormat(df.getFormat("d-mmm"));
		styles.put("cell_bg", style);

		Font font3 = wb.createFont();
		font3.setFontHeightInPoints((short)14);
		font3.setColor(IndexedColors.DARK_BLUE.getIndex());
		font3.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setFont(font3);
		style.setWrapText(true);
		styles.put("cell_h", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setWrapText(true);
		styles.put("cell_normal", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setWrapText(true);
		styles.put("cell_normal_centered", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		style.setWrapText(true);
		style.setDataFormat(df.getFormat("d-mmm"));
		styles.put("cell_normal_date", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setIndention((short)1);
		style.setWrapText(true);
		styles.put("cell_indented", style);

		style = createBorderedStyle(wb);
		style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		styles.put("cell_blue", style);

		return styles;
	}

	private static CellStyle createBorderedStyle(Workbook wb){
		CellStyle style = wb.createCellStyle();
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		return style;
	}
}