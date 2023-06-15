package com.mcm.EmployeeManagementSystem.usecase.project;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.Project;
import com.mcm.EmployeeManagementSystem.security.aes.AESKeyGenerator;
import com.mcm.EmployeeManagementSystem.security.aes.DataEncryption;
import com.mcm.EmployeeManagementSystem.store.ProjectStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.project.CreateProjectValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProjectUseCase {

    private final CreateProjectValidator validator;
    private final ProjectStore projectStore;
    private final AESKeyGenerator aesKeyGenerator;
    @Value("${security.encryption.key}")
    private String encryptionKey;

    public Response create(Project project) {
        ValidationReport report = validator.validate(project);
        if (report.isValid()) {
            try {
                byte[] keyBytes = aesKeyGenerator.decodeAESKey(encryptionKey);
                String encryptedName = DataEncryption.encryptData(project.getName(), keyBytes);
                project.setName(encryptedName);
                project = projectStore.save(project);
            } catch (Exception e) {
                // Handling encryption error
                report.addMessage("Project", "Error encrypting project name.");
            }
        }
        return new Response(report, project);
    }
}
