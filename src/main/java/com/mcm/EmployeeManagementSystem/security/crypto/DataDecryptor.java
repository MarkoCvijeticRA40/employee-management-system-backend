package com.mcm.EmployeeManagementSystem.security.crypto;

import com.mcm.EmployeeManagementSystem.security.aes.AESKeyGenerator;
import com.mcm.EmployeeManagementSystem.security.aes.DataDecryption;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataDecryptor {

    private byte[] encryptionKeyBytes;
    private final Dotenv dotenv;
    private final DataDecryption dataDecryption;
    private final AESKeyGenerator aesKeyGenerator;
    private final AddressEncryptor addressEncryptor;

    public String decryptData(String encryptedData) {
        String encryptionKey = dotenv.get("ENCRYPTION_KEY");
        encryptionKeyBytes = aesKeyGenerator.decodeAESKey(encryptionKey);
        try {
            String decryptedData = dataDecryption.decryptData(encryptedData, encryptionKeyBytes);
            // Ovde možete implementirati odgovarajuću logiku za obradu dekriptovanih podataka i konverziju u odgovarajući tip.
            return decryptedData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
