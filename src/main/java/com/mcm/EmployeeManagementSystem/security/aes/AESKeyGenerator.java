package com.mcm.EmployeeManagementSystem.security.aes;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AESKeyGenerator {
    public String generateAESKey() throws Exception {
        // Generating AES key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256); // Key length in bits (here it's 256 bits)
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyBytes = secretKey.getEncoded();
        // Displaying the generated key in hexadecimal format
        StringBuilder sb = new StringBuilder();
        for (byte b : keyBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public byte[] decodeAESKey(String hexKey) {
        int length = hexKey.length();
        byte[] keyBytes = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            keyBytes[i / 2] = (byte) ((Character.digit(hexKey.charAt(i), 16) << 4)
                    + Character.digit(hexKey.charAt(i + 1), 16));
        }
        return keyBytes;
    }
}
