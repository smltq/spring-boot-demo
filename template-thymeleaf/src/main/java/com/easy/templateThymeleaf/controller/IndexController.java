package com.easy.templateThymeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

@Controller
public class IndexController {

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
    public String index(Model model, Locale locale) {

        model.addAttribute("title", messageSource.getMessage("text.title", null, locale));
        return "index";
    }
}