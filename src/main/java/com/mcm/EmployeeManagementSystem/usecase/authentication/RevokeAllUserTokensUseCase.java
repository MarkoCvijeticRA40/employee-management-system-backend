package com.mcm.EmployeeManagementSystem.usecase.authentication;

import com.mcm.EmployeeManagementSystem.entity.UserEntity;
import com.mcm.EmployeeManagementSystem.security.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RevokeAllUserTokensUseCase {
    private final TokenRepository repository;

    public void revokeAllUserTokens(UserEntity user) {
        var validUserTokens = repository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        repository.saveAll(validUserTokens);
    }
}
