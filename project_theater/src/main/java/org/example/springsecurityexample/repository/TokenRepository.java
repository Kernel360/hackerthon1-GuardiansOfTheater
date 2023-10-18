package org.example.springsecurityexample.repository;

import org.example.springsecurityexample.domain.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, Long> {
}