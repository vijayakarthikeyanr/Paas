package com.paas.app.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.paas.app.model.AppPlatform;
import com.paas.app.model.Response;

@Component
public class ReadExcelUtil {
	@Autowired
	SFTPUtil sftpUtil;

	public Response readExcel() {
		ObjectMapper mapper = new ObjectMapper();
		Response response = new Response();
		List<AppPlatform> appPlatformList = Lists.newArrayList();
		// Get first/desired sheet from the workbook
		XSSFSheet sheet = loadExcel().getSheetAt(0);

		// Iterate through each rows one by one
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (row.getRowNum() != 0) {
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				AppPlatform appPlatform = new AppPlatform();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					// Check the cell type and format accordingly
					switch (cell.getCellType()) {
					case STRING:
						if (StringUtils.isNotEmpty(cell.getStringCellValue())) {
							setData(cell, appPlatform);
						}
						break;
					case NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							if (null != cell.getDateCellValue()) {
								setData(cell, appPlatform);
							}
						} else {
							if (cell.getNumericCellValue() >= 0) {
								setData(cell, appPlatform);
							}
						}
						break;
					case BOOLEAN:
						setData(cell, appPlatform);
						break;
					default:

					}
				}
				// System.out.println(""); 
				if (null != appPlatform && StringUtils.isNotEmpty(appPlatform.getDomain())) {
					appPlatformList.add(appPlatform);
				}
			}
		}
		try {
			response.setAppPlatformList(appPlatformList);
			System.out.println(mapper.writeValueAsString(response));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	private XSSFWorkbook loadExcel() {
		XSSFWorkbook workbook = null;
		try {
			FileInputStream file = new FileInputStream(
					new File("C:\\MS Office Patching 2021\\CaaS\\Reports\\PaaS Platform Matrix_Macro.xlsm"));
			// Create Workbook instance holding reference to .xlsx file
			workbook = new XSSFWorkbook(file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return workbook;
	}

	private void setData(Cell cell, AppPlatform appPlatform) {
		// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		switch (cell.getColumnIndex()) {
		case 0:
			appPlatform.setDomain(cell.getStringCellValue());
			break;
		case 1:
			appPlatform.setOrg(cell.getStringCellValue());
			break;
		case 2:
			appPlatform.setApplicationInstance(cell.getStringCellValue());
			break;
		case 3:
			appPlatform.setClNo(cell.getStringCellValue());
			break;
		case 4:
			appPlatform.setEnvironment(cell.getStringCellValue());
			break;
		case 5:
			appPlatform.setMicroservices(cell.getNumericCellValue());
			break;
		}
	}

}
