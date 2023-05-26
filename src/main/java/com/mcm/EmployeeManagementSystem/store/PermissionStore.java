package com.mcm.EmployeeManagementSystem.store;

import com.mcm.EmployeeManagementSystem.converter.PermissionConverter;
import com.mcm.EmployeeManagementSystem.model.Permission;
import com.mcm.EmployeeManagementSystem.repository.PermissionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PermissionStore {
    private final PermissionRepository repository;
    private final PermissionConverter converter;

    public boolean exists(String name) {
        return repository.existsByName(name);
    }

    public Permission find(String name) {
        return converter.toModel(repository.findByName(name));
    }
}
