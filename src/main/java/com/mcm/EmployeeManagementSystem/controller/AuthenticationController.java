package com.mcm.EmployeeManagementSystem.controller;

import com.mcm.EmployeeManagementSystem.dto.AuthenticationRequest;
import com.mcm.EmployeeManagementSystem.dto.AuthenticationResponse;
import com.mcm.EmployeeManagementSystem.dto.GenerateTokensResponse;
import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.handler.exceptions.InvalidLinkException;
import com.mcm.EmployeeManagementSystem.handler.exceptions.InvalidTokenException;
import com.mcm.EmployeeManagementSystem.handler.exceptions.TokenLinkIsAlreadyUsedException;
import com.mcm.EmployeeManagementSystem.security.config.JwtService;
import com.mcm.EmployeeManagementSystem.security.crypto.DataEncryptor;
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
@CrossOrigin(origins = "http://localhost:4200")
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
    private final ForgotPasswordUseCase forgotPasswordUseCase;
    private final TwoFactorLoginUseCase twoFactorLoginUseCase;
    private final DataEncryptor dataEncryptor;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request) {
        request.setEmail(dataEncryptor.encryptData(request.getEmail()));
        return loginUseCase.authenticate(request);
    }

    @PostMapping("/two-factor/login")
    public AuthenticationResponse twoFactorLogin(@RequestBody AuthenticationRequest request, @RequestParam Integer oneTimeCode) {
        if(twoFactorLoginUseCase.isUserCodeValid(request,oneTimeCode) == true){
            request.setEmail(dataEncryptor.encryptData(request.getEmail()));
            return loginUseCase.authenticate(request);
        }
        request.setEmail("Wrong code");
        return loginUseCase.authenticate(request);
    }

    @GetMapping("/generate-sttoken/{email}")
    public Response generate(@PathVariable String email) throws NoSuchAlgorithmException, InvalidKeyException {
        return passwordlessLoginUseCase.login(email);
    }

    @GetMapping("/verify-sttoken")
    public ResponseEntity<GenerateTokensResponse> verify(@RequestParam("token") String token, @RequestParam("hmac") String hmac) throws NoSuchAlgorithmException, InvalidKeyException {
        String dataToSign = token;
        if (verifyHmacUseCase.verify(dataToSign, hmac)) {
            String tokenLink = "https://localhost:443/auth/verify-sttoken?token=" + token + "&hmac=" + hmac;
            if (!isTokenLinkUsedUseCase.isUsed(tokenLink)) {
                setTokenLinkToUsedUseCase.setToUsed(tokenLink);
                if (isShortTermTokenValidUseCase.isValid(token)) {
                    GenerateTokensResponse response = generateTokensUseCase.generate(token);
                    HttpHeaders responseHeaders = new HttpHeaders();
                    if (response.getRoleName().equals("Administrator")) {
                        responseHeaders.setLocation(URI.create("http://localhost:4200/administrator/welcome/" + response.getAccessToken() + "/" + response.getRefreshToken() + "/" + response.getEmail()));
                    } else if (response.getRoleName().equals("Software engineer")) {
                        responseHeaders.setLocation(URI.create("http://localhost:4200/softwareengineer/welcome/" + response.getAccessToken() + "/" + response.getRefreshToken() + "/" + response.getEmail()));
                    } else if (response.getRoleName().equals("Project manager")) {
                        responseHeaders.setLocation(URI.create("http://localhost:4200/projectmanager/welcome/" + response.getAccessToken() + "/" + response.getRefreshToken() + "/" + response.getEmail()));
                    } else if (response.getRoleName().equals("HR manager")) {
                        responseHeaders.setLocation(URI.create("http://localhost:4200/hrmanager/welcome/" + response.getAccessToken() + "/" + response.getRefreshToken() + "/" + response.getEmail()));
                    }
                    return new ResponseEntity<>(responseHeaders, HttpStatus.SEE_OTHER);
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

    @GetMapping("/refresh-token/{refreshToken}")
    public Response refresh(@PathVariable String refreshToken) throws NoSuchAlgorithmException, InvalidKeyException {
        return refreshTokenUseCase.refresh(refreshToken);
    }

    @PutMapping("/forgot-password/{email}")
    public Response forgotPassword(@PathVariable String email) {
        return forgotPasswordUseCase.generateAndSendPassword(email);
    }
}
