package org.example.springsecurityexample.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springsecurityexample.service.SignService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {
    private final SignService service;

    @GetMapping("/")
    public String root() {
        isAuthenticated();
        return "redirect:/login/view";
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities().stream().forEach(it -> log.info("Logged : {}", it.getAuthority().toString()));
        log.info("Logged auth: {}", authentication);
        log.info("Logged auth class: {}", authentication.isAuthenticated());
        return authentication.isAuthenticated();
    }
}
