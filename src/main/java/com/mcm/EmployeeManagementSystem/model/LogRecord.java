package com.mcm.EmployeeManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogRecord {
    private LocalDateTime dateTime;
    private String logLevel;
    private String name;
    private String message;
}
