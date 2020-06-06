package com.katzendorn.demo.controller;

import com.katzendorn.demo.entity.User;
import com.katzendorn.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.model.IModel;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "list2")
    public String listPage(ModelMap model) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("you", principal);
        return "list";
    }



    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String returnListUsers(ModelMap model) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<User> list = userService.allUsers();
        model.addAttribute("list", list);
        model.addAttribute("you", principal);
        return "listAllUsers";
    }


    //======== edit user block do ==========
    @GetMapping(value = "edit")
    public String editUser(Long id, ModelMap model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping(value = "edit")
    public String editUser(@RequestParam long id, @RequestParam String username,
                           @RequestParam String email, @RequestParam int maxweight) {
            User user = userService.findUserById(id);
            user.setUsername(username);
            user.setEmail(email);
            user.setMaxweight(maxweight);
            userService.updateUser(user);
            return "redirect:/admin/list";
    }
    //===== begin edit user block ==========


    @GetMapping(value = "delete")
    public String editUser(@RequestParam Long id) {
        System.out.println("мой ИД = " + id);
        userService.deleteUser(id);
        return "redirect:/admin/list";
    }
}
