package com.mcm.EmployeeManagementSystem.usecase.link;

import com.mcm.EmployeeManagementSystem.usecase.hmac.hmacutil.ConvertToSecretKeyUseCase;
import com.mcm.EmployeeManagementSystem.usecase.hmac.hmacutil.GenerateHMACUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class GenerateActivationLinkUseCase {
    @Value("${spring.secretKey}")
    private String secretKeyBase64;
    private final ConvertToSecretKeyUseCase convertToSecretKeyUseCase;
    private final GenerateHMACUseCase generateHMACUseCase;

    public String generateActivationLink(String userId) throws NoSuchAlgorithmException, InvalidKeyException {
        LocalDateTime expirationDateTime = LocalDateTime.now().plusHours(24);
        String expirationString = expirationDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        String dataToSign = userId + expirationString;
        String hmac = generateHMACUseCase.generate(dataToSign, convertToSecretKeyUseCase.convert(secretKeyBase64));

        return UriComponentsBuilder.fromUriString("http://localhost:443/users/activate")
                .queryParam("user", userId)
                .queryParam("expires", expirationString)
                .queryParam("hmac", hmac)
                .toUriString();

    }


}
