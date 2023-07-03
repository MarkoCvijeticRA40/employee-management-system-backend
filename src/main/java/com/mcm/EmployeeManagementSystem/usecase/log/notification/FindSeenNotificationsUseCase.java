package com.mcm.EmployeeManagementSystem.usecase.log.notification;

import com.mcm.EmployeeManagementSystem.entity.LogNotificationEntity;
import com.mcm.EmployeeManagementSystem.store.LogNotificationStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindSeenNotificationsUseCase {
    private final LogNotificationStore store;

    public List<LogNotificationEntity> find() {
        return store.find(true);
    }
}
