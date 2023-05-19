package com.mcm.EmployeeManagementSystem.store;

import com.mcm.EmployeeManagementSystem.model.Project;

import java.util.List;

public interface IProjectStore {
    public Project save(Project project);
    public List<Project> findAll();
}
