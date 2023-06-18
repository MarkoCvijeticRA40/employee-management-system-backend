package com.mcm.EmployeeManagementSystem.usecase.role;

import com.mcm.EmployeeManagementSystem.dto.AddPermissionToRoleRequest;
import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.entity.LogNotificationEntity;
import com.mcm.EmployeeManagementSystem.model.Permission;
import com.mcm.EmployeeManagementSystem.model.Role;
import com.mcm.EmployeeManagementSystem.store.PermissionStore;
import com.mcm.EmployeeManagementSystem.store.RoleStore;
import com.mcm.EmployeeManagementSystem.usecase.log.notification.CreateLogNotificationUseCase;
import com.mcm.EmployeeManagementSystem.usecase.log.notification.NotifyAdministratorsUseCase;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.role.AddPermissionToRoleValidator;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddPermissionToRoleUseCase {
    private final AddPermissionToRoleValidator validator;
    private final RoleStore roleStore;
    private final PermissionStore permissionStore;
    private final CreateLogNotificationUseCase createLogNotificationUseCase;
    private final NotifyAdministratorsUseCase notifyAdministratorsUseCase;
    private static final Logger logger = LogManager.getLogger(AddPermissionToRoleUseCase.class);

    public Response add(AddPermissionToRoleRequest request) {
        ValidationReport report = validator.validate(request);
        if (report.isValid()) {
            Role role = roleStore.find(request.getRoleName());
            Permission permission = permissionStore.find(request.getPermissionName());
            List<Permission> permissions = role.getPermissions();
            permissions.add(permission);
            role.setPermissions(permissions);
            roleStore.save(role);
            logger.info("Permission " + request.getPermissionName() + " added to Role " + role.getName());
            createLogNotificationUseCase.create("INFO", "AddPermissionToRoleUseCase", "Permission " + request.getPermissionName() + " added to Role " + role.getName());
            LogNotificationEntity logNotification = new LogNotificationEntity("INFO", "AddPermissionToRoleUseCase", "Permission " + request.getPermissionName() + " added to Role " + role.getName(), LocalDateTime.now());
            notifyAdministratorsUseCase.notifyAdministratorsAboutPermissionChanges(logNotification);
        }

        return new Response(report, request.getPermissionName());
    }
}
