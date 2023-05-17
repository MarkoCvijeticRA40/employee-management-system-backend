package com.mcm.EmployeeManagementSystem.usecase.registrationrequest;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.RegistrationRequest;
import com.mcm.EmployeeManagementSystem.store.RegistrationRequestStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.registrationrequest.CreateRegistrationRequestValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateRegistrationRequestUseCase {
    private final RegistrationRequestStore store;
    private final CreateRegistrationRequestValidator validator;

    public Response create(RegistrationRequest registrationRequest) {
        ValidationReport report = validator.validate(registrationRequest);
        RegistrationRequest createdRegistrationRequest = new RegistrationRequest();
        if (report.isValid()) {
            createdRegistrationRequest = store.save(registrationRequest);
        }

        return new Response(report, createdRegistrationRequest.getId());
    }
}
