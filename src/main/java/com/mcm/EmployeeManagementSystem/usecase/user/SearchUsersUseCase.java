package com.mcm.EmployeeManagementSystem.usecase.user;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SearchUsersUseCase {

    private final FindByRoleNameUseCase findByRoleNameUseCase;
    private final UserStore userStore;

    public Response searchEngineers(String email, String name, String surname, LocalDateTime start, LocalDateTime end) {
        ValidationReport report = new ValidationReport();
        List<User> filteredUsers = new ArrayList<>();
        report.setValid(true);
        List<User> allEngineers = userStore.findByRoleName("Software engineer");

        Predicate<User> emailFilter = user -> email != null && !email.isEmpty() && user.getEmail().equalsIgnoreCase(email);
        Predicate<User> nameFilter = user -> name != null && !name.isEmpty() && user.getName().equalsIgnoreCase(name);
        Predicate<User> surnameFilter = user -> surname != null && !surname.isEmpty() && user.getSurname().equalsIgnoreCase(surname);
        Predicate<User> startEndFilter = user -> start != null && end != null && user.getStartOfWork().isAfter(start) && user.getStartOfWork().isBefore(end);

        filteredUsers.addAll(allEngineers.stream()
                .filter(emailFilter.or(nameFilter).or(surnameFilter).or(startEndFilter))
                .distinct()
                .collect(Collectors.toList()));

        List<User> uniqueUsers = filteredUsers.stream().distinct().collect(Collectors.toList());

        return new Response(report, uniqueUsers);
    }

}
