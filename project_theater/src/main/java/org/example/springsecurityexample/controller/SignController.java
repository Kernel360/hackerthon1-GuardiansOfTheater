package org.example.springsecurityexample.controller;

// 로그인, 회원가입, 유저 조회 3가지 기능

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springsecurityexample.domain.Authority;
import org.example.springsecurityexample.dto.SignRequest;
import org.example.springsecurityexample.dto.SignResponse;
import org.example.springsecurityexample.dto.TokenDto;
import org.example.springsecurityexample.repository.MemberRepository;
import org.example.springsecurityexample.service.SignService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SignController {

    private final MemberRepository memberRepository;
    private final SignService signService;

    @GetMapping("/login")
    public String loginView() {
        log.info("init login view");
        return "auth/login";
    }

    @PostMapping("/api/login")
    @ResponseBody
    public ResponseEntity<SignResponse> login(
            @RequestBody SignRequest request,
            Model model
    ) throws Exception {
        ResponseEntity<SignResponse> response = new ResponseEntity<>(
                signService.login(request),
                HttpStatus.OK
        );
        if (!response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("error", true);
            model.addAttribute("etext", "로그인 실패");
            log.info("loog err : {}", model.getAttribute("error"));
            log.info("loog err : {}", model.getAttribute("error"));
        }
        log.info("loog response : {}", response.getStatusCode());
        log.info("loog response : {}", response.getBody().getRoles().stream().map(Authority::getName));
        model.addAttribute("response", response.getBody());
        return response;
    }

    @GetMapping("/register")
    public String registerView() {
        return "auth/register";
    }

    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity<Boolean> register(
            @RequestBody SignRequest request
    ) throws Exception {
        ResponseEntity<Boolean> response = new ResponseEntity<>(signService.register(request), HttpStatus.OK);
        log.info("loog register : {}", response.getStatusCode());
        log.info("loog user : {}", signService.login(request).getAccount());
//        return "redirect:/user";
        return response;
    }

    //    @GetMapping("/user")
//    public ResponseEntity<SignResponse> getUser(@RequestParam String account) throws Exception {
//        return new ResponseEntity<>(signService.getMember(account), HttpStatus.OK);
//

    @GetMapping("/admin/get")
    public ResponseEntity<SignResponse> getUserForAdmin(@RequestParam String account) throws Exception {
        return new ResponseEntity<>(signService.getMember(account), HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<TokenDto> refresh(@RequestBody TokenDto token) throws Exception {
        return new ResponseEntity<>(signService.refreshAccessToken(token), HttpStatus.OK);
    }
}