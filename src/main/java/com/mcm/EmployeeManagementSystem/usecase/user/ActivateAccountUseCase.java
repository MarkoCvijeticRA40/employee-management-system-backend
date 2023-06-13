package com.mcm.EmployeeManagementSystem.usecase.user;

import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.user.ReadUserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ActivateAccountUseCase {
    private final UserStore store;
    private final ReadUserValidator readUserValidator;

    public void activate(Long userId) {
        ValidationReport report = readUserValidator.validate(userId);
        if (report.isValid()) {
            User user = store.find(userId);
            user.setAccountEnabled(true);
            //Dodao zbog funkcionalnosti sa blokiranjem korisnika
            user.setStartOfWork(LocalDateTime.now());
            store.save(user);
        }
    }

}
