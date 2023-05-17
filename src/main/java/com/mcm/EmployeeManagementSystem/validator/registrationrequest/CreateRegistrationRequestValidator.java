package com.mcm.EmployeeManagementSystem.validator.registrationrequest;

import com.mcm.EmployeeManagementSystem.constant.AddressConstant;
import com.mcm.EmployeeManagementSystem.constant.RegistrationRequestConstant;
import com.mcm.EmployeeManagementSystem.model.RegistrationRequest;
import com.mcm.EmployeeManagementSystem.model.RegistrationRequestStatus;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.Validator;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Component
public class CreateRegistrationRequestValidator implements Validator<RegistrationRequest> {
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    private static boolean isValid(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    @Override
    public ValidationReport validate(RegistrationRequest registrationRequest) {
        ValidationReport report = new ValidationReport(true, new HashMap<>());
        if (registrationRequest == null) {
            report.setValid(false);
            report.addMessage(RegistrationRequestConstant.REGISTRATION_REQUEST, "registration request is null");
        } else {
            if (isBlank(registrationRequest.getEmail())) {
                report.setValid(false);
                report.addMessage(RegistrationRequestConstant.EMAIL, "email is blank");
            }
            if (isBlank(registrationRequest.getPassword())) {
                report.setValid(false);
                report.addMessage(RegistrationRequestConstant.PASSWORD, "password is blank");
            } else {
                if (!isValid(registrationRequest.getPassword())) {
                    report.setValid(false);
                    report.addMessage(RegistrationRequestConstant.PASSWORD, "password format is not valid");
                }
            }
            if (isBlank(registrationRequest.getName())) {
                report.setValid(false);
                report.addMessage(RegistrationRequestConstant.NAME, "name is blank");
            }
            if (isBlank(registrationRequest.getSurname())) {
                report.setValid(false);
                report.addMessage(RegistrationRequestConstant.SURNAME, "surname is blank");
            }
            if (isBlank(registrationRequest.getAddress().getCountry())) {
                report.setValid(false);
                report.addMessage(AddressConstant.COUNTRY, "country is blank");
            }
            if (isBlank(registrationRequest.getAddress().getCity())) {
                report.setValid(false);
                report.addMessage(AddressConstant.CITY, "city is blank");
            }
            if (isBlank(registrationRequest.getAddress().getStreet())) {
                report.setValid(false);
                report.addMessage(AddressConstant.STREET, "street is blank");
            }
            if (isBlank(registrationRequest.getAddress().getNumber())) {
                report.setValid(false);
                report.addMessage(AddressConstant.NUMBER, "number is blank");
            }
            if (isBlank(registrationRequest.getPhoneNum())) {
                report.setValid(false);
                report.addMessage(RegistrationRequestConstant.PHONE_NUM, "phone num is blank");
            }
            if (isBlank(registrationRequest.getTitle())) {
                report.setValid(false);
                report.addMessage(RegistrationRequestConstant.TITLE, "title is blank");
            }
            if (isBlank(registrationRequest.getRoleName())) {
                report.setValid(false);
                report.addMessage(RegistrationRequestConstant.ROLE_NAME, "role name is blank");
            }
            if (registrationRequest.getStatus() != RegistrationRequestStatus.PENDING) {
                report.setValid(false);
                report.addMessage(RegistrationRequestConstant.STATUS, "status is not pending");
            }
            if (registrationRequest.getSendTime() == null) {
                report.setValid(false);
                report.addMessage(RegistrationRequestConstant.SEND_TIME, "send time is null");
            }
        }

        return report;
    }
}
