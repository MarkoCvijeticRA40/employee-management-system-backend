package com.mcm.EmployeeManagementSystem.usecase.log.notification;

import com.mcm.EmployeeManagementSystem.entity.LogNotificationEntity;
import com.mcm.EmployeeManagementSystem.store.LogNotificationStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateLogNotificationUseCase {
    private final LogNotificationStore store;

    public void create(String logLevel, String name, String message) {
        LocalDateTime currentTime = LocalDateTime.now();
        LogNotificationEntity logNotification = new LogNotificationEntity(logLevel, name, message, currentTime);
        store.save(logNotification);
    }
}
