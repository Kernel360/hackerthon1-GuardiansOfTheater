package org.example.springsecurityexample.controller;

// 로그인, 회원가입, 유저 조회 3가지 기능

import org.example.springsecurityexample.dto.SignRequest;
import org.example.springsecurityexample.dto.SignResponse;
import org.example.springsecurityexample.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.example.springsecurityexample.repository.MemberRepository;
import org.example.springsecurityexample.service.SignService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SignController {

    private final MemberRepository memberRepository;
    private final SignService memberService;

    @PostMapping(value = "/login")
    public ResponseEntity<SignResponse> login(@RequestBody SignRequest request) throws Exception {
        return new ResponseEntity<>(memberService.login(request), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Boolean> signup(@RequestBody SignRequest request) throws Exception {
        return new ResponseEntity<>(memberService.register(request), HttpStatus.OK);
    }

    @GetMapping("/user/get")
    public ResponseEntity<SignResponse> getUser(@RequestParam String account) throws Exception {
        return new ResponseEntity<>( memberService.getMember(account), HttpStatus.OK);
    }

    @GetMapping("/admin/get")
    public ResponseEntity<SignResponse> getUserForAdmin(@RequestParam String account) throws Exception {
        return new ResponseEntity<>( memberService.getMember(account), HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<TokenDto> refresh(@RequestBody TokenDto token) throws Exception {
        return new ResponseEntity<>( memberService.refreshAccessToken(token), HttpStatus.OK);
    }
}