package com.mcm.EmployeeManagementSystem.validator;

public interface MultiParamValidator<T> {
    ValidationReport validate(T... params);
}
