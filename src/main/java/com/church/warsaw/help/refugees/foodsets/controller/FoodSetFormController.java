package com.church.warsaw.help.refugees.foodsets.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FoodSetFormController {

    @GetMapping("/food-set-form")
    public String foodSetFormPage(Model model) {
        return "foodSetForm";
    }
}
