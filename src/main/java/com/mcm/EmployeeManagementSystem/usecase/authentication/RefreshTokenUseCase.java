package com.mcm.EmployeeManagementSystem.usecase.authentication;

import com.mcm.EmployeeManagementSystem.converter.UserConverter;
import com.mcm.EmployeeManagementSystem.dto.AuthenticationResponse;
import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.security.config.JwtService;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.authentication.RefreshTokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenUseCase {
    private final RefreshTokenValidator validator;
    private final JwtService jwtService;
    private final UserStore userStore;
    private final UserConverter userConverter;
    private final RevokeAllUserTokensUseCase revokeAllUserTokensUseCase;
    private final SaveUserTokenUseCase saveUserTokenUseCase;

    public Response refresh(String refreshToken) {
        ValidationReport report = validator.validate(refreshToken);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (report.isValid()) {
            User user = userStore.find(jwtService.extractUsername(refreshToken));
            var accessToken = jwtService.generateToken(userConverter.toEntity(user));
            revokeAllUserTokensUseCase.revokeAllUserTokens(userConverter.toEntity(user));
            saveUserTokenUseCase.saveUserToken(userConverter.toEntity(user), accessToken);
            authenticationResponse = AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }

        return new Response(report, authenticationResponse);

    }
}
