package com.church.warsaw.help.refugees.foodsets.controller;

import com.church.warsaw.help.refugees.foodsets.docgenerator.PdfGeneratorService;
import com.church.warsaw.help.refugees.foodsets.dto.RegistrationInfo;
import com.church.warsaw.help.refugees.foodsets.service.RegistrationInfoService;
import com.church.warsaw.help.refugees.foodsets.validator.DataValidator;
import com.church.warsaw.help.refugees.foodsets.validator.ValidationResult;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@Slf4j
public class RegistrationInfoController {

  private final DataValidator dataValidator;

  private final RegistrationInfoService registrationInfoService;

  private final PdfGeneratorService pdfGeneratorService;

  @GetMapping("/food-set-form")
  public String foodSetFormPage() {


    return "foodSetForm";
  }

  @PostMapping("/registration-infos")
  public String save(@ModelAttribute RegistrationInfo registrationInfo,
                     Model model,
                     RedirectAttributes rm) {


    ValidationResult validationResult = dataValidator.validate(registrationInfo);

    if (validationResult.isError()) {
      rm.addFlashAttribute("error", validationResult.getErrorMessage());
      rm.addFlashAttribute("registrationInfo", registrationInfo);

      return "redirect:/food-set-form";
    }

    registrationInfoService.registerForm(registrationInfo);

    return "foodSetFormSuccess";
  }


  @PutMapping("/registration-infos/{id}")
  @ResponseStatus(value = HttpStatus.OK)
  public void update(@PathVariable("id") String id, @RequestBody UpdateRegistrationInfoRequest request) {

    registrationInfoService.updateForm(id, request);
  }

  @GetMapping("/registration-infos")
  public String getRegistrationInfos(@RequestParam(name = "receiveDate", required = false)
                                     @DateTimeFormat(pattern = "yyyy-mm-dd")
                                     LocalDate receiveDate, Model model) {

    LocalDate date = receiveDate == null ? LocalDate.now() : receiveDate;
    List<RegistrationInfo> registrationInfos =
        registrationInfoService.getRegistrationInfoByDate(date);

    model.addAttribute("registrationInfos", registrationInfos);

    return "registrationInfos";
  }

  @GetMapping("/registration-infos-content")
  @ResponseBody
  public List<RegistrationInfo> getRegistrationInfosContent(
      @RequestParam(name = "receiveDate", required = false)
      String receiveDate) {

    LocalDate date = receiveDate == null ? LocalDate.now() : LocalDate.parse(receiveDate);

    List<RegistrationInfo> registrationInfoByDate =
        registrationInfoService.getRegistrationInfoByDate(date);

    log.info("Found registrations info with count={}", registrationInfoByDate.size());

    return registrationInfoByDate;
  }

  @GetMapping("/registration-infos/stream-of-delivery")
  @ResponseBody
  public List<String> getStreamOfDelivery(@RequestParam(name = "receiveDate", required = false)
                                          String receiveDate) {

    if (StringUtils.isBlank(receiveDate)) {
      return Collections.emptyList();
    }

    return registrationInfoService.getAvailableStreamsByDate(LocalDate.parse(receiveDate));
  }

  @GetMapping("/registration-infos/generate-pdf-file")
  public void generatePdfFile(@RequestParam(name = "receiveDate", required = false)
                              String receiveDate,
                              HttpServletResponse response) throws IOException {

    LocalDate date = receiveDate == null ? LocalDate.now() : LocalDate.parse(receiveDate);
    List<RegistrationInfo> registrationInfos =
        registrationInfoService.getRegistrationInfoByDate(date);

    response.setContentType("application/pdf");
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");

    String currentDateTime = dateFormat.format(new Date());

    response.setHeader("Content-Disposition",
        "attachment; filename=registrations-infos" + currentDateTime + ".pdf");

    pdfGeneratorService.generate(registrationInfos, response);
  }

}
