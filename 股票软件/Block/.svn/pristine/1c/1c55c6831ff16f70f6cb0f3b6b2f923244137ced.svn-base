package com.block.model.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelService {
	
	public static List<String[]> getData(String fileName) {
		List<String[]> result = new ArrayList<String[]>();
		FileInputStream fis = null;
		Workbook book = null;
		Sheet sheet = null;
		try {
			fis = new FileInputStream(new File(fileName));
			if (fileName.endsWith("xlsx")) {
				book = new XSSFWorkbook(fis);
			} else {
				book = new HSSFWorkbook(fis);
			}
			sheet = book.getSheetAt(0);
			for (int i=1;i<=sheet.getLastRowNum();i++) {
				Row row = sheet.getRow(i);
				String[] temp = new String[2];
				Date date = row.getCell(0).getDateCellValue();
				
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String key = formatter.format(date);
				temp[0] = key;
				Double value = row.getCell(1).getNumericCellValue();
				if (i == sheet.getLastRowNum()) {
					temp[1] = value + "#";
				} else {
					temp[1] = value + "";
				}
					result.add(temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		ExcelService.getData("E:/1.xlsx");
	}
}
