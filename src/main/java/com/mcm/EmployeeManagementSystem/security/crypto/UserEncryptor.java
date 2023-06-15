package com.mcm.EmployeeManagementSystem.security.crypto;

import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.security.aes.AESKeyGenerator;
import com.mcm.EmployeeManagementSystem.security.aes.DataEncryption;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserEncryptor {

    private byte[] encryptionKeyBytes;
    private final Dotenv dotenv;
    private final DataEncryption dataEncryption;
    private final AESKeyGenerator aesKeyGenerator;
    private final AddressEncryptor addressEncryptor;

    public User encryptUser(User user) {
        String encryptionKey = dotenv.get("ENCRYPTION_KEY");
        encryptionKeyBytes = aesKeyGenerator.decodeAESKey(encryptionKey);
        try {
            //user.setEmail(dataEncryption.encryptData(user.getEmail(), encryptionKeyBytes));
            user.setName(dataEncryption.encryptData(user.getName(), encryptionKeyBytes));
            user.setSurname(dataEncryption.encryptData(user.getSurname(), encryptionKeyBytes));
            user.setAddress(addressEncryptor.encryptAddress(user.getAddress()));
            user.setPhoneNum(dataEncryption.encryptData(user.getPhoneNum(), encryptionKeyBytes));
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
