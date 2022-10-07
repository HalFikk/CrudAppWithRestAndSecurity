package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    public List<User> getAdminPage() {
        return userService.getAllUser();
    }

    @GetMapping("/page")
    public User getUserPage(Principal principal) {
        return userService.getUser(userService.getUserByUsername(principal.getName()).getId());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveNewUser(@RequestBody User user) {
        System.out.println(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
    }

    @PutMapping (value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@PathVariable ("id") long id, @RequestBody User user) {
        System.out.println(user);
        userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable ("id") long id) {
        userService.deleteUser(id);
    }
}