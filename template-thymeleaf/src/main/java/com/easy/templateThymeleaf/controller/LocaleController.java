package com.easy.templateThymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LocaleController {

    @GetMapping(value = "/locale")
    public String localeHandler(HttpServletRequest request) {

        String lastUrl = request.getHeader("referer");
        return "redirect:" + lastUrl;
    }
}
