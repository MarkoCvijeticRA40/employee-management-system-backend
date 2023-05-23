package com.mcm.EmployeeManagementSystem.usecase.authentication;

import com.mcm.EmployeeManagementSystem.constant.EmailConstant;
import com.mcm.EmployeeManagementSystem.converter.UserConverter;
import com.mcm.EmployeeManagementSystem.dto.PasswordlessAuthenticationRequest;
import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.TokenLink;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.security.config.JwtService;
import com.mcm.EmployeeManagementSystem.security.token.TokenRepository;
import com.mcm.EmployeeManagementSystem.store.TokenLinkStore;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.usecase.email.SendEmailUseCase;
import com.mcm.EmployeeManagementSystem.usecase.link.GenerateTokenLinkUseCase;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.authentication.PasswordlessLoginValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class PasswordlessLoginUseCase {
    private final TokenRepository repository;
    private final PasswordlessLoginValidator validator;
    private final JwtService jwtService;
    private final UserConverter userConverter;
    private final UserStore userStore;
    private final RevokeAllUserTokensUseCase revokeAllUserTokensUseCase;
    private final SaveUserTokenUseCase saveUserTokenUseCase;
    private final GenerateTokenLinkUseCase generateTokenLinkUseCase;
    private final SendEmailUseCase sendEmailUseCase;
    private final TokenLinkStore tokenLinkStore;

    public Response login(PasswordlessAuthenticationRequest request) throws NoSuchAlgorithmException, InvalidKeyException {
        ValidationReport report = validator.validate(request.getEmail());
        if (report.isValid()) {
            User user = userStore.find(request.getEmail());
            var shortTermToken = jwtService.generateShortTermToken(userConverter.toEntity(user));
            revokeAllUserTokensUseCase.revokeAllUserTokens(userConverter.toEntity(user));
            saveUserTokenUseCase.saveUserToken(userConverter.toEntity(user), shortTermToken);
            String message = generateTokenLinkUseCase.generateTokenLink(shortTermToken);
            sendEmailUseCase.send(user.getEmail(), message, EmailConstant.LOGIN_SUBJECT);
            TokenLink tokenLink = new TokenLink(0l, message, false);
            tokenLinkStore.save(tokenLink);
        }

        return new Response(report, request.getEmail());
    }

}
