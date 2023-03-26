package com.church.warsaw.help.refugees.foodsets.docgenerator;

import com.church.warsaw.help.refugees.foodsets.dto.RegistrationInfo;
import com.lowagie.text.DocumentException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExcelGeneratorService {
  public void generate(List<RegistrationInfo> registrationInfos, HttpServletResponse response)
      throws DocumentException {
    try (HSSFWorkbook workbook = new HSSFWorkbook()) {
      HSSFSheet sheet = workbook.createSheet("Інформація по реєстрації");

      HSSFRow rowhead = sheet.createRow((short) 0);
      rowhead.createCell(0).setCellValue("No.");
      rowhead.createCell(1).setCellValue("Дата");
      rowhead.createCell(2).setCellValue("Час");
      rowhead.createCell(3).setCellValue("Телефон Пол.");
      rowhead.createCell(4).setCellValue("Телефон Messanger.");
      rowhead.createCell(5).setCellValue("Електронна пошта");
      rowhead.createCell(6).setCellValue("Фамілія");
      rowhead.createCell(7).setCellValue("Ім'я");
      rowhead.createCell(8).setCellValue("Члени сім'ї");
      rowhead.createCell(9).setCellValue("Категорія");
      rowhead.createCell(10).setCellValue("Тип допомоги");
      rowhead.createCell(11).setCellValue("Отримано");

      int count = 0;
      for (RegistrationInfo info : registrationInfos) {

        ++count;
        RegistrationInfo registrationInfo = info;
        HSSFRow row = sheet.createRow(count);

        row.createCell(0).setCellValue(count);
        row.createCell(1).setCellValue(registrationInfo.getReceiveDate());
        row.createCell(2).setCellValue(registrationInfo.getStream());
        row.createCell(3).setCellValue(registrationInfo.getPhoneNumber());
        row.createCell(4).setCellValue(registrationInfo.getPhoneNumberMessenger());
        row.createCell(5).setCellValue(registrationInfo.getEmail());
        row.createCell(6).setCellValue(registrationInfo.getSurname());
        row.createCell(7).setCellValue(registrationInfo.getName());
        row.createCell(8).setCellValue(registrationInfo.getKidsCount());
        row.createCell(9).setCellValue(registrationInfo.getCategoriesAssistance());
        row.createCell(10).setCellValue(registrationInfo.getTypeSet());
        row.createCell(11).setCellValue(registrationInfo.getReceive());
      }
      autoSizeColumns(workbook);
      workbook.write(response.getOutputStream());
    } catch (Exception ex) {
      log.error("Something want wrong with generation excel file error:{}", ex.getMessage());
    }
  }

  public void autoSizeColumns(HSSFWorkbook workbook) {
    int numberOfSheets = workbook.getNumberOfSheets();
    for (int i = 0; i < numberOfSheets; i++) {
      Sheet sheet = workbook.getSheetAt(i);
      if (sheet.getPhysicalNumberOfRows() > 0) {
        Row row = sheet.getRow(sheet.getFirstRowNum());
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
          Cell cell = cellIterator.next();
          int columnIndex = cell.getColumnIndex();
          sheet.autoSizeColumn(columnIndex);
        }
      }
    }
  }

}
