package com.mcm.EmployeeManagementSystem.security.crypto;

import com.mcm.EmployeeManagementSystem.security.aes.AESKeyGenerator;
import com.mcm.EmployeeManagementSystem.security.aes.DataEncryption;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataEncryptor {

    private byte[] encryptionKeyBytes;
    private final Dotenv dotenv;
    private final DataEncryption dataEncryption;
    private final AESKeyGenerator aesKeyGenerator;

    public String encryptData(Object data) {
        String encryptionKey = dotenv.get("ENCRYPTION_KEY");
        encryptionKeyBytes = aesKeyGenerator.decodeAESKey(encryptionKey);
        try {
            String stringData = String.valueOf(data);
            return dataEncryption.encryptData(stringData, encryptionKeyBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}