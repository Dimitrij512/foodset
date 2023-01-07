package com.church.warsaw.help.refugees.foodsets.controller;

import static com.church.warsaw.help.refugees.foodsets.service.RegistrationInfoService.HAS_ERROR_KEY;

import com.church.warsaw.help.refugees.foodsets.docgenerator.ExcelGeneratorService;
import com.church.warsaw.help.refugees.foodsets.dto.RegistrationInfo;
import com.church.warsaw.help.refugees.foodsets.service.RegistrationInfoService;
import com.church.warsaw.help.refugees.foodsets.validator.DataValidator;
import com.church.warsaw.help.refugees.foodsets.validator.ValidationResult;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
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
  private final ExcelGeneratorService excelGeneratorService;

  @GetMapping("/food-set-form")
  public String foodSetFormPage() {

    return "foodSetForm";
  }

  @GetMapping("/food-set-form/success")
  public String foodSetFormSuccessPage() {
    return "foodSetFormSuccess";
  }

  @PostMapping("/registration-infos/create")
  public String save(@ModelAttribute RegistrationInfo registrationInfo, RedirectAttributes rm) {

    ValidationResult validationResult = dataValidator.validate(registrationInfo);

    if (validationResult.isError()) {
      rm.addFlashAttribute("error", validationResult.getErrorMessage());
      rm.addFlashAttribute("registrationInfo", registrationInfo);

      return "redirect:/food-set-form";
    }

    Pair<String, String> registrationResult =
        registrationInfoService.registerForm(registrationInfo);

    if (HAS_ERROR_KEY.equals(registrationResult.getLeft())) {
      rm.addFlashAttribute("error", registrationResult.getRight());
      rm.addFlashAttribute("registrationInfo", registrationInfo);
      return "redirect:/food-set-form";
    }

    return "redirect:/food-set-form/success";
  }

  @GetMapping("/registration-infos/create/free")
  public String foodSetFormFreePage() {
    return "foodSetFormFree";
  }

  @PostMapping("/registration-infos/create/free")
  public String createFree(@ModelAttribute RegistrationInfo registrationInfo) {

    registrationInfoService.registerFormFree(registrationInfo);

    return "redirect:/registration-infos";
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

  @GetMapping("/registration-infos-by-range")
  public String getRegistrationInfosWithRange(@RequestParam(name = "startDate", required = false)
                                              @DateTimeFormat(pattern = "yyyy-mm-dd")
                                              LocalDate startDate,
                                              @RequestParam(name = "startDate", required = false)
                                              @DateTimeFormat(pattern = "yyyy-mm-dd")
                                              LocalDate endDate,
                                              Model model) {

    LocalDate start = startDate == null ? LocalDate.now() : startDate;
    LocalDate end = endDate == null ? LocalDate.now() : endDate;

    List<RegistrationInfo> registrationInfos =
        registrationInfoService.getRegistrationInfoByRange(start, end);

    model.addAttribute("registrationInfos", registrationInfos);

    return "registrationInfos";
  }

  @GetMapping("/registration-infos-content-by-range")
  @ResponseBody
  public List<RegistrationInfo> getRegistrationInfosContentWithRange(
      @RequestParam(name = "startDate")
      String startDate,
      @RequestParam(name = "endDate")
      String endDate) {

    LocalDate start = StringUtils.isBlank(startDate) ? LocalDate.now() : LocalDate.parse(startDate);
    LocalDate end = StringUtils.isBlank(endDate) ? LocalDate.now() : LocalDate.parse(endDate);

    List<RegistrationInfo> registrationInfoByDate =
        registrationInfoService.getRegistrationInfoByRange(start, end);

    log.info("Found registrations info with count={}", registrationInfoByDate.size());

    return registrationInfoByDate;
  }

  @GetMapping("/registration-infos-content")
  @ResponseBody
  public List<RegistrationInfo> getRegistrationInfosContentWithRange(
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

  @GetMapping("/registration-infos/generate-excel-file")
  public void generateExcelFile(@RequestParam(name = "startDate", required = false)
                                String startDate,
                                @RequestParam(name = "endDate", required = false)
                                String endDate,
                                HttpServletResponse response) {

    LocalDate start = StringUtils.isBlank(startDate) ? LocalDate.now() : LocalDate.parse(startDate);
    LocalDate end = StringUtils.isBlank(endDate) ? LocalDate.now() : LocalDate.parse(endDate);

    List<RegistrationInfo> registrationInfos =
        registrationInfoService.getRegistrationInfoByRange(start, end).stream()
            .sorted(Comparator.comparing(RegistrationInfo::getSurname))
            .collect(Collectors.toList());

    response.setContentType("application/vnd.ms-excel");
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");

    String currentDateTime = dateFormat.format(new Date());

    response.setContentType("application/vnd.ms-excel");
    response.setHeader("Content-Disposition",
        "attachment; filename=registrations-infos" + currentDateTime + ".xls");

    excelGeneratorService.generate(registrationInfos, response);
  }

}
