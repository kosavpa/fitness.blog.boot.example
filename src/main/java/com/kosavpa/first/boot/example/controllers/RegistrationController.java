package com.kosavpa.first.boot.example.controllers;


import com.kosavpa.first.boot.example.dao.entity.users.UserEntity;
import com.kosavpa.first.boot.example.dao.service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private UserService userService;

    @Autowired
    public void setUserServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute(name = "userEntity")
    public UserEntity user(){

        return new UserEntity();
    }

    @GetMapping()
    public String registration(Model model) {
        model.addAttribute("userEntity", new UserEntity());

        return "registration";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("userEntity") @Valid UserEntity userForm, Errors errors, Model model) {
        if (errors.hasErrors()){
            return "registration";
        }

        if (!userService.saveUser(userForm)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }

        return "redirect:/";
    }
}
