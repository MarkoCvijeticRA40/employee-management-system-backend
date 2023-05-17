package com.mcm.EmployeeManagementSystem.controller;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.RegistrationRequest;
import com.mcm.EmployeeManagementSystem.model.RegistrationRequestStatus;
import com.mcm.EmployeeManagementSystem.usecase.registrationrequest.CreateRegistrationRequestUseCase;
import com.mcm.EmployeeManagementSystem.usecase.registrationrequest.FindAllPendingRequestsUseCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@RestController
@RequestMapping("/requests")
public class RegistrationRequestController {
    private final CreateRegistrationRequestUseCase createRegistrationRequestUseCase;
    private final FindAllPendingRequestsUseCase findAllPendingRequestsUseCase;

    @PostMapping
    public Response create(@RequestBody RegistrationRequest registrationRequest) {
        registrationRequest.setStatus(RegistrationRequestStatus.PENDING);
        registrationRequest.setSendTime(LocalDateTime.now());
        return createRegistrationRequestUseCase.create(registrationRequest);
    }

    @GetMapping("/pending")
    public Response getAllPendingRequests() {
        return findAllPendingRequestsUseCase.find();
    }
}
