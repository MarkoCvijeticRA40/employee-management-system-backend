package com.mcm.EmployeeManagementSystem.validator.authentication;

import com.mcm.EmployeeManagementSystem.constant.EmailConstant;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Component
@RequiredArgsConstructor
public class PasswordlessLoginValidator {
    private final UserStore store;

    public ValidationReport validate(String email) {
        ValidationReport report = new ValidationReport(true, new HashMap<>());
        if (isBlank(email)) {
            report.setValid(false);
            report.addMessage(EmailConstant.EMAIL, "email is blank");
        } else {
            if (!store.exists(email)) {
                report.setValid(false);
                report.addMessage(EmailConstant.EMAIL, "user with passed email does not exist");
            } else {
                User user = store.find(email);
                if (!user.isAccountEnabled()) {
                    report.setValid(false);
                    report.addMessage(EmailConstant.EMAIL, "account is not activated");
                }
            }
        }

        return report;
    }

}
