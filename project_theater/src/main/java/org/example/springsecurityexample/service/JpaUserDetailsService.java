package org.example.springsecurityexample.service;
// JPA 를 이용하여 DB에서 유저 정보를 조회 구현

import org.example.springsecurityexample.Config.security.CustomUserDetails;
import org.example.springsecurityexample.domain.Member;
import org.example.springsecurityexample.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByAccount(username).orElseThrow(
                () -> new UsernameNotFoundException("Invalid authentication!")
        );

        return new CustomUserDetails(member);
    }
}