package com.mcm.EmployeeManagementSystem.usecase.user;

import com.mcm.EmployeeManagementSystem.converter.UserConverter;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FindByRoleNameUseCase {

    private final UserRepository userRepository;
    private final UserConverter converter;
    private final GetAllEnabledUseCase getAllEnabledUseCase;

    public List<User> findByRoleName(String roleName) {
        List<User> users = getAllEnabledUseCase.getAllEnabled();
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
}
