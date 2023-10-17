package org.example.springsecurityexample.member.dto;
//응답, 반환애 이용할 DTO

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignRequest {

    private Long id;

    private String account;

    private String password;

    private String nickname;

    private String name;

    private String email;

}