package com.mcm.EmployeeManagementSystem.usecase.authentication;

import com.mcm.EmployeeManagementSystem.constant.UserConstant;
import com.mcm.EmployeeManagementSystem.dto.AuthenticationRequest;
import com.mcm.EmployeeManagementSystem.dto.AuthenticationResponse;
import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.security.aes.DataDecryption;
import com.mcm.EmployeeManagementSystem.security.crypto.DataDecryptor;
import com.mcm.EmployeeManagementSystem.security.crypto.DataEncryptor;
import com.mcm.EmployeeManagementSystem.security.twofa.TOTPCodeGenerator;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TwoFactorLoginUseCase {

    private final TOTPCodeGenerator totpCodeGenerator;
    private final UserStore userStore;
    private final LoginUseCase loginUseCase;
    private final DataDecryptor dataDecryptor;

    public boolean isUserCodeValid(AuthenticationRequest request, int oneTimeCode) {
        User user = userStore.find(request.getEmail());
        String oneTimeCodeString = String.valueOf(oneTimeCode);
        if (user != null) {
            String googleAuthenticationCode = totpCodeGenerator.getTOTPCode(dataDecryptor.decryptData(user.getSecretKey()));
            return oneTimeCodeString.equals(googleAuthenticationCode);
        }
        return false;
    }

    public Response twoFactorlogin(AuthenticationRequest authenticationRequest, int oneTimeCode){
        ValidationReport report = new ValidationReport();
        if(isUserCodeValid(authenticationRequest,oneTimeCode) == false){
            report.setValid(false);
            report.addMessage(UserConstant.SECRET_KEY,"Wrong code!");
            return new Response(report,authenticationRequest);
        }
        else {
            loginUseCase.authenticate(authenticationRequest);
            report.setValid(true);
        }
        return new Response(report,authenticationRequest);
    }
}
