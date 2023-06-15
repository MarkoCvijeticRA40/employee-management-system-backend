package com.mcm.EmployeeManagementSystem.usecase.user;

import com.mcm.EmployeeManagementSystem.converter.UserConverter;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.repository.UserRepository;
import com.mcm.EmployeeManagementSystem.security.crypto.UserDecryptor;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FindUsersWithStartDateUseCase {

    private final UserRepository userRepository;
    private final UserConverter converter;
    private final UserDecryptor userDecryptor;

    public List<User> findUsersWithStartDate() {
        List<User> users = converter.toModel(userRepository.findAll());
        List<User> filteredUsers = new ArrayList<>();

        for (User user : users) {
            if (user.getStartOfWork() != null) {
                User decryptedUser = userDecryptor.decryptUser(user);
                filteredUsers.add(decryptedUser);
            }
        }
        return filteredUsers;
    }
}
