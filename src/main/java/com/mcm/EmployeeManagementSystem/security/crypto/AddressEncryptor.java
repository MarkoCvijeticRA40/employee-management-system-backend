package com.mcm.EmployeeManagementSystem.security.crypto;

import com.mcm.EmployeeManagementSystem.model.Address;
import com.mcm.EmployeeManagementSystem.security.aes.AESKeyGenerator;
import com.mcm.EmployeeManagementSystem.security.aes.DataDecryption;
import com.mcm.EmployeeManagementSystem.security.aes.DataEncryption;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AddressEncryptor {

    private byte[] encryptionKeyBytes;
    private final Dotenv dotenv;
    private final DataEncryption dataEncryption;
    private final AESKeyGenerator aesKeyGenerator;

    public Address encryptAddress(Address address) {
        String encryptionKey = dotenv.get("ENCRYPTION_KEY");
        encryptionKeyBytes = aesKeyGenerator.decodeAESKey(encryptionKey);
        try {
            address.setId(address.getId());
            address.setCountry(dataEncryption.encryptData(address.getCountry(), encryptionKeyBytes));
            address.setCity(dataEncryption.encryptData(address.getCity(), encryptionKeyBytes));
            address.setStreet(dataEncryption.encryptData(address.getStreet(), encryptionKeyBytes));
            address.setNumber(dataEncryption.encryptData(address.getNumber(), encryptionKeyBytes));
            return address;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
