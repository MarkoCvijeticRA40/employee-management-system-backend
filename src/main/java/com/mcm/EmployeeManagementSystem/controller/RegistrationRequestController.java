package com.mcm.EmployeeManagementSystem.controller;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.RegistrationRequest;
import com.mcm.EmployeeManagementSystem.model.RegistrationRequestStatus;
import com.mcm.EmployeeManagementSystem.usecase.CreateRegistrationRequestUseCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@RestController
@RequestMapping("/requests")
public class RegistrationRequestController {
    private final CreateRegistrationRequestUseCase createRegistrationRequestUseCase;

    @PostMapping
    public Response create(@RequestBody RegistrationRequest registrationRequest) {
        registrationRequest.setStatus(RegistrationRequestStatus.PENDING);
        registrationRequest.setSendTime(LocalDateTime.now());
        return createRegistrationRequestUseCase.create(registrationRequest);
    }
}
