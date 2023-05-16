package com.mcm.EmployeeManagementSystem.validator;

public interface ReadValidator<T> {
    ValidationReport validate(T id);
}
