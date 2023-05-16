package com.mcm.EmployeeManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "project_user_assignments")
public class ProjectUserAssignmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private ProjectEntity project;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
    private String workDescription;
    private LocalDateTime startOfWork;
    private LocalDateTime endOfWork;
}
