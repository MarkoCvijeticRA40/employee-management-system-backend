package com.mcm.EmployeeManagementSystem.store;

import com.mcm.EmployeeManagementSystem.converter.UserConverter;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<User> findByRoleName(String roleName) {
        List<User> users = converter.toModel(repository.findAll());
        List<User> foundedUsers = new ArrayList<>();

        for (User user : users) {
            List<String> roleNames = user.getRoleNames();
            for (String role : roleNames) {
                if (role.equals(roleName)) {
                    foundedUsers.add(user); // Add the user to the foundedUsers list, not the users list
                    break; // Exit the inner loop since the user is found
                }
            }
        }
        return foundedUsers;
    }

    public List<User> findByTitle(String title) {

        List<User> users = converter.toModel(repository.findAll());
        List<User> foundedUsers = new ArrayList<>();

        for (User user : users) {
            if (user.getTitle().equals(title)) {
                foundedUsers.add(user);
            }
        }
        return foundedUsers;
    }

    public List<User> searchEngineers(String email, String name, String surname, LocalDateTime start, LocalDateTime end) {
        List<User> allEngineers = findByRoleName("Software engineer\n");

        List<User> filteredUsers = allEngineers.stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .filter(user -> user.getSurname().equalsIgnoreCase(surname))
                .filter(user -> user.getStartOfWork().isAfter(start) && user.getStartOfWork().isBefore(end))
                .collect(Collectors.toList());

        return filteredUsers;
    }

    public List<User> getAllEnabled() {
        List<User> users = converter.toModel(repository.findAll());
        List<User> filteredUsers = new ArrayList<>();

        for (User user : users) {
            if (user.isAccountEnabled() == true) {
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
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
    
    public List<User> getAllPotentialWorkers() {
        List<User> users = converter.toModel(repository.findAll());
        List<User> filteredUsers = new ArrayList<>();

        for (User user : users) {
            boolean hasSEorPMRole = false;
            for (String role : user.getRoleNames()) {
                if (role.equals("Software engineer\n") || role.equals("Project manager")) {
                    hasSEorPMRole = true;
                    break;
                }
            }
            if (hasSEorPMRole) {
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }
}
