package com.mcm.EmployeeManagementSystem.usecase.project;

import com.mcm.EmployeeManagementSystem.converter.ProjectConverter;
import com.mcm.EmployeeManagementSystem.model.Project;
import com.mcm.EmployeeManagementSystem.repository.ProjectRepository;
import com.mcm.EmployeeManagementSystem.security.aes.AESKeyGenerator;
import com.mcm.EmployeeManagementSystem.security.aes.DataDecryption;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllUseCase {

    private final AESKeyGenerator aesKeyGenerator;
    private final ProjectConverter converter;
    private final ProjectRepository repository;

    @Value("${security.encryption.key}")
    private String encryptionKey;

    public List<Project> findAll() {
        List<Project> projects = converter.toModel(repository.findAll());
        try {
            byte[] keyBytes = aesKeyGenerator.decodeAESKey(encryptionKey);
            for (Project project : projects) {
                try {
                    String decryptedName = DataDecryption.decryptData(project.getName(), keyBytes);
                    project.setName(decryptedName);
                } catch (Exception e) {
                    // Handling decryption error
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            // Handling key decoding error
            e.printStackTrace();
        }
        return projects;
    }
}
