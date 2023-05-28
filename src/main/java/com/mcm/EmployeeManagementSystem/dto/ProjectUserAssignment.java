package com.mcm.EmployeeManagementSystem.dto;

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
    private Long projectId;
    private Long userId;
    private String workDescription;
    private LocalDateTime startOfWork;
    private LocalDateTime endOfWork;
}
