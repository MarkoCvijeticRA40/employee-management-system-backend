package com.mcm.EmployeeManagementSystem.usecase.log;

import com.mcm.EmployeeManagementSystem.entity.LogNotificationEntity;
import com.mcm.EmployeeManagementSystem.model.LogRecord;
import com.mcm.EmployeeManagementSystem.store.LogNotificationStore;
import com.mcm.EmployeeManagementSystem.usecase.log.notification.NotifyAdministratorsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ShouldWarnAdminUseCase {
    private final ParseLogFileUseCase parseLogFileUseCase;
    private final NotifyAdministratorsUseCase notifyAdministratorsUseCase;
    private final LogNotificationStore store;


    public boolean check(String logLevel, String name, String message) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime fiveMinutesAgo = currentTime.minusMinutes(5);
        int count = 0;
        for (LogRecord logRecord : parseLogFileUseCase.parse()) {
            LocalDateTime logTime = logRecord.getDateTime();
            if (logTime.isAfter(fiveMinutesAgo)
                    && logTime.isBefore(currentTime)
                    && logRecord.getLogLevel().trim().equals(logLevel)
                    && logRecord.getName().trim().equals(name)
                    && logRecord.getMessage().equals(message)) {
                count++;
            }
        }
        if (count >= 5) {
            LogNotificationEntity logNotification = new LogNotificationEntity(logLevel, name, message, count, currentTime);
            store.save(logNotification);
            notifyAdministratorsUseCase.notify(logNotification);
            return true;
        }

        return false;
    }
}
