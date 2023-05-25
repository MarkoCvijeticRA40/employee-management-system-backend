package com.mcm.EmployeeManagementSystem.store;

import com.mcm.EmployeeManagementSystem.converter.ProjectUserAssignmentConverter;
import com.mcm.EmployeeManagementSystem.converter.UserConverter;
import com.mcm.EmployeeManagementSystem.model.ProjectUserAssignment;
import com.mcm.EmployeeManagementSystem.model.Skill;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.repository.ProjectUserAssignmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ProjectUserAssignmentStore {
    private final ProjectUserAssignmentRepository repository;
    private final ProjectUserAssignmentConverter converter;
    private final UserConverter userConverter;

    public ProjectUserAssignment save(ProjectUserAssignment projectUserAssignment) {
        return converter.toModel(repository.save(converter.toEntity(projectUserAssignment)));
    }
    public List<ProjectUserAssignment> findByUser(User user) {
        return converter.toModel(repository.findByUser(userConverter.toEntity(user)));
    }
    public ProjectUserAssignment find(Long id) {
        return converter.toModel(repository.findOne(id));
    }
}
