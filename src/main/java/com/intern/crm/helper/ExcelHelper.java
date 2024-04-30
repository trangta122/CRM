package com.intern.crm.helper;

import com.intern.crm.entity.Opportunity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"company", "email", "phone", "address", "website", "revenue", "isCustomer"};
    static String SHEET = "opportunity";

    //checks whether the file format is Excel or not.
    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    //reads the Excel Sheet data returns list of opportunties
    public static List<Opportunity> excelToOpportunities(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            List<Opportunity> opportList = new ArrayList<Opportunity>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                //skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();
                Opportunity opportunity = new Opportunity();
                int cellIndex = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIndex) {
                        case 0:
                            opportunity.setCompany(currentCell.getStringCellValue());
                            opportunity.setName(currentCell.getStringCellValue());
                            break;
                        case 1:
                            opportunity.setEmail(currentCell.getStringCellValue());
                            break;
                        case 2:
                            opportunity.setPhone(currentCell.getStringCellValue());
                            break;
                        case 3:
                            opportunity.setAddress(currentCell.getStringCellValue());
                            break;
                        case 4:
                            opportunity.setWebsite(currentCell.getStringCellValue());
                            break;
                        case 5:
                            opportunity.setRevenue(currentCell.getNumericCellValue());
                            break;
                        case 6:
                            opportunity.setCustomer(currentCell.getBooleanCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIndex++;
                } opportList.add(opportunity);
            }
            workbook.close();
            return opportList;
        } catch (IOException e) {
            throw new RuntimeException("Failed tp parse Excel file" + e.getMessage());
        }
    }
}
