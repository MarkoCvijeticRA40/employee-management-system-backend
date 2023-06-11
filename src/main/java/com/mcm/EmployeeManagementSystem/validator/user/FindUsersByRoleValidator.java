package com.mcm.EmployeeManagementSystem.validator.user;

import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class FindUsersByRoleValidator implements Validator<String> {

    private final UserStore store;
    @Override
    public ValidationReport validate(String role) {
        ValidationReport report = new ValidationReport(true, new HashMap<>());
        if (role == null || role.trim().isEmpty()) {
            report.setValid(false);
            report.addMessage("value", "String cannot be blank");
        }
        return report;
    }
}
