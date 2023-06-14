package com.mcm.EmployeeManagementSystem.security.aes;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AESKeyGenerator {
    public String generateAESKey() throws Exception {
        // Generisanje AES ključa
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256); // Dužina ključa u bitima (ovde je 256 bita)
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyBytes = secretKey.getEncoded();
        // Prikazivanje generisanog ključa u heksadekadnom formatu
        StringBuilder sb = new StringBuilder();
        for (byte b : keyBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}