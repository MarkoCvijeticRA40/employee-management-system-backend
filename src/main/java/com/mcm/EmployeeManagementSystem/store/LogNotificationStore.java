package com.mcm.EmployeeManagementSystem.store;

import com.mcm.EmployeeManagementSystem.entity.LogNotificationEntity;
import com.mcm.EmployeeManagementSystem.repository.LogNotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class LogNotificationStore {
    private final LogNotificationRepository repository;

    public void save(LogNotificationEntity logNotification) {
        repository.save(logNotification);
    }

    public List<LogNotificationEntity> findAll() {
        return repository.findAll();
    }

    public List<LogNotificationEntity> find(boolean isRead) {
        return repository.findByRead(isRead);
    }
}
