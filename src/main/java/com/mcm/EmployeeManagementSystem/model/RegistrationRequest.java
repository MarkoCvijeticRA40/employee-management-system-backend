package com.mcm.EmployeeManagementSystem.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private Address address;
    private String phoneNum;
    private String title;
    private String roleName;
    private RegistrationRequestStatus status;
    private LocalDateTime sendTime;
}
