package org.example.springsecurityexample.security;

import org.example.springsecurityexample.security.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, Long> {
}