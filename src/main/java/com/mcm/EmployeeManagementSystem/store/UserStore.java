package com.mcm.EmployeeManagementSystem.store;

import com.mcm.EmployeeManagementSystem.converter.UserConverter;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserStore implements IUserStore{

    private final UserRepository repository;
    private final UserConverter converter;

    @Override
    public User save(User user) {
        return converter.toModel(repository.save(converter.toEntity(user)));
    }

    @Override
    public List<User> findUsers(String email, String name, String surname) {

        List<User> users = findAll();
        List<User> foundedUsers = new ArrayList<>();

        for (User user : users) {
            if ((name == null || name.isEmpty() || user.getName().equalsIgnoreCase(name.toLowerCase())) &&
                    (surname == null || surname.isEmpty() || user.getSurname().equalsIgnoreCase(surname.toLowerCase())) &&
                    (email == null || email.isEmpty() || user.getEmail().equalsIgnoreCase(email.toLowerCase()))) {
                foundedUsers.add(user);
            }
        }
        return foundedUsers;
    }

    @Override
    public List<User> findAll() {
        return converter.toModel(repository.findAll());
    }



}
it 