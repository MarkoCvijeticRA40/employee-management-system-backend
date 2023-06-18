package com.mcm.EmployeeManagementSystem.usecase.qrcode;

import com.google.zxing.WriterException;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.security.crypto.DataDecryptor;
import com.mcm.EmployeeManagementSystem.usecase.email.SendEmailUseCase;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SendQrCodeUseCase {

    private final GenerateQrCodeUseCase generateQrCodeUseCase;
    private final DataDecryptor dataDecryptor;
    private final SendEmailUseCase sendEmailUseCase;

    public void send2FALoginQrCode(User user) {
        String barcode = generateQrCodeUseCase.generateGoogleAuthenticatorBarcode(dataDecryptor.decryptData(user.getSecretKey()),dataDecryptor.decryptData(user.getEmail()));
        try {
            String filePath = generateQrCodeUseCase.saveQRCodeImage(barcode,user.getEmail());
            sendEmailUseCase.sendQRCode(dataDecryptor.decryptData(user.getEmail()), "This is your QR code for two-factor login to the system!", "Scan this QR code in your Google Authenticator app!", filePath);
        } catch (MessagingException | IOException | WriterException e) {
            // Handle the exception (e.g., log the error, display a user-friendly message)
            e.printStackTrace();
        }
    }
}
