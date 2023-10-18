package org.example.springsecurityexample.repository;
//사용자 조회하기 위한 레파지토리
import org.example.springsecurityexample.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByAccount(String account);
}