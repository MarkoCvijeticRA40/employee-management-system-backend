package com.mcm.EmployeeManagementSystem.usecase.user;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.security.crypto.UserEncryptor;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.user.EditUserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EditHrManagerUseCase {

    private final UserStore store;
    private final EditUserValidator validator;
    private final PasswordEncoder passwordEncoder;
    private final UserEncryptor userEncryptor;

    public Response update(Long id, User user) {
        user = userEncryptor.encryptUser(user);
        ValidationReport report = validator.validate(user);
        User editedUser = store.find(id);
        if (report.isValid()) {
            editedUser = IsHrManagerPasswordChanged(user, editedUser);
            editedUser = store.save(editedUser);
        }

        return new Response(report, editedUser);
    }

    public User IsHrManagerPasswordChanged(User user, User editedUser) {
        if(!user.getPassword().equals(editedUser.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return user;
    }
}
