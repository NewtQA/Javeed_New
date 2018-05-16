package com.orangehrm.excelutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FetchDataFromXL {
	
public String[][] fetchXLData(String ExcelLocation,String SheetName)
{
	String dataSets[][]=null;
	try {
		FileInputStream file=new FileInputStream(new File(ExcelLocation));
		//create workbook instance by holding the reference to .xlsx file
		XSSFWorkbook oWB=new XSSFWorkbook(file);
		// get first or desired sheet from the  workbook
		XSSFSheet oSheet=oWB.getSheet(SheetName);
		//count number of active rows
		int totalrows =oSheet.getLastRowNum()+1;
		//count number of active columns 
		int totalcol=oSheet.getRow(0).getLastCellNum();
		//create array of rows and colmns
		dataSets=new String[totalrows-1][totalcol];
		// iterate through each row
				Iterator<Row> rowIterator = oSheet.iterator();	
				int i=0;
				int t=0;
				while (rowIterator.hasNext()){
					
				}
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

}
