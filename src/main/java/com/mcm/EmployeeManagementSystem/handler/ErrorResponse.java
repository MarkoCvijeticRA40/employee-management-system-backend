package com.mcm.EmployeeManagementSystem.handler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private String title;

    public ErrorResponse() {
        this.title = "Employee management system application";
    }
}
