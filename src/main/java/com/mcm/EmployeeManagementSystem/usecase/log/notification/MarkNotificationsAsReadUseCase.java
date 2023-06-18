package com.mcm.EmployeeManagementSystem.usecase.log.notification;

import com.mcm.EmployeeManagementSystem.entity.LogNotificationEntity;
import com.mcm.EmployeeManagementSystem.store.LogNotificationStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarkNotificationsAsReadUseCase {
    private final FindNonSeenNotificationsUseCase findNonSeenNotificationsUseCase;
    private final LogNotificationStore store;

    public void setRead() {
        for (LogNotificationEntity logNotification : findNonSeenNotificationsUseCase.find()) {
            logNotification.setRead(true);
            store.save(logNotification);
        }
    }
}
