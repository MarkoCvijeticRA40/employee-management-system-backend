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
public class ProjectUserAssignment {
    private Long id;
    private Project project;
    private User user;
    private String workDescription;
    private LocalDateTime startOfWork;
    private LocalDateTime endOfWork;
}
