package com.netc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HwController {

    @GetMapping( "/" )
    public String welcomePage(Model model) {
        model.addAttribute("hello", "HELLO WORLD");
        return "index";
    }
}