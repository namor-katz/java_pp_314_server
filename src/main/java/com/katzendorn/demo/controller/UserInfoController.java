package com.katzendorn.demo.controller;

import com.katzendorn.demo.entity.User;
import com.katzendorn.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class UserInfoController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public String userInfo2(ModelMap model) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); //конструктор??
        model.addAttribute("user", principal);
        return "userInfo";
    }
}
