package com.mcm.EmployeeManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private Address address;
    private String phoneNum;
    private String title;
    private List<String> roleName;
    private LocalDateTime startOfWork;
    private List<Skill> skills;
    private boolean isAccountEnabled;
}
