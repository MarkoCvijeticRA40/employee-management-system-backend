package com.mcm.EmployeeManagementSystem.store;

import com.mcm.EmployeeManagementSystem.converter.UserConverter;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.repository.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserStore {
    private final UserRepository repository;
    private final UserConverter converter;

    User save(User user) {
        return converter.toModel(repository.save(converter.toEntity(user)));
    }
}
