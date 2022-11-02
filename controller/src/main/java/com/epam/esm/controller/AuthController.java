package com.epam.esm.controller;

import com.epam.esm.entity.User;
import com.epam.esm.security.JWTUtil;
import com.epam.esm.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Class for represent pages for login and registration
 */
@Controller
@RequestMapping("/auth")
public class AuthController {
    static final String ADMIN = "ROLE_ADMIN";
    private final UserServiceImpl userService;
    private AuthenticationManager authenticationManager;
    private JWTUtil jwtTokenUtil;

    @Autowired
    public AuthController(UserServiceImpl userService, AuthenticationManager authenticationManager, JWTUtil jwtTokenUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * Method for return login page
     *
     * @return login page
     */
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    /**
     * Method for return registration page
     *
     * @param model
     * @return registration page
     */
    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/registration";
    }

    /**
     * Method for perforn registration
     *
     * @param user
     * @return registration page
     */
    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") User user) {
        userService.register(user);
        return "redirect:/auth/login";
    }
}