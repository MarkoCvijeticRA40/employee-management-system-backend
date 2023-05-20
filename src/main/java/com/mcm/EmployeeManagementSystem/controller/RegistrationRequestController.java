package com.mcm.EmployeeManagementSystem.controller;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.RegistrationRequest;
import com.mcm.EmployeeManagementSystem.model.RegistrationRequestStatus;
import com.mcm.EmployeeManagementSystem.usecase.registrationrequest.ApproveRequestUseCase;
import com.mcm.EmployeeManagementSystem.usecase.registrationrequest.CreateRequestUseCase;
import com.mcm.EmployeeManagementSystem.usecase.registrationrequest.FindAllPendingRequestsUseCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@RestController
@RequestMapping("/requests")
public class RegistrationRequestController {
    private final CreateRequestUseCase createRequestUseCase;
    private final FindAllPendingRequestsUseCase findAllPendingRequestsUseCase;
    private final ApproveRequestUseCase approveRequestUseCase;

    @PostMapping
    public Response create(@RequestBody RegistrationRequest registrationRequest) {
        registrationRequest.setStatus(RegistrationRequestStatus.PENDING);
        registrationRequest.setSendTime(LocalDateTime.now());
        return createRequestUseCase.create(registrationRequest);
    }

    @GetMapping("/pending")
    public Response getAllPendingRequests() {
        return findAllPendingRequestsUseCase.find();
    }

    @PutMapping("approve/{id}")
    public Response approve(@PathVariable Long id) throws NoSuchAlgorithmException, InvalidKeyException {
        return approveRequestUseCase.approve(id);
    }
}
