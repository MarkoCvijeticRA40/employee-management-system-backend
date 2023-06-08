package com.mcm.EmployeeManagementSystem.usecase.user;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.MultiParamValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SearchUsersUseCase {

    private final FindByRoleNameUseCase findByRoleNameUseCase;
    private final MultiParamValidator<String> validator;
    private final UserStore userStore;

    public Response searchEngineers(String email, String name, String surname, LocalDateTime start, LocalDateTime end) {
        ValidationReport report = validator.validate(email, name, surname, start.toString(), end.toString());
        List<User> filteredUsers = new ArrayList<>();
        if (report.isValid()) {
            List<User> allEngineers = userStore.findByRoleName("Software engineer");

            Predicate<User> emailFilter = user -> user.getEmail().equalsIgnoreCase(email);
            Predicate<User> nameFilter = user -> user.getName().equalsIgnoreCase(name);
            Predicate<User> surnameFilter = user -> user.getSurname().equalsIgnoreCase(surname);
            Predicate<User> startEndFilter = user -> user.getStartOfWork().isAfter(start) && user.getStartOfWork().isBefore(end);

            filteredUsers.addAll(allEngineers.stream()
                    .filter(emailFilter.or(nameFilter).or(surnameFilter).or(startEndFilter))
                    .collect(Collectors.toList()));
        }
        return new Response(report,filteredUsers);
    }
}
