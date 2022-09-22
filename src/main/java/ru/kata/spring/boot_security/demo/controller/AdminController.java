package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String getAdminPage(Model model, Principal principal) {
        User userAdmin = userService.getUserByUsername(principal.getName());
//        ArrayList<String> allRole = new ArrayList<>(){"ROLE_ADMIN", "ROLE_USER"};
//        String[] listRole = {"ROLE_ADMIN", "ROLE_USER"};
        var allUser = userService.getAllUser();
        model.addAttribute("newUser", new User());
        model.addAttribute("userAdmin", userAdmin);
        System.out.println(allUser);
        model.addAttribute("listUser", allUser);
        model.addAttribute("listRole", Arrays.asList("ROLE_ADMIN", "ROLE_USER"));
        return "admin";
    }

    @PostMapping("/saveNewUser")
    public String saveNewUser(@ModelAttribute ("user") User user) {
        System.out.println("Сохранение " + user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        System.out.println(user);
        return "redirect:/admin";
    }
    @PatchMapping("/{id}")
    public String updateUser(@PathVariable ("id") long id, @ModelAttribute ("user") User user) {
        System.out.println("Из редактирования " + user);
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String updateUser(@PathVariable ("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    /*@PostMapping
    public String addNewUser(Model model) {
        model.addAttribute("user");
        return "redirect:/admin";
    }*/
    /*@GetMapping("/addNewUser")
    public String addNewUser(Model model) {
        model.addAttribute("user", new User());
        return "admin/saveNewUser";
    }

    @PostMapping("/saveNewUser")
    public String saveNewUser(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/update/{id}")
    public String updateBeforeUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "admin/updateUser";
    }
    @PostMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") long id, @ModelAttribute("user") User user) {
        userService.updateUser(id, user);
        return "redirect:/admin";
    }
    @RequestMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("userDelete", userService.getUser(id));
        userService.deleteUser(id);
        return "redirect:/admin";
    }*/
}
