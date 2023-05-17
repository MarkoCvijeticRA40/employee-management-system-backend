package com.mcm.EmployeeManagementSystem.store;

import com.mcm.EmployeeManagementSystem.converter.UserConverter;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
    public List<User> findByRoleName(String roleName) {

        List<User> users = converter.toModel(repository.findAll());
        List<User> foundedUsers = new ArrayList<>();

        for (User user : users) {
            List<String> roleNames = user.getRoleNames();
            for (String role : roleNames) {
                if (role.equals(roleName)) {
                    users.add(user);
                }
            }
        }
        return foundedUsers;
    }

    @Override
    public List<User> findByTitle(String title) {

        List<User> users = converter.toModel(repository.findAll());
        List<User> foundedUsers = new ArrayList<>();

        for (User user : users) {
            if(user.getTitle().equals(title)) {
                foundedUsers.add(user);
            }
        }
        return foundedUsers;
    }

    @Override
    public List<User> searchEngineers(String email, String name, String surname, LocalDateTime start,LocalDateTime end) {

        //Ovde vratiti na findByRole
        List<User> allEngineers = findByTitle("Engineer");

        List<User> filteredUsers = allEngineers.stream()
                .filter(user -> user.getEmail().toLowerCase().equals(email.toLowerCase()))
                .filter(user -> user.getName().toLowerCase().equals(name.toLowerCase()))
                .filter(user -> user.getSurname().toLowerCase().equals(surname.toLowerCase()))
                .collect(Collectors.toList());

        return filteredUsers;
    }

}
