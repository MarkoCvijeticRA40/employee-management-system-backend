package com.mcm.EmployeeManagementSystem.store;

import com.mcm.EmployeeManagementSystem.converter.ProjectConverter;
import com.mcm.EmployeeManagementSystem.converter.ProjectUserAssignmentConverter;
import com.mcm.EmployeeManagementSystem.converter.UserConverter;
import com.mcm.EmployeeManagementSystem.model.Project;
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
    private final ProjectConverter projectConverter;

    public ProjectUserAssignment save(ProjectUserAssignment projectUserAssignment) {
        return converter.toModel(repository.save(converter.toEntity(projectUserAssignment)));
    }
    public List<ProjectUserAssignment> findByUser(User user) {
        return converter.toModel(repository.findByUser(userConverter.toEntity(user)));
    }
    public List<ProjectUserAssignment> findByProject(Project project) {
        return converter.toModel(repository.findByProject(projectConverter.toEntity(project)));
    }
    public ProjectUserAssignment find(Long id) {
        return converter.toModel(repository.findOne(id));
    }
    public void delete(ProjectUserAssignment projectUserAssignment) {
        repository.delete(converter.toEntity(projectUserAssignment));
    }
    public boolean exists(Long id) {
        return repository.existsById(id);
    }
}
