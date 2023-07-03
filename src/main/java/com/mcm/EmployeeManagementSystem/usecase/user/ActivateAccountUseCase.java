package com.mcm.EmployeeManagementSystem.usecase.user;

import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.security.crypto.UserEncryptor;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.usecase.qrcode.SendQrCodeUseCase;
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
    private final UserEncryptor userEncryptor;
    private final SendQrCodeUseCase sendQrCodeUseCase;

    public void activate(Long userId) {
        ValidationReport report = readUserValidator.validate(userId);
        if (report.isValid()) {
            User user = store.find(userId);
            user = userEncryptor.encryptUser(user);
            user.setAccountEnabled(true);
            user.setStartOfWork(LocalDateTime.now());
            store.save(user);
            sendQrCodeUseCase.send2FALoginQrCode(user);
        }
    }
}
