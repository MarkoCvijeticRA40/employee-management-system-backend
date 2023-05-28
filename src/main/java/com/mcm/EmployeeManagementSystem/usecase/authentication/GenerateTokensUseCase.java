package com.mcm.EmployeeManagementSystem.usecase.authentication;

import com.mcm.EmployeeManagementSystem.dto.AuthenticationResponse;
import com.mcm.EmployeeManagementSystem.dto.GenerateTokensResponse;
import com.mcm.EmployeeManagementSystem.security.config.JwtService;
import com.mcm.EmployeeManagementSystem.security.token.Token;
import com.mcm.EmployeeManagementSystem.security.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenerateTokensUseCase {
    private final TokenRepository repository;
    private final JwtService jwtService;
    private final RevokeAllUserTokensUseCase revokeAllUserTokensUseCase;
    private final SaveUserTokenUseCase saveUserTokenUseCase;

    public GenerateTokensResponse generate(String tokenValue) {
        Optional<Token> tokenOptional = repository.findByToken(tokenValue);
        Token token = tokenOptional.get();
        var jwtToken = jwtService.generateToken(token.getUser());
        var refreshToken = jwtService.generateRefreshToken(token.getUser());
        revokeAllUserTokensUseCase.revokeAllUserTokens(token.getUser());
        saveUserTokenUseCase.saveUserToken(token.getUser(), jwtToken);
        return GenerateTokensResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .roleName(token.getUser().getRoles().get(0).getName())
                .email(token.getUser().getEmail())
                .build();

    }

}
