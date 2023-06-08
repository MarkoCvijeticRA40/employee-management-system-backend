package com.mcm.EmployeeManagementSystem.validator.user;

import com.mcm.EmployeeManagementSystem.validator.MultiParamValidator;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import org.springframework.stereotype.Component;

@Component
public class FindUsersValidator implements MultiParamValidator<String> {

    @Override
    public ValidationReport validate(String... params) {
        ValidationReport report = new ValidationReport();
        if (params.length > 0 && params[0].isEmpty()) {
            report.addMessage("email", "Invalid input: Empty email parameter");
        }
        if (params.length > 1 && params[1] == null) {
            report.addMessage("name", "Invalid input: Null name parameter");
        }
        if (params.length > 2 && params[2].isEmpty()) {
            report.addMessage("surname", "Invalid input: Empty surname parameter");
        }
        if (params.length > 3 && params[3].isEmpty()) {
            report.addMessage("startDate", "Invalid input: Empty startDate parameter");
        }
        if (params.length > 4 && params[4].isEmpty()) {
            report.addMessage("endDate", "Invalid input: Empty endDate parameter");
        }
        report.setValid(report.getErrorMessages().isEmpty());
        return report;
    }
}
