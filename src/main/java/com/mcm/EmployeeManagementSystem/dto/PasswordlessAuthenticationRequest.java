package com.mcm.EmployeeManagementSystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PasswordlessAuthenticationRequest {
    private String email;
}
