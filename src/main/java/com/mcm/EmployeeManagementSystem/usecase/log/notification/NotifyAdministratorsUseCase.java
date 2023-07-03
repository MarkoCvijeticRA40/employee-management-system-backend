package com.mcm.EmployeeManagementSystem.usecase.log.notification;

import com.mcm.EmployeeManagementSystem.constant.EmailConstant;
import com.mcm.EmployeeManagementSystem.constant.RoleConstant;
import com.mcm.EmployeeManagementSystem.entity.LogNotificationEntity;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.usecase.email.SendEmailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotifyAdministratorsUseCase {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
    private final UserStore store;
    private final SendEmailUseCase sendEmailUseCase;

    public void notify(LogNotificationEntity logNotification) {
        List<User> administrators = store.findByRole(RoleConstant.ADMINISTRATOR);
        String message =
                "Log Level: " + logNotification.getLogLevel() + "\n" +
                        "Name: " + logNotification.getName() + "\n" +
                        "Message:" + logNotification.getMessage() + "\n" +
                        "Count: " + logNotification.getCount() + "\n" +
                        "Date and Time: " + logNotification.getStart().format(formatter) + "\n";
        for (User user : administrators) {
            sendEmailUseCase.send(user.getEmail(), message, EmailConstant.LOG_NOTIFICATION_SUBJECT);
            System.out.println("Email sent");
        }
    }

    public void notifyAdministratorsAboutPermissionChanges(LogNotificationEntity logNotification) {
        List<User> administrators = store.findByRole(RoleConstant.ADMINISTRATOR);
        String message =
                "Log Level: " + logNotification.getLogLevel() + "\n" +
                        "Name: " + logNotification.getName() + "\n" +
                        "Message:" + logNotification.getMessage() + "\n" +
                        "Date and Time: " + logNotification.getStart().format(formatter) + "\n";
        for (User user : administrators) {
            sendEmailUseCase.send(user.getEmail(), message, EmailConstant.LOG_NOTIFICATION_PERMISSION_SUBJECT);
            System.out.println("Email sent");
        }
    }
}
