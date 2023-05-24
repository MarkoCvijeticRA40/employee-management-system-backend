package com.mcm.EmployeeManagementSystem.usecase.user;


import com.mcm.EmployeeManagementSystem.converter.UserConverter;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.repository.UserRepository;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FindPotentialEmployeeUseCase {

    private final UserStore userStore;
    private final UserRepository userRepository;
    private final UserConverter converter;

    public List<User> getAllPotentialWorkers() {
        List<User> users = converter.toModel(userRepository.findAll());
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
