package com.mcm.EmployeeManagementSystem.controller;

import com.mcm.EmployeeManagementSystem.dto.*;
import com.mcm.EmployeeManagementSystem.handler.exceptions.InvalidLinkException;
import com.mcm.EmployeeManagementSystem.handler.exceptions.InvalidTokenException;
import com.mcm.EmployeeManagementSystem.handler.exceptions.TokenLinkIsAlreadyUsedException;
import com.mcm.EmployeeManagementSystem.security.config.JwtService;
import com.mcm.EmployeeManagementSystem.usecase.authentication.*;
import com.mcm.EmployeeManagementSystem.usecase.hmac.hmacutil.VerifyHmacUseCase;
import com.mcm.EmployeeManagementSystem.usecase.link.IsTokenLinkUsedUseCase;
import com.mcm.EmployeeManagementSystem.usecase.link.SetTokenLinkToUsedUseCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Getter
@Setter
@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final LoginUseCase loginUseCase;
    private final PasswordlessLoginUseCase passwordlessLoginUseCase;
    private final VerifyHmacUseCase verifyHmacUseCase;
    private final IsTokenLinkUsedUseCase isTokenLinkUsedUseCase;
    private final SetTokenLinkToUsedUseCase setTokenLinkToUsedUseCase;
    private final IsShortTermTokenValidUseCase isShortTermTokenValidUseCase;
    private final JwtService jwtService;
    private final GenerateTokensUseCase generateTokensUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request) {
        return loginUseCase.authenticate(request);
    }

    @GetMapping("/generate-sttoken")
    public Response generate(@RequestBody PasswordlessAuthenticationRequest request) throws NoSuchAlgorithmException, InvalidKeyException {
        return passwordlessLoginUseCase.login(request);
    }

    @GetMapping("/verify-sttoken")
    public ResponseEntity<AuthenticationResponse> verify(@RequestParam("token") String token, @RequestParam("hmac") String hmac) throws NoSuchAlgorithmException, InvalidKeyException {
        String dataToSign = token;
        URI redirectUrl = URI.create("");
        if (verifyHmacUseCase.verify(dataToSign, hmac)) {
            String tokenLink = "http://localhost:443/auth/verify-sttoken?token=" + token + "&hmac=" + hmac;
            if (!isTokenLinkUsedUseCase.isUsed(tokenLink)) {
                setTokenLinkToUsedUseCase.setToUsed(tokenLink);
                if (isShortTermTokenValidUseCase.isValid(token)) {
                    AuthenticationResponse response = generateTokensUseCase.generate(token);
                    HttpHeaders responseHeaders = new HttpHeaders();
                    responseHeaders.setLocation(redirectUrl);
                    return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
                } else {
                    throw new InvalidTokenException();
                }
            } else {
                throw new TokenLinkIsAlreadyUsedException();
            }
        } else {
            throw new InvalidLinkException();
        }
    }

    @GetMapping("/refresh-token")
    public Response refresh(@RequestBody RefreshTokenRequest request) throws NoSuchAlgorithmException, InvalidKeyException {
        return refreshTokenUseCase.refresh(request.getRefreshToken());
    }

}
