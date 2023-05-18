package com.mcm.EmployeeManagementSystem.store;

import com.mcm.EmployeeManagementSystem.converter.ProjectConverter;
import com.mcm.EmployeeManagementSystem.model.Project;
import com.mcm.EmployeeManagementSystem.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProjectStore implements IProjectStore {

    private final ProjectRepository repository;
    private final ProjectConverter converter;

    @Override
    public Project save(Project project) {
        return converter.toModel(repository.save(converter.toEntity(project)));
    }



}