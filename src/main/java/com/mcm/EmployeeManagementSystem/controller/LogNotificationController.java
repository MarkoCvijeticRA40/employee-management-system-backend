package com.mcm.EmployeeManagementSystem.controller;

import com.mcm.EmployeeManagementSystem.entity.LogNotificationEntity;
import com.mcm.EmployeeManagementSystem.usecase.log.notification.FindAllNotificationsUseCase;
import com.mcm.EmployeeManagementSystem.usecase.log.notification.FindNonSeenNotificationsUseCase;
import com.mcm.EmployeeManagementSystem.usecase.log.notification.MarkNotificationsAsReadUseCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/logNotification")
public class LogNotificationController {
    private final FindAllNotificationsUseCase findAllNotificationsUseCase;
    private final FindNonSeenNotificationsUseCase findNonSeenNotificationsUseCase;
    private final MarkNotificationsAsReadUseCase markNotificationsAsReadUseCase;

    @GetMapping("/all")
    public List<LogNotificationEntity> getAll() {
        return findAllNotificationsUseCase.find();
    }

    @GetMapping("/new")
    public List<LogNotificationEntity> findNonSeen() {
        return findNonSeenNotificationsUseCase.find();
    }

    @PutMapping("/setRead")
    public void setRead() {
        markNotificationsAsReadUseCase.setRead();
    }
}
