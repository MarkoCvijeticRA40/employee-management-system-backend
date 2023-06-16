package com.mcm.EmployeeManagementSystem.usecase.authentication;

import com.mcm.EmployeeManagementSystem.dto.AuthenticationRequest;
import com.mcm.EmployeeManagementSystem.dto.AuthenticationResponse;
import com.mcm.EmployeeManagementSystem.repository.UserRepository;
import com.mcm.EmployeeManagementSystem.security.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final SaveUserTokenUseCase saveUserTokenUseCase;
    private final RevokeAllUserTokensUseCase revokeAllUserTokensUseCase;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokensUseCase.revokeAllUserTokens(user);
        saveUserTokenUseCase.saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


}
