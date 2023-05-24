package com.mcm.EmployeeManagementSystem.usecase.user;

import com.mcm.EmployeeManagementSystem.converter.UserConverter;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.repository.UserRepository;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SearchUsersUseCase {

    private final UserStore userStore;
    private final UserRepository userRepository;
    private final UserConverter converter;
    private final FindByRoleNameUseCase findByRoleNameUseCase;

    public List<User> searchEngineers(String email, String name, String surname, LocalDateTime start, LocalDateTime end) {
        List<User> allEngineers = findByRoleNameUseCase.findByRoleName("Software engineer\n");

        List<User> filteredUsers = allEngineers.stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .filter(user -> user.getSurname().equalsIgnoreCase(surname))
                .filter(user -> user.getStartOfWork().isAfter(start) && user.getStartOfWork().isBefore(end))
                .collect(Collectors.toList());

        return filteredUsers;
    }
}
