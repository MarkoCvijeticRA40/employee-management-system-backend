package com.mcm.EmployeeManagementSystem.validator;

public interface Validator<T> {
    ValidationReport validate(T model);
}
