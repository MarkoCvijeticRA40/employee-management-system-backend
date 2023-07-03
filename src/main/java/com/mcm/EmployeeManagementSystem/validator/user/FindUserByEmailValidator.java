package com.mcm.EmployeeManagementSystem.validator.user;

import com.mcm.EmployeeManagementSystem.constant.UserConstant;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.validator.ReadValidator;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@AllArgsConstructor
public class FindUserByEmailValidator implements ReadValidator<String> {
    private final UserStore store;

    @Override
    public ValidationReport validate(String email) {
        ValidationReport report = new ValidationReport(true, new HashMap<>());
        if (!store.exists(email)) {
            report.setValid(false);
            report.addMessage(UserConstant.USER, "user with passed email does not exist");
        }

        return report;
    }
}
