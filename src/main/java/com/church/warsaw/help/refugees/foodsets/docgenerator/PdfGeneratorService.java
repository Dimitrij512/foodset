package com.church.warsaw.help.refugees.foodsets.docgenerator;

import static com.lowagie.text.Element.ALIGN_CENTER;
import static java.awt.Color.DARK_GRAY;
import static java.awt.Color.GRAY;

import com.church.warsaw.help.refugees.foodsets.dto.RegistrationInfo;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class PdfGeneratorService {

  public void generate(List<RegistrationInfo> registrationInfos, HttpServletResponse response)
      throws DocumentException, IOException {

    try (Document document = new Document(PageSize.A4.rotate())) {
      PdfWriter.getInstance(document, response.getOutputStream());
      document.open();


      Font fontTittle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
      fontTittle.setSize(20);

      Paragraph paragraph = new Paragraph("List of registrations", fontTittle);
      paragraph.setAlignment(ALIGN_CENTER);
      document.add(paragraph);

      PdfPTable table = new PdfPTable(9);
      table.setHorizontalAlignment(ALIGN_CENTER);
      table.setWidthPercentage(100);
      table.setWidths(new int[] {2, 4, 4, 4, 4, 4, 4, 4, 4});
      table.setSpacingBefore(5);

      // Create Table Cells for the table header
      PdfPCell cell = new PdfPCell();
      cell.setBackgroundColor(GRAY.brighter());
      cell.setHorizontalAlignment(ALIGN_CENTER);
      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
      cell.setPadding(5);

      Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
      font.setColor(DARK_GRAY);
      cell.setPhrase(new Phrase("#", font));
      table.addCell(cell);

      cell.setPhrase(new Phrase("Date", font));
      table.addCell(cell);

      cell.setPhrase(new Phrase("Time", font));
      table.addCell(cell);

      cell.setPhrase(new Phrase("Phone", font));
      table.addCell(cell);

      cell.setPhrase(new Phrase("Surname", font));
      table.addCell(cell);

      cell.setPhrase(new Phrase("Name", font));
      table.addCell(cell);

      cell.setPhrase(new Phrase("Kids", font));
      table.addCell(cell);


      cell.setPhrase(new Phrase("Type", font));
      table.addCell(cell);

      cell.setPhrase(new Phrase("Received", font));
      table.addCell(cell);

      int count = 0;
      for (RegistrationInfo registrationInfo : registrationInfos) {

        table.addCell(String.valueOf(++count));
        table.addCell(registrationInfo.getReceiveDate());
        table.addCell(registrationInfo.getStream());
        table.addCell(registrationInfo.getPhoneNumber());
        table.addCell(registrationInfo.getSurname());
        table.addCell(registrationInfo.getName());
        table.addCell(String.valueOf(registrationInfo.getKidsCount()));
        table.addCell(registrationInfo.getTypeSet());
        table.addCell(registrationInfo.getReceive());
      }
      document.add(table);
    }
  }
}
