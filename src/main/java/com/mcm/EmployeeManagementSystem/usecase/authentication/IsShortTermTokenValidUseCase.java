package com.mcm.EmployeeManagementSystem.usecase.authentication;

import com.mcm.EmployeeManagementSystem.security.config.JwtService;
import com.mcm.EmployeeManagementSystem.security.token.Token;
import com.mcm.EmployeeManagementSystem.security.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IsShortTermTokenValidUseCase {
    private final TokenRepository repository;
    private final JwtService jwtService;

    public boolean isValid(String shortTermToken) {
        Optional<Token> tokenOptional = repository.findByToken(shortTermToken);
        if (!tokenOptional.isPresent()) {
            return false;
        }

        Token token = tokenOptional.get();
        if (jwtService.isTokenValid(token.getToken(), token.getUser())) {
            return true;
        }

        return false;
    }
}
