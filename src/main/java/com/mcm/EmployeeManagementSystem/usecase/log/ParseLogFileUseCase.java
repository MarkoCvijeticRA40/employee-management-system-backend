package com.mcm.EmployeeManagementSystem.usecase.log;

import com.mcm.EmployeeManagementSystem.model.LogRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParseLogFileUseCase {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");

    public List<LogRecord> parse() {
        String filePath = "C:\\Users\\KORISNIK\\Desktop\\EmployeeManagementSystemBackend\\EmployeeManagementSystem\\logs\\logs.log";
        List<LogRecord> logRecords = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                LogRecord logRecord = parseLogRecord(line);
                if (logRecord != null) {
                    logRecords.add(logRecord);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logRecords;
    }

    private LogRecord parseLogRecord(String logLine) {
        String[] parts = logLine.split("\\|");
        if (parts.length >= 4) {
            String dateTimeString = parts[0];
            String logLevel = parts[1];
            String controllerName = parts[2];
            String message = parts[3];

            LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);

            return new LogRecord(dateTime, logLevel, controllerName, message);
        }

        return null;
    }
}
