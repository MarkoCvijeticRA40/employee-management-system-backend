package com.mcm.EmployeeManagementSystem.validator.projectuserassignment;

import com.mcm.EmployeeManagementSystem.constant.UserConstant;
import com.mcm.EmployeeManagementSystem.store.ProjectUserAssignmentStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class FindProjectUserAssignmentValidator implements Validator<Long> {
    private final ProjectUserAssignmentStore store;

    @Override
    public ValidationReport validate(Long id) {
        ValidationReport report = new ValidationReport(true, new HashMap<>());
        if (!store.exists(id)) {
            report.setValid(false);
            report.addMessage(UserConstant.USER, "user with passed id does not exist");
        }

        return report;
    }
}
