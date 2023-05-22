package com.mcm.EmployeeManagementSystem.usecase.authentication;

import com.mcm.EmployeeManagementSystem.entity.UserEntity;
import com.mcm.EmployeeManagementSystem.security.token.Token;
import com.mcm.EmployeeManagementSystem.security.token.TokenRepository;
import com.mcm.EmployeeManagementSystem.security.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveUserTokenUseCase {
    private final TokenRepository repository;

    public void saveUserToken(UserEntity user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        repository.save(token);
    }
}
