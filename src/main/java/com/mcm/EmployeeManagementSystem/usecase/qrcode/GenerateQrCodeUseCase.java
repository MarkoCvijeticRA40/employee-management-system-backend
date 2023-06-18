package com.mcm.EmployeeManagementSystem.usecase.qrcode;

import com.google.zxing.WriterException;
import com.mcm.EmployeeManagementSystem.security.twofa.GoogleAuthenticatorBarCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class GenerateQrCodeUseCase {

    public String saveQRCodeImage(String barcode, String email) throws IOException, WriterException {
        String sanitizedEmail = email.replaceAll("[^a-zA-Z0-9]", "_");
        String fileName = sanitizedEmail + ".png";
        String filePath = "C:" + File.separator + "Users" + File.separator + "marko" + File.separator + "Desktop" + File.separator + "employee-management-system-backend" + File.separator + "qrCodes" + File.separator + fileName;
        new GoogleAuthenticatorBarCodeGenerator().createQRCode(barcode, filePath, 400, 400);
        return filePath;
    }

    public String generateGoogleAuthenticatorBarcode(String secretKey, String email) {
        return new GoogleAuthenticatorBarCodeGenerator().getGoogleAuthenticatorBarCode(secretKey, email);
    }
}
