package com.you.springmvc2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class Test {

    @RequestMapping("/test")
    public String view1(Model model){
        model.addAttribute("message","你好");
        return "home";
    }
}
