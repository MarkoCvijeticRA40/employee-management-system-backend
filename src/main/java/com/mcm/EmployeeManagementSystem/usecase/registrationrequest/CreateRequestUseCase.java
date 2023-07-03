package com.mcm.EmployeeManagementSystem.usecase.registrationrequest;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.RegistrationRequest;
import com.mcm.EmployeeManagementSystem.security.crypto.AddressEncryptor;
import com.mcm.EmployeeManagementSystem.security.twofa.SecretKeyGenerator;
import com.mcm.EmployeeManagementSystem.store.RegistrationRequestStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.registrationrequest.CreateRegistrationRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateRequestUseCase {
    private final RegistrationRequestStore store;
    private final CreateRegistrationRequestValidator validator;
    private final PasswordEncoder passwordEncoder;
    private final AddressEncryptor addressEncryptor;

    public Response create(RegistrationRequest registrationRequest) {
        ValidationReport report = validator.validate(registrationRequest);
        RegistrationRequest createdRegistrationRequest = new RegistrationRequest();
        registrationRequest.setAddress(registrationRequest.getAddress());
        if (report.isValid()) {
            registrationRequest.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            createdRegistrationRequest = store.save(registrationRequest);
        }

        return new Response(report, createdRegistrationRequest.getId());
    }
}
