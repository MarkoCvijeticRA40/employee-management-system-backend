package com.mcm.EmployeeManagementSystem.usecase.registrationrequest;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.entity.RegistrationRequestEntityStatus;
import com.mcm.EmployeeManagementSystem.model.RegistrationRequest;
import com.mcm.EmployeeManagementSystem.store.RegistrationRequestStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class FindAllPendingRequestsUseCase {
    private final RegistrationRequestStore store;

    public Response find() {
        ValidationReport report = new ValidationReport(true, new HashMap<>());
        List<RegistrationRequest> registrationRequests = store.find(RegistrationRequestEntityStatus.PENDING);

        return new Response(report, registrationRequests);
    }
}
