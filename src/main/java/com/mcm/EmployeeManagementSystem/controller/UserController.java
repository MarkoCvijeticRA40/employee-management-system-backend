package com.mcm.EmployeeManagementSystem.controller;

import com.mcm.EmployeeManagementSystem.usecase.hmac.hmacutil.VerifyHmacUseCase;
import com.mcm.EmployeeManagementSystem.usecase.link.IsActivationLinkUsedUseCase;
import com.mcm.EmployeeManagementSystem.usecase.link.SetLinkToUsedUseCase;
import com.mcm.EmployeeManagementSystem.usecase.user.ActivateAccountUseCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final VerifyHmacUseCase verifyHmacUseCase;
    private final ActivateAccountUseCase activateAccountUseCase;
    private final IsActivationLinkUsedUseCase isActivationLinkUsedUseCase;
    private final SetLinkToUsedUseCase setLinkToUsedUseCase;

    @GetMapping("/activate")
    public String activateUser(@RequestParam("user") String userId,
                               @RequestParam("expires") String expirationString,
                               @RequestParam("hmac") String hmac) throws NoSuchAlgorithmException, InvalidKeyException {
        String dataToSign = userId + expirationString;

        if (verifyHmacUseCase.verify(dataToSign, hmac)) {
            LocalDateTime expirationDateTime = LocalDateTime.parse(expirationString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
            if (LocalDateTime.now().isAfter(expirationDateTime)) {
                return "Activation link has expired.";
            }

            String activationLink = "http://localhost:8080/users/activate?user=" + userId + "&expires=" + expirationString + "&hmac=" + hmac;
            if (!isActivationLinkUsedUseCase.isUsed(activationLink)) {
                activateAccountUseCase.activate(Long.valueOf(userId));
                setLinkToUsedUseCase.setToUsed(activationLink);
            } else {
                return "Activation link is already used";
            }
            return "User account activated successfully.";
        } else {
            return "Invalid activation link.";
        }
    }

}
