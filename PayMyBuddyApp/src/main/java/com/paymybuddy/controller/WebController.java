package com.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
	
    @GetMapping("/")
    public String viewSlachPage() {
        return "index";
    }
    
    @GetMapping("/index")
    public String viewIndexPage() {
        return "index";
    }
    
    @GetMapping("/home")
    public String viewHomePage() {
        return "index";
    }
    
	@GetMapping("/login")
	public String viewLoginPage() {
		return "login";
	}
    
}
