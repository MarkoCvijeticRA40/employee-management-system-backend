package com.mcm.EmployeeManagementSystem.usecase.link;

import com.mcm.EmployeeManagementSystem.usecase.hmac.hmacutil.ConvertToSecretKeyUseCase;
import com.mcm.EmployeeManagementSystem.usecase.hmac.hmacutil.GenerateHMACUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class GenerateTokenLinkUseCase {
    @Value("${spring.secretKey}")
    private String secretKeyBase64;
    private final ConvertToSecretKeyUseCase convertToSecretKeyUseCase;
    private final GenerateHMACUseCase generateHMACUseCase;

    public String generateTokenLink(String token) throws NoSuchAlgorithmException, InvalidKeyException {
        String hmac = generateHMACUseCase.generate(token, convertToSecretKeyUseCase.convert(secretKeyBase64));

        return UriComponentsBuilder.fromUriString("https://localhost:443/auth/verify-sttoken")
                .queryParam("token", token)
                .queryParam("hmac", hmac)
                .toUriString();
    }
}
