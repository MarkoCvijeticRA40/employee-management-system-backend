package com.mcm.EmployeeManagementSystem.store;

import com.mcm.EmployeeManagementSystem.converter.RoleConverter;
import com.mcm.EmployeeManagementSystem.model.Role;
import com.mcm.EmployeeManagementSystem.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoleStore {
    private final RoleRepository repository;
    private final RoleConverter converter;

    public boolean exists(String name) {
        return repository.existsByName(name);
    }

    public Role find(String name) {
        return converter.toModel(repository.findByName(name));
    }

    public Role save(Role role) {
        return converter.toModel(repository.save(converter.toEntity(role)));
    }
}
