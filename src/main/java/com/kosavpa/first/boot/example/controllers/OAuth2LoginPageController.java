package com.kosavpa.first.boot.example.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/oauth2LoginPage")
public class OAuth2LoginPageController {
    @GetMapping
    public String redirectToAuthorizeAndAuthontificateVkFlow(){
        return "redirect:/oauth2/authorization/vk";
    }
}
