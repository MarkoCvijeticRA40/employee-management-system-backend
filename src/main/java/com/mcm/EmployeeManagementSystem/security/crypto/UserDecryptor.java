package com.mcm.EmployeeManagementSystem.security.crypto;

import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.security.aes.AESKeyGenerator;
import com.mcm.EmployeeManagementSystem.security.aes.DataDecryption;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserDecryptor {

    private byte[] encryptionKeyBytes;
    private final Dotenv dotenv;
    private final DataDecryption dataDecryption;
    private final AESKeyGenerator aesKeyGenerator;
    private final AddressDecryptor addressDecryptor;

    public User decryptUser(User user) {
        String encryptionKey = dotenv.get("ENCRYPTION_KEY");
        encryptionKeyBytes = aesKeyGenerator.decodeAESKey(encryptionKey);
        try {
            //user.setEmail(dataDecryption.decryptData(user.getEmail(), encryptionKeyBytes));
            user.setName(dataDecryption.decryptData(user.getName(), encryptionKeyBytes));
            user.setSurname(dataDecryption.decryptData(user.getSurname(), encryptionKeyBytes));
            user.setAddress(addressDecryptor.decryptAddress(user.getAddress()));
            user.setPhoneNum(dataDecryption.decryptData(user.getPhoneNum(), encryptionKeyBytes));
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
