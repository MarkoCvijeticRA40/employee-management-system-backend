package com.mcm.EmployeeManagementSystem.validator.authentication;

import com.mcm.EmployeeManagementSystem.constant.AuthenticationConstant;
import com.mcm.EmployeeManagementSystem.constant.EmailConstant;
import com.mcm.EmployeeManagementSystem.constant.UserConstant;
import com.mcm.EmployeeManagementSystem.converter.UserConverter;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.security.config.JwtService;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Component
@RequiredArgsConstructor
public class RefreshTokenValidator {
    private final JwtService jwtService;
    private final UserStore userStore;
    private final UserConverter userConverter;

    public ValidationReport validate(String refreshToken) {
        ValidationReport report = new ValidationReport(true, new HashMap<>());
        if (isBlank(refreshToken)) {
            report.setValid(false);
            report.addMessage(AuthenticationConstant.REFRESH_TOKEN, "refresh token is blank");
        } else {
            String email = jwtService.extractUsername(refreshToken);
            if (isBlank(email)) {
                report.setValid(false);
                report.addMessage(EmailConstant.EMAIL, "there is no username in the refresh token");
            } else {
                User user = userStore.find(email);
                if (!jwtService.isTokenValid(refreshToken, userConverter.toEntity(user))) {
                    report.setValid(false);
                    report.addMessage(AuthenticationConstant.REFRESH_TOKEN, "refresh token is not valid");
                }
                //Dodato zbog funkcionalnosti za blokiranje korisnika
                if(user.isAccountEnabled() == false){
                    report.setValid(false);
                    report.addMessage(UserConstant.IS_ACCOUNT_ENABLED,"user is not enabled");
                }
                if(user.getStartOfWork() == null) {
                    report.setValid(false);
                    report.addMessage(UserConstant.START_OF_WORK,"user do not start working");
                }
            }
        }
        return report;
    }
}
