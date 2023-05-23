package com.mcm.EmployeeManagementSystem.usecase.hmac.hmacutil;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class VerifyHmacUseCase {
    @Value("${spring.secretKey}")
    private String secretKeyBase64;
    private final ConvertToSecretKeyUseCase convertToSecretKeyUseCase;

    public boolean verify(String data, String hmac) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(convertToSecretKeyUseCase.convert(secretKeyBase64));
        byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        String calculatedHmac = DatatypeConverter.printHexBinary(rawHmac);
        return calculatedHmac.equals(hmac);
    }
}
