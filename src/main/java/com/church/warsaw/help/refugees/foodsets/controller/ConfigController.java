package com.church.warsaw.help.refugees.foodsets.controller;

import com.church.warsaw.help.refugees.foodsets.dto.Config;
import com.church.warsaw.help.refugees.foodsets.service.ConfigService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@Slf4j
public class ConfigController {

    ConfigService configService;

    @GetMapping("/configuration")
    public String foodSetFormSuccessPage() {

        return "configForm";
    }

    @ResponseBody
    @GetMapping("/configuration/available-dates")
    public List<String> getAvailableDates() {

        return configService.getConfigEntity().getAvailableDates()
                .stream()
                .map(LocalDate::toString)
                .collect(Collectors.toList());
    }

    @PutMapping("/configuration/available-dates")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateAvailableDates(@RequestBody Config config) {
        configService.updateAvailableDates(config.getAvailableDates().stream()
                .map(LocalDate::parse)
                .collect(Collectors.toList()));
    }
}
