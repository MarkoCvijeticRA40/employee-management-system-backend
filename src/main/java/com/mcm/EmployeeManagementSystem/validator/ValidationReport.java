package com.mcm.EmployeeManagementSystem.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationReport {
    private boolean isValid;
    private Map<String, String> errorMessages = new HashMap<>();

    public void addMessage(String title, String context) {
        errorMessages.put(title, context);
    }
}
