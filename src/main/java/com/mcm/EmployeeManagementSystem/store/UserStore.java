package com.mcm.EmployeeManagementSystem.store;

import com.mcm.EmployeeManagementSystem.converter.UserConverter;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UserStore {

    private final UserRepository repository;
    private final UserConverter converter;

    public User save(User user) {
        return converter.toModel(repository.save(converter.toEntity(user)));
    }

    public boolean exists(Long id) {
        return repository.existsById(id);
    }

    public User find(Long id) {
        return converter.toModel(repository.findOne(id));
    }

    public User find(String email) {
        return converter.toModel(repository.findUserByEmail(email));
    }

    public boolean exists(String email) {
        return repository.existsByEmail(email);
    }

    public User getById(Long id) {
        List<User> users = converter.toModel(repository.findAll());
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
}
