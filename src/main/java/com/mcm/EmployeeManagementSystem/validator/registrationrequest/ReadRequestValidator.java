package com.mcm.EmployeeManagementSystem.validator.registrationrequest;

import com.mcm.EmployeeManagementSystem.constant.RegistrationRequestConstant;
import com.mcm.EmployeeManagementSystem.store.RegistrationRequestStore;
import com.mcm.EmployeeManagementSystem.validator.ReadValidator;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class ReadRequestValidator implements ReadValidator<Long> {
    private final RegistrationRequestStore store;

    @Override
    public ValidationReport validate(Long id) {
        ValidationReport report = new ValidationReport(true, new HashMap<>());
        if (!store.exists(id)) {
            report.setValid(false);
            report.addMessage(RegistrationRequestConstant.REGISTRATION_REQUEST, "request with passed id does not exist");
        }

        return report;
    }
}
