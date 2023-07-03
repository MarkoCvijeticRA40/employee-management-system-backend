package com.mcm.EmployeeManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "log_notifications")
public class LogNotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String logLevel;
    private String name;
    private String message;
    private int count;
    private LocalDateTime start;
    private boolean read;

    public LogNotificationEntity(String logLevel, String name, String message, int count, LocalDateTime start) {
        this.logLevel = logLevel;
        this.name = name;
        this.message = message;
        this.count = count;
        this.start = start;
        this.read = false;
    }

    public LogNotificationEntity(String logLevel, String name, String message, LocalDateTime currentTime) {
        this.logLevel = logLevel;
        this.name = name;
        this.message = message;
        this.start = currentTime;
        this.read = false;
    }
}
