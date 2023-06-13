package com.mcm.EmployeeManagementSystem.usecase.authentication;

import com.mcm.EmployeeManagementSystem.constant.EmailConstant;
import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.usecase.email.SendEmailUseCase;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.user.EditUserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class ForgotPasswordUseCase {

    private final SendEmailUseCase sendEmailUseCase;
    private final EditUserValidator validator;
    private final UserStore store;
    private final PasswordEncoder passwordEncoder;

    public Response generateAndSendPassword(String email) {
        User user = store.find(email);
        ValidationReport report = validator.validate(user);
        if (report.isValid()) {
            String password = generatePassword();
            String message = "Your password is " + password;
            user.setPassword(passwordEncoder.encode(password));
            store.save(user);
            sendEmailUseCase.send(email, message, EmailConstant.ACTIVATION_SUBJECT);
        }
        return new Response(report, user);
    }

    public String generatePassword() {
        String password = generateRandomPassword();
        if(password.isEmpty()){
            password = generateRandomPassword();
        }
        return password;
    }

    public String generateRandomPassword() {
        String allowedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@#$%^&+=";
        SecureRandom random = new SecureRandom();
        StringBuilder passwordBuilder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int randomIndex = random.nextInt(allowedCharacters.length());
            char randomCharacter = allowedCharacters.charAt(randomIndex);
            passwordBuilder.append(randomCharacter);
        }
        return passwordBuilder.toString();
    }
}
