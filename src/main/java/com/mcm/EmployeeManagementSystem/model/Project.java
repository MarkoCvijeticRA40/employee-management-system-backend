package com.mcm.EmployeeManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private Long id;
    private String name;
    private String duration;
    private List<User> users;
}
