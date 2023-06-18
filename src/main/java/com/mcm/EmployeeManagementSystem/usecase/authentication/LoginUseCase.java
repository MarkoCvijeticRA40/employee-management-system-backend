package com.mcm.EmployeeManagementSystem.usecase.authentication;

import com.mcm.EmployeeManagementSystem.dto.AuthenticationRequest;
import com.mcm.EmployeeManagementSystem.dto.AuthenticationResponse;
import com.mcm.EmployeeManagementSystem.repository.UserRepository;
import com.mcm.EmployeeManagementSystem.security.config.JwtService;
import com.mcm.EmployeeManagementSystem.usecase.log.ShouldWarnAdminUseCase;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final SaveUserTokenUseCase saveUserTokenUseCase;
    private final RevokeAllUserTokensUseCase revokeAllUserTokensUseCase;
    private final PasswordEncoder passwordEncoder;
    private final ShouldWarnAdminUseCase shouldWarnAdminUseCase;
    private static final Logger logger = LogManager.getLogger(LoginUseCase.class);

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        if (repository.findByEmail(request.getEmail()).isEmpty()) {
            logger.info("User with email: " + request.getEmail() + " does not exist");
            shouldWarnAdminUseCase.check("INFO", "LoginUseCase", "User with email: " + request.getEmail() + " does not exist");
        } else {
            var userOptional = repository.findByEmail(request.getEmail());
            var user = userOptional.get();
            if (!user.isEnabled()) {
                logger.info("User with email: " + request.getEmail() + " is not enabled");
                shouldWarnAdminUseCase.check("INFO", "LoginUseCase", "User with email: " + request.getEmail() + " is disabled or not approved");
            }
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                logger.info("User's with email: " + request.getEmail() + " password is not correct");
                shouldWarnAdminUseCase.check("INFO", "LoginUseCase", "User's with email: " + request.getEmail() + " password is not correct");
            }
        }

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
        logger.info("User with email: " + request.getEmail() + " log in successfully");
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


}
