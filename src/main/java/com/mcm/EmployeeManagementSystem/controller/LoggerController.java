package com.mcm.EmployeeManagementSystem.controller;

import com.mcm.EmployeeManagementSystem.dto.LoggerRequest;
import com.mcm.EmployeeManagementSystem.usecase.log.ShouldWarnAdminUseCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@Setter
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/logger")
public class LoggerController {
    private static final Logger logger = LogManager.getLogger(LoggerController.class);
    private final ShouldWarnAdminUseCase shouldWarnAdminUseCase;

    @RequestMapping("/unauthenticated")
    public void logUnauthenticatedAccess(@RequestBody LoggerRequest dto) {
        logger.warn("Unauthenticated user with IP address " + dto.getUserIdentity() + " tried to access a page on the front end");
        shouldWarnAdminUseCase.check("WARN ", "LoggerController", "Unauthenticated user with IP address " + dto.getUserIdentity() + " tried to access a page on the front end");
    }

    @RequestMapping("/unauthorized")
    public void logUnauthorizedAccess(@RequestBody LoggerRequest dto) {
        logger.warn("Unauthorized user with email " + dto.getUserIdentity() + " tried to access a page on the front end");
        shouldWarnAdminUseCase.check("WARN ", "LoggerController", "Unauthorized user with email " + dto.getUserIdentity() + " tried to access a page on the front end");
    }
}
