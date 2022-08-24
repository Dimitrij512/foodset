package com.church.warsaw.help.refugees.foodsets.controller;

import com.church.warsaw.help.refugees.foodsets.docgenerator.PdfGeneratorService;
import com.church.warsaw.help.refugees.foodsets.dto.RegistrationInfo;
import com.church.warsaw.help.refugees.foodsets.service.RegistrationInfoService;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class FoodSetFormController {

  private final RegistrationInfoService registrationInfoService;

  private final PdfGeneratorService pdfGeneratorService;

  @GetMapping("/food-set-form")
  public String foodSetFormPage() {
    return "foodSetForm";
  }

  @PostMapping("/register-food-set-form")
  public String registerForm(@ModelAttribute RegistrationInfo registrationInfo) {
    registrationInfoService.registerForm(registrationInfo);

    return "foodSetFormSuccess";
  }

  @GetMapping("refugee/registration-infos")
  public String getRegistrationInfos(@RequestParam(name = "receiveDate", required = false)
                                     @DateTimeFormat(pattern = "yyyy-mm-dd")
                                     LocalDate receiveDate,
                                     Model model) {

    LocalDate date = receiveDate == null ? LocalDate.now() : receiveDate;
    List<RegistrationInfo> registrationInfos =
        registrationInfoService.getRegistrationInfoByDate(date);

    model.addAttribute("registrationInfos", registrationInfos);

    return "registrationInfos";
  }

  @GetMapping("refugee/registration-infos/generate-pdf-file")
  public void generatePdfFile(@RequestParam(name = "receiveDate", required = false)
                              @DateTimeFormat(pattern = "yyyy-mm-dd")
                              LocalDate receiveDate, HttpServletResponse response) throws IOException {

    LocalDate date = receiveDate == null ? LocalDate.now() : receiveDate;
    List<RegistrationInfo> registrationInfos = registrationInfoService.getRegistrationInfoByDate(date);

    response.setContentType("application/pdf");
    DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");

    String currentDateTime = dateFormat.format(new Date());

    response.setHeader("Content-Disposition", "attachment; filename=registrations-infos" + currentDateTime + ".pdf");

    pdfGeneratorService.generate(registrationInfos, response);
  }

}
