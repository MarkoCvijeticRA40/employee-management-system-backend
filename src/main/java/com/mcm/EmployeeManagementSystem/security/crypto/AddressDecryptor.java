package com.mcm.EmployeeManagementSystem.security.crypto;

import com.mcm.EmployeeManagementSystem.model.Address;
import com.mcm.EmployeeManagementSystem.security.aes.AESKeyGenerator;
import com.mcm.EmployeeManagementSystem.security.aes.DataDecryption;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AddressDecryptor {

    private byte[] encryptionKeyBytes;
    private final Dotenv dotenv;
    private final DataDecryption dataDecryption;
    private final AESKeyGenerator aesKeyGenerator;

    public Address decryptAddress(Address address) {
        String encryptionKey = dotenv.get("ENCRYPTION_KEY");
        encryptionKeyBytes = aesKeyGenerator.decodeAESKey(encryptionKey);
        try {
            address.setCountry(dataDecryption.decryptData(address.getCountry(), encryptionKeyBytes));
            address.setCity(dataDecryption.decryptData(address.getCity(), encryptionKeyBytes));
            address.setStreet(dataDecryption.decryptData(address.getStreet(), encryptionKeyBytes));
            address.setNumber(dataDecryption.decryptData(address.getNumber(), encryptionKeyBytes));
            return address;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
