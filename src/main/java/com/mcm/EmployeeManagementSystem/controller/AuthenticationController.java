package com.mcm.EmployeeManagementSystem.controller;

import com.mcm.EmployeeManagementSystem.dto.AuthenticationRequest;
import com.mcm.EmployeeManagementSystem.dto.AuthenticationResponse;
import com.mcm.EmployeeManagementSystem.usecase.authentication.LoginUseCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@Setter
@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final LoginUseCase loginUseCase;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request) {
        return loginUseCase.authenticate(request);
    }
}
