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
public class FindByTitleUseCase {

    private final UserStore userStore;
    private final UserRepository userRepository;
    private final UserConverter converter;

    public List<User> findByTitle(String title) {

        List<User> users = converter.toModel(userRepository.findAll());
        List<User> foundedUsers = new ArrayList<>();

        for (User user : users) {
            if (user.getTitle().equals(title)) {
                foundedUsers.add(user);
            }
        }
        return foundedUsers;
    }
}
