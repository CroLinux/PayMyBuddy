package com.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
	
    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }
    
    @GetMapping("/index")
    public String viewIndexPage() {
        return "index";
    }
    
}
