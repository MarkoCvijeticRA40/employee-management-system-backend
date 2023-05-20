package com.mcm.EmployeeManagementSystem.usecase.hmac.hmacutil;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class ConvertToSecretKeyUseCase {

    public SecretKey convert(String secretKeyBase64) {
        byte[] decodedKey = Base64.getDecoder().decode(secretKeyBase64);
        return new SecretKeySpec(decodedKey, "HmacSHA256");
    }
}
