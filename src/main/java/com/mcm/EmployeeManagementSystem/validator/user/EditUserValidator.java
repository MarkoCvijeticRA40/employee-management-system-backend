package com.mcm.EmployeeManagementSystem.validator.user;

import com.mcm.EmployeeManagementSystem.constant.AddressConstant;
import com.mcm.EmployeeManagementSystem.constant.RegistrationRequestConstant;
import com.mcm.EmployeeManagementSystem.constant.RoleConstant;
import com.mcm.EmployeeManagementSystem.constant.UserConstant;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Component
@RequiredArgsConstructor
public class EditUserValidator implements Validator<User> {
    @Override
    public ValidationReport validate(User user) {
        ValidationReport report = new ValidationReport(true, new HashMap<>());
        if (user == null) {
            report.setValid(false);
            report.addMessage(UserConstant.USER, "user is null");
        } else {
            if (isBlank(user.getEmail())) {
                report.setValid(false);
                report.addMessage(UserConstant.EMAIL, "email is blank");
            }
            if (isBlank(user.getPassword())) {
                report.setValid(false);
                report.addMessage(UserConstant.PASSWORD, "password is blank");
            }
            if (isBlank(user.getName())) {
                report.setValid(false);
                report.addMessage(UserConstant.NAME, "name is blank");
            }
            if (isBlank(user.getSurname())) {
                report.setValid(false);
                report.addMessage(UserConstant.SURNAME, "surname is blank");
            }
            if (isBlank(user.getAddress().getCountry())) {
                report.setValid(false);
                report.addMessage(AddressConstant.COUNTRY, "country is blank");
            }
            if (isBlank(user.getAddress().getCity())) {
                report.setValid(false);
                report.addMessage(AddressConstant.CITY, "city is blank");
            }
            if (isBlank(user.getAddress().getStreet())) {
                report.setValid(false);
                report.addMessage(AddressConstant.STREET, "street is blank");
            }
            if (isBlank(user.getAddress().getNumber())) {
                report.setValid(false);
                report.addMessage(AddressConstant.NUMBER, "number is blank");
            }
            if (isBlank(user.getPhoneNum())) {
                report.setValid(false);
                report.addMessage(UserConstant.PHONE_NUM, "phone num is blank");
            }
            if (isBlank(user.getTitle())) {
                report.setValid(false);
                report.addMessage(UserConstant.TITLE, "title is blank");
            }
            if ((user.getRoleNames().isEmpty())) {
                report.setValid(false);
                report.addMessage(UserConstant.ROLE_NAMES, "role name is blank");
            } else {
                if (!isRoleNameValid(user)) {
                    report.setValid(false);
                    report.addMessage(RegistrationRequestConstant.ROLE_NAME, "invalid role name");
                }
            }
            if (user.isAccountEnabled() != true) {
                report.setValid(false);
                report.addMessage(UserConstant.IS_ACCOUNT_ENABLED, "account is not enabled");
            }
            if (user.getStartOfWork() == null) {
                report.setValid(false);
                report.addMessage(UserConstant.START_OF_WORK, "start of work time is null");
            }
        }

        return report;
    }

    private static boolean isRoleNameValid(User user) {
        for (String role: user.getRoleNames()) {
            if (role == RoleConstant.SOFTWARE_ENGINEER || role == RoleConstant.HR_MANAGER || role == RoleConstant.PROJECT_MANAGER) {
                continue;
            }
            return true;
        }
        return false;
    }
}
