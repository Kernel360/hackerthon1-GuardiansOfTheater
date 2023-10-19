package org.example.springsecurityexample.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springsecurityexample.service.SignService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
    private final SignService service;

    @GetMapping("/user")
    public String showUserInformation(Model model) {
        model.addAttribute("account", "SampleAccount");
        model.addAttribute("password", "********");
        model.addAttribute("nickname", "SampleNickname");
        model.addAttribute("name", "John Doe");
        model.addAttribute("email", "johndoe@example.com");

        return "user/information"; // This should match the name of your Thymeleaf template file.
    }
}
