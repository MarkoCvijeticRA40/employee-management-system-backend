package com.mcm.EmployeeManagementSystem.usecase.authentication;

import com.google.zxing.WriterException;
import com.mcm.EmployeeManagementSystem.dto.AuthenticationRequest;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.security.crypto.DataEncryptor;
import com.mcm.EmployeeManagementSystem.security.twofa.GoogleAuthenticatorBarCodeGenerator;
import com.mcm.EmployeeManagementSystem.security.twofa.TOTPCodeGenerator;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.usecase.email.SendEmailUseCase;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class TwoFactorLoginUseCase {

    private final TOTPCodeGenerator totpCodeGenerator;
    private final UserStore userStore;
    private final DataEncryptor dataEncryptor;
    private final SendEmailUseCase sendEmailUseCase;

    public boolean isUserCodeValid(AuthenticationRequest request, int oneTimeCode) {
        User user = userStore.find(request.getEmail());
        String oneTimeCodeString = String.valueOf(oneTimeCode);
        if (user != null) {
            try {
                String googleAuthenticationCode = totpCodeGenerator.getTOTPCode(user.getSecretKey());
                if(oneTimeCodeString.equals(googleAuthenticationCode))
                {
                    String email = dataEncryptor.encryptData(request.getEmail());
                    String barcode = generateGoogleAuthenticatorBarcode(user.getSecretKey(), user.getEmail());
                    String filePath = saveQRCodeImage(barcode, email);
                    sendQRCodeEmail(request.getEmail(), filePath);
                    return true;
                }
            } catch (Exception e) {
                handleUserCodeValidationException(e);
            }
        }
        return false;
    }

    private String generateGoogleAuthenticatorBarcode(String secretKey, String email) {
        String sanitizedEmail = email.replaceAll("[^a-zA-Z0-9]", "_");
        return new GoogleAuthenticatorBarCodeGenerator().getGoogleAuthenticatorBarCode(secretKey, email);
    }

    private String saveQRCodeImage(String barcode, String email) throws IOException, WriterException {
        String sanitizedEmail = email.replaceAll("[^a-zA-Z0-9]", "_");
        String fileName = sanitizedEmail + ".png";
        String filePath = "C:" + File.separator + "Users" + File.separator + "marko" + File.separator + "Desktop" + File.separator + "employee-management-system-backend" + File.separator + "qrCodes" + File.separator + fileName;
        new GoogleAuthenticatorBarCodeGenerator().createQRCode(barcode, filePath, 300, 300);
        return filePath;
    }

    private void sendQRCodeEmail(String email, String filePath) throws MailException, MessagingException {
        sendEmailUseCase.sendQRCode(email, filePath, "QR Code for Two-Factor Authentication");
    }

    private void handleUserCodeValidationException(Exception e) {
        e.printStackTrace();
        // Add exception handling as needed
    }
}
