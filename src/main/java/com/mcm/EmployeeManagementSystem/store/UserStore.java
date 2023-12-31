package com.mcm.EmployeeManagementSystem.store;

import com.mcm.EmployeeManagementSystem.converter.UserConverter;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.repository.UserRepository;
import com.mcm.EmployeeManagementSystem.security.crypto.DataEncryptor;
import com.mcm.EmployeeManagementSystem.security.crypto.UserDecryptor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class UserStore {

    private final UserRepository repository;
    private final UserConverter converter;
    private final UserRepository userRepository;
    private final UserDecryptor userDecryptor;
    private final DataEncryptor dataEncryptor;

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
        email = dataEncryptor.encryptData(email);
        return converter.toModel(repository.findUserByEmail(email));
    }
    public boolean exists(String email) {
        email = dataEncryptor.encryptData(email);
        return repository.existsByEmail(email);
    }

    public User getById(Long id) {
        List<User> users = converter.toModel(repository.findAll());
        for (User user : users) {
            if (user.getId() == id) {
                userDecryptor.decryptUser(user);
                return user;
            }
        }
        return null;
    }

    public List<User> getAllEnabled() {
        List<User> users = converter.toModel(userRepository.findAll());
        List<User> filteredUsers = new ArrayList<>();

        for (User user : users) {
            if (user.isAccountEnabled() == true && user.getStartOfWork() != null) {
                user = userDecryptor.decryptUser(user);
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }

    public List<User> findByRoleName(String roleName) {
        List<User> users = getAllEnabled();
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

    public void delete(User user) {
        repository.delete(converter.toEntity(user));
    }

    public List<User> findByRole(String roleName) {
        return converter.toModel(repository.findByRoles_Name(roleName));
    }
}
