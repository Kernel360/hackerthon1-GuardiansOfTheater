package org.example.springsecurityexample.controller;

// 로그인, 회원가입, 유저 조회 3가지 기능

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springsecurityexample.domain.Authority;
import org.example.springsecurityexample.domain.Member;
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

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SignController {

    private final MemberRepository memberRepository;
    private final SignService signService;

    @GetMapping("/login/view")
    public String loginView() {
        log.info("init login view");
        return "auth/login";
    }

    @PostMapping("/api")
    @ResponseBody
    public ResponseEntity<SignResponse> apiLogin(
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
        log.info("loog api response : {}", response.getStatusCode());
        log.info("loog api response : {}", response.getBody().getRoles().stream().map(Authority::getName));
        model.addAttribute("response", response.getBody());
        return response;
    }

    @PostMapping("/login")
    public String postLogin(
            HttpServletRequest httpReq,
            SignRequest request,
            ModelMap map
    ) throws Exception {
        SignResponse res = signService.login(request);
        ResponseEntity<SignResponse> response = new ResponseEntity<>(
                res,
                HttpStatus.OK
        );
        if (!request.getAccount().equals(res.getAccount())) {
            map.addAttribute("error", true);
            log.info("loog err : {}", map.getAttribute("response"));
            log.info("loog err : {}", map.getAttribute("error"));
        }

        log.info("loog post login response : {}", response.getStatusCode());
        log.info("loog post response : {}", response.getBody().getRoles().stream().map(Authority::getName));
        map.addAttribute("data", response);
        httpReq.setAttribute("member", memberRepository.findByAccount(res.getAccount()).get());
        return "redirect:/user";
    }
//
//    @GetMapping("/user")
//    public String getUserInfo(
//            Model model
//    ) {
//        model.addAttribute("account", "SampleAccount");
//        model.addAttribute("password", "********");
//        model.addAttribute("nickname", "SampleNickname");
//        model.addAttribute("name", "John Doe");
//        model.addAttribute("email", "johndoe@example.com");
//        return "user/information"; // This should match the name of your Thymeleaf template file.
//    }

    @GetMapping("/register/view")
    public String registerView() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(
            SignRequest request,
            Model model
    ) throws Exception {
        ResponseEntity<Boolean> response = new ResponseEntity<>(signService.register(request), HttpStatus.OK);
        model.addAttribute("callback", true);
        log.info("loog register : {}", response.getStatusCode());
        log.info("loog user : {}", signService.login(request).getAccount());
        return "user/monitoring";
    }

    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity<Boolean> registerApi(
            @RequestBody SignRequest request
    ) throws Exception {
        ResponseEntity<Boolean> response = new ResponseEntity<>(signService.register(request), HttpStatus.OK);
        log.info("loog register : {}", response.getStatusCode());
        log.info("loog user : {}", signService.login(request).getAccount());
//        return "redirect:/user";
        return response;
    }

    @GetMapping("/admin/get")
    public ResponseEntity<SignResponse> getUserForAdmin(@RequestParam String account) throws Exception {
        return new ResponseEntity<>(signService.getMember(account), HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<TokenDto> refresh(@RequestBody TokenDto token) throws Exception {
        return new ResponseEntity<>(signService.refreshAccessToken(token), HttpStatus.OK);
    }
}