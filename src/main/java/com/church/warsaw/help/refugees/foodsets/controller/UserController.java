package com.church.warsaw.help.refugees.foodsets.controller;

import static java.util.Objects.nonNull;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

  @GetMapping("/login")
  public String getLoginPage(Model model, String error, String logout) {

    if (nonNull(error))
      model.addAttribute("error", "Логін або пароль вказано не вірно!");

    if (nonNull(logout))
      model.addAttribute("message", "You have been logged out successfully.");

    return "users/login";
  }
}
