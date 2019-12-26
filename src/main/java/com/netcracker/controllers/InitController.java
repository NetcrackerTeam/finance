package com.netcracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class InitController {

    @RequestMapping
    public String welcomePage(Model model) {
        model.addAttribute("hello", "HELLO WORLD");
      //  return  "index";
       return "viewsLoginRegestration/layoutLoginUser";
      // return "viewsLoginRegestration/layoutRegistrationUser";
    }
}