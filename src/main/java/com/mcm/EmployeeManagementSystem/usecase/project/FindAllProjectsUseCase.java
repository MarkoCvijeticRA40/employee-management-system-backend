package com.mcm.EmployeeManagementSystem.usecase.project;

import com.mcm.EmployeeManagementSystem.converter.ProjectConverter;
import com.mcm.EmployeeManagementSystem.model.Project;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.repository.ProjectRepository;
import com.mcm.EmployeeManagementSystem.security.crypto.UserDecryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllProjectsUseCase {

    private final ProjectConverter converter;
    private final ProjectRepository repository;
    private final UserDecryptor userDecryptor;

    public List<Project> findAll() {
        List<Project> projects = converter.toModel(repository.findAll());

        for (Project project : projects) {
            List<User> users = project.getUsers();
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                User decryptedUser = userDecryptor.decryptUser(user);
                users.set(i, decryptedUser);
            }
        }
        return projects;
    }
}
