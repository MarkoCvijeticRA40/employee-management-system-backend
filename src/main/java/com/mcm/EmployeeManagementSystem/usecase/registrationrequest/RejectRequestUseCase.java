package com.mcm.EmployeeManagementSystem.usecase.registrationrequest;

import com.mcm.EmployeeManagementSystem.constant.EmailConstant;
import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.RegistrationRequest;
import com.mcm.EmployeeManagementSystem.model.RegistrationRequestStatus;
import com.mcm.EmployeeManagementSystem.store.RegistrationRequestStore;
import com.mcm.EmployeeManagementSystem.usecase.email.SendEmailUseCase;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.registrationrequest.ChangeRequestStatusValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RejectRequestUseCase {
    private final RegistrationRequestStore store;
    private final ChangeRequestStatusValidator validator;
    private final SendEmailUseCase sendEmailUseCase;

    public Response reject(Long requestId, String reasonForRejection) {
        ValidationReport report = validator.validate(requestId);
        if (report.isValid()) {
            RegistrationRequest request = store.find(requestId);
            request.setStatus(RegistrationRequestStatus.REJECTED);
            store.save(request);
            sendEmailUseCase.send(request.getEmail(), reasonForRejection, EmailConstant.REJECTION_SUBJECT);
        }

        return new Response(report, requestId);
    }
}
