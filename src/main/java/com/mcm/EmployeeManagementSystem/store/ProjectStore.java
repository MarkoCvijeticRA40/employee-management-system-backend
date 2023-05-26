package com.mcm.EmployeeManagementSystem.store;

import com.mcm.EmployeeManagementSystem.converter.ProjectConverter;
import com.mcm.EmployeeManagementSystem.model.Project;
import com.mcm.EmployeeManagementSystem.model.ProjectUserAssignment;
import com.mcm.EmployeeManagementSystem.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ProjectStore {

    private final ProjectRepository repository;
    private final ProjectConverter converter;

    public Project save(Project project) {
        return converter.toModel(repository.save(converter.toEntity(project)));
    }

    public List<Project> findAll() {
        return converter.toModel(repository.findAll());
    }
    public boolean exists(Long id) {
        return repository.existsById(id);
    }
    public Project find(Long id) {
        return converter.toModel(repository.findOne(id));
    }
}